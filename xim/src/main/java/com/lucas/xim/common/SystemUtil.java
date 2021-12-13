package com.lucas.xim.common;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import com.lucas.xim.v1.IMContext;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/10  6:24 下午
 */
public class SystemUtil {
    private static final String TAG = SystemUtil.class.getSimpleName();

    private static final int TIME_INTERVAL = 15000;
    private static boolean mFirstTimeRun  = true;
    private static boolean sRunningMemCheck = false;
    private static int  lastMemUsage = 0;
    private static float lastAppCpu = 0;
    private static float lastSysCpu = 0;
    private static long  lastMemCheckTimeStamps = 0;
    private static long lastAppCpuTime = 0;
    private static long lastSysCpuTime = 0;
    private static CpuUsageMeasurer sCpuUsageMeasurer;

    private static String buildBrand = "";
    private static String buildManufacturer = "";
    private static String buildModel = "";
    private static String buildVersionRelease = "";
    private static int buildVersionSDKInt = 0;

    private static final String DEVICE_INFO = "DeviceInfo";
    private static final String DEVICE_ID = "DeviceID";

    private static boolean sIsLoadLibrarySuccess = false;

    public static boolean isLibraryLoaded() {
        return sIsLoadLibrarySuccess;
    }

    /**
     * 从默认路径加载libImSDK.so
     * @return 是否加载成功
     */
    public static boolean loadIMLibrary() {
        return loadIMLibrary(null);
    }

    /**
     * 从指定文件夹路径加载libImSDK.so
     * @param libraryDirPath libImSDK.so所在文件夹的路径
     * @return 是否加载成功
     */
    @SuppressLint("UnsafeDynamicallyLoadedCode")
    public static boolean loadIMLibrary(String libraryDirPath) {
        if (sIsLoadLibrarySuccess) {
            return true;
        }
        try {
            if (!TextUtils.isEmpty(libraryDirPath)) {
                String libraryName = "libImSDK.so";
                String libraryFullPath = libraryDirPath + File.separator + libraryName;
                System.load(libraryFullPath);
                sIsLoadLibrarySuccess = true;
                Log.i(TAG, "system load so library success: " + libraryFullPath);
            } else {
                System.loadLibrary("ImSDK");
                sIsLoadLibrarySuccess = true;
                Log.i(TAG, "system load so library success, libImSDK.so");
            }
        } catch (UnsatisfiedLinkError e) {
            sIsLoadLibrarySuccess = false;
            Log.e(TAG, "system load so library failed, " + e.getMessage());
        } catch (Exception e) {
            sIsLoadLibrarySuccess = false;
            Log.e(TAG, "system load so library failed, " + e.getMessage());
        }
        return sIsLoadLibrarySuccess;
    }

    public static void setBuildModel(String model) {
        buildModel = model;
    }

    public static void setBuildBrand(String brand) {
        buildBrand = brand;
    }

    public static void setBuildManufacturer(String manufacturer) {
        buildManufacturer = manufacturer;
    }

    public static void setBuildVersionRelease(String versionRelease) {
        buildVersionRelease = versionRelease;
    }

    public static void setBuildVersionSDKInt(int versionSDKInt) {
        buildVersionSDKInt = versionSDKInt;
    }

    public static String getDeviceType() {
        if (TextUtils.isEmpty(buildModel)) {
            buildModel = Build.MODEL;
        }

        return buildModel;
    }

    public static String getSystemVersion() {
        if (TextUtils.isEmpty(buildVersionRelease)) {
            buildVersionRelease = Build.VERSION.RELEASE;
        }

        return buildVersionRelease;
    }

    public static int getSDKVersion() {
        if (buildVersionSDKInt == 0) {
            buildVersionSDKInt = Build.VERSION.SDK_INT;
        }

        return buildVersionSDKInt;
    }

    public static String getDeviceID() {
        Context context = IMContext.getInstance().getApplicationContext();
        if (null == context) {
            return "";
        }

        String deviceId = "";
        boolean isNeedSave = false;
        SharedPreferences preferences = context.getSharedPreferences(DEVICE_INFO, Context.MODE_PRIVATE);
        if (!preferences.contains(DEVICE_ID)) {
            deviceId = UUID.randomUUID().toString();
            isNeedSave = true;
        } else {
            deviceId = preferences.getString(DEVICE_ID, "");

            // 检测是否符合UUID的形式，不符合则重新生成
            String pattern = "\\w{8}(-\\w{4}){3}-\\w{12}";
            boolean isMatch = Pattern.matches(pattern, deviceId);
            if (!isMatch || TextUtils.isEmpty(deviceId)) {
                deviceId = UUID.randomUUID().toString();
                isNeedSave = true;
            }
        }

        if (isNeedSave) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(DEVICE_ID, deviceId);
            editor.apply();
        }

