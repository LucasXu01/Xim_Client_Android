package com.lucas.ximdemo.xim;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.v1.IMManager;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.adapter.OnlineListAdapter;
import com.lucas.ximdemo.xim.network.Network;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class OnlineListActivity extends AppCompatActivity {


    ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinelist);

        generateDatas();

        RecyclerView mRv = findViewById(R.id.recycle);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(linearLayoutManager);

        OnlineListAdapter adapter = new OnlineListAdapter(this, mDatas);
        mRv.setAdapter(adapter);


    }

    private void generateDatas() {
        for (int i = 1; i <= 100; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }


}