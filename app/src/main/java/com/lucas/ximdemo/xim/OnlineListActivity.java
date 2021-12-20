package com.lucas.ximdemo.xim;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lucas.xim.XIMManager;
import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.v1.IMCallback;
import com.lucas.xim.v1.IMManager;
import com.lucas.xim.v1.IMValueCallback;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.adapter.OnlineListAdapter;
import com.lucas.ximdemo.xim.network.Network;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public OnlineListAdapter adapter;
    ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinelist);


        RecyclerView mRv = findViewById(R.id.recycle);
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(linearLayoutManager);

        adapter = new OnlineListAdapter(this, mDatas);
        mRv.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();


        XIMManager.getInstance().getOnlineMembers(new IMCallback(new IMValueCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> var1) {
                mDatas.clear();
                mDatas.addAll(var1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int var1, String var2) {

            }
        }));

    }


}