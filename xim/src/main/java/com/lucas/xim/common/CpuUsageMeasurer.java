package com.lucas.xim.common;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  6:25 下午
 */
import android.os.Process;
import android.text.TextUtils;


import com.lucas.xim.BaseManager;

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CpuUsageMeasurer {
    private static final String TAG = "CpuUsageMeasurer";
    private static final long UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(2);

    private final long mClockClkInHz;
    private final int mProcessorCount;

    private RandomAccessFile mProcessStatFile;
    private RandomAccessFile mSystemStatFile;
    private long mLastUpdateTime = 0;

    private float mLastAppCpuTimeUsed = 0;
    private float mLastAppCpuUsage = 0;

    private long mTotalCpuTime = 0;
    private long mIdleCpuTime = 0;
    private float mLastSysCpuUsage = 0;

    public CpuUsageMeasurer() {
        mClockClkInHz = BaseManager.getInstance().getClockTickInHz();
        mProcessorCount = Runtime.getRuntime().availableProcessors();
        String filePath = String.format(Locale.ENGLISH, "/proc/%d/stat", Process.myPid());
        try {
            mProcessStatFile = new RandomAccessFile(filePath, "r");
        } catch (IOException e) {
            IMLog.e(TAG, "open /proc/[PID]/stat failed. " + e.getMessage());
        }

        try {
            mSystemStatFile = new RandomAccessFile("/proc/stat", "r");
        } catch (IOException e) {
            // ignored
        }
    }

    int[] getCpuUsage() {
        synchronized (this) {
            if (BaseManager.getInstance().getTimeTick() - mLastUpdateTime >= UPDATE_INTERVAL) {
                updateCpuUsage();
            }
            return new int[] {(int) (mLastAppCpuUsage * 10), (int) (mLastSysCpuUsage * 10)};
        }
    }

    private void updateCpuUsage() {
        String[] params = readFirstLineAndSplit(mProcessStatFile);
        // stat文件正常为52个字段，三星Galaxy Note 8会多出来一个字段
        if (params == null || params.length < 52) {
            return;
        }

        // #13 ~ #16分别代表utime、stime、cutime、cstime，具体见http://man7.org/linux/man-pages/man5/proc.5.html
        long ticks = Long.parseLong(params[13]) + Long.parseLong(params[14])
                + Long.parseLong(params[15]) + Long.parseLong(params[16]);
        long cpuTimeUsed = (long) (1000f * ticks / mClockClkInHz);

        long totalCpuTime, idleCpuTime;
        String[] cpuInfos = readFirstLineAndSplit(mSystemStatFile);
        if (cpuInfos == null || cpuInfos.length < 8) {
            // /proc/stat读取不到的时候，将total和idle的赋值为时间差（需要乘以CPU核数）
            totalCpuTime = idleCpuTime = BaseManager.getInstance().getTimeTick() * mProcessorCount;
        } else {
            totalCpuTime = Long.parseLong(cpuInfos[1]) + Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[5]) + Long.parseLong(cpuInfos[6])
                    + Long.parseLong(cpuInfos[7]);
            idleCpuTime = Long.parseLong(cpuInfos[4]) + Long.parseLong(cpuInfos[5]);
            // 统一换算为ms为单位
            totalCpuTime = (long) (1000f * totalCpuTime / mClockClkInHz);
            idleCpuTime = (long) (1000f * idleCpuTime / mClockClkInHz);
        }

        long timeDelta = totalCpuTime - mTotalCpuTime;
        mLastAppCpuUsage = 100f * (cpuTimeUsed - mLastAppCpuTimeUsed) / timeDelta;
        mLastSysCpuUsage = 100f * (timeDelta - (idleCpuTime - mIdleCpuTime)) / timeDelta;

        mLastAppCpuTimeUsed = cpuTimeUsed;
        mIdleCpuTime = idleCpuTime;
        mTotalCpuTime = totalCpuTime;

        mLastUpdateTime = BaseManager.getInstance().getTimeTick();
    }

    private static String[] readFirstLineAndSplit(RandomAccessFile file) {
        if (file == null) {
            return null;
        }

        String line = null;
        try {
            file.seek(0);
            line = file.readLine();
        } catch (IOException e) {
            IMLog.e(TAG, "read line failed. " + e.getMessage());
        }

        if (TextUtils.isEmpty(line)) {
            return null;
        }

        return line.split("\\s+");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeQuietly(mProcessStatFile);
        closeQuietly(mSystemStatFile);
        IMLog.i(TAG, "measurer is released");
    }

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
}