        return deviceId;
    }

    public static int getInstanceType() {
        int vendorId = 2002;

        if (isBrandXiaoMi()) {
            vendorId = 2000;
        } else if (isBrandHuawei()) {
            vendorId = 2001;
        } else if (isBrandMeizu()) {
            vendorId = 2003;
        } else if (isBrandOppo()) {
            vendorId = 2004;
        } else if (isBrandVivo()) {
            vendorId = 2005;
        }

        return vendorId;
    }

    static boolean isBrandXiaoMi() {
        String brandXiaoMi = "xiaomi";
        return brandXiaoMi.equalsIgnoreCase(getBuildBrand()) || brandXiaoMi.equalsIgnoreCase(getBuildManufacturer());
    }

    static boolean isBrandHuawei() {
        String brandHuaWei = "huawei";
        return brandHuaWei.equalsIgnoreCase(getBuildBrand()) || brandHuaWei.equalsIgnoreCase(getBuildManufacturer());
    }

    static boolean isBrandMeizu() {
        String brandMeiZu = "meizu";
        return brandMeiZu.equalsIgnoreCase(getBuildBrand()) || brandMeiZu.equalsIgnoreCase(getBuildManufacturer());
    }

    static boolean isBrandOppo() {
        return "oppo".equalsIgnoreCase(getBuildBrand()) || "oppo".equalsIgnoreCase(getBuildManufacturer()) ||
                "realme".equalsIgnoreCase(getBuildBrand()) || "realme".equalsIgnoreCase(getBuildManufacturer()) ||
                "oneplus".equalsIgnoreCase(getBuildBrand()) || "oneplus".equalsIgnoreCase(getBuildManufacturer());
    }

    static boolean isBrandVivo() {
        String brandVivo = "vivo";
        return brandVivo.equalsIgnoreCase(getBuildBrand()) || brandVivo.equalsIgnoreCase(getBuildManufacturer());
    }

    private static String getBuildBrand() {
        if (TextUtils.isEmpty(buildBrand)) {
            buildBrand = Build.BRAND;
        }

        return buildBrand;
    }

    private static String getBuildManufacturer() {
        if (TextUtils.isEmpty(buildManufacturer)) {
            buildManufacturer = Build.MANUFACTURER;
        }

        return buildManufacturer;
    }

    public static String getSDKInitPath() {
        Context context = IMContext.getInstance().getApplicationContext();
        if (null == context) {
            return "";
        }

        String initPath = context.getFilesDir().toString();

        IMLog.v(TAG, "SDK Init Path: " + initPath);

        return initPath;
    }

    public static String getSDKLogPath() {
        Context context = IMContext.getInstance().getApplicationContext();
        if (null == context) {
            return "";
        }

        // Android 10 系统开始使用分区存储，因此使用应用专有目录。
        String logPath = "";
        File filesDir = context.getExternalFilesDir(null);
        if (filesDir == null) {
            logPath = "/sdcard/Android/data/" + context.getPackageName() + "/log/tencent/imsdk";
        } else {
            logPath = filesDir.getAbsolutePath() + File.separator + "log" + File.separator + "tencent" + File.separator + "imsdk";
        }

        File file = new File(logPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                file = context.getFilesDir();
                IMLog.e(TAG, "create log folder failed");
            }
        }

        logPath = file.getAbsolutePath() + "/";

        IMLog.v(TAG, "SDK LOG Path: " + logPath);

        return logPath;
    }

    /**
     * 计算 MD5
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static float getAppCpuUsage() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastAppCpuTime < TIME_INTERVAL) {
            return lastAppCpu;
        }

        int[] cpuUsage = getProcessCPURate();
        int appCpuUsage = cpuUsage[0] / 10;
        float appCpuUsagePercent = (float) appCpuUsage / 100;
        lastAppCpu = appCpuUsagePercent;
        lastAppCpuTime = currentTimeMillis;
        return appCpuUsagePercent;
    }

    public static float getSysCpuUsage() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastSysCpuTime < TIME_INTERVAL) {
            return lastSysCpu;
        }

        int[] cpuUsage = getProcessCPURate();
        int systemCpuUsage = cpuUsage[1] / 10;
        float systemCpuUsagePercent = (float) systemCpuUsage / 100;
        lastSysCpu = systemCpuUsagePercent;
        lastSysCpuTime = currentTimeMillis;
        return systemCpuUsagePercent;
    }

    static int[] getProcessCPURate(){
        if (sCpuUsageMeasurer == null) {
            sCpuUsageMeasurer = new CpuUsageMeasurer();
        }
        if (mFirstTimeRun) {
            mFirstTimeRun = false;
            sCpuUsageMeasurer.getCpuUsage();
            return new int[] {0, 0};
        }

        return sCpuUsageMeasurer.getCpuUsage();
    }

    public static float getAppMemory() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (sRunningMemCheck || (lastMemCheckTimeStamps != 0 && currentTimeMillis - lastMemCheckTimeStamps < TIME_INTERVAL)) {
            ///<请求间隔小于15秒的，直接返回上一次的计算结果。
            return (float)lastMemUsage;
        }
        sRunningMemCheck = true;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int totalPss = 0;
                long ts = System.currentTimeMillis();
                sRunningMemCheck = false;
                try {
                    Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                    Debug.getMemoryInfo(memoryInfo);
                    totalPss = memoryInfo.getTotalPss();
                    lastMemCheckTimeStamps = currentTimeMillis;
                    lastMemUsage = totalPss/1024;
                } catch (Exception e) {
                    totalPss = 0;
                }
            }
        });
        return (float)lastMemUsage ;
    }
}
