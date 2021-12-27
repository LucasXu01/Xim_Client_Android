package com.lucas.ximdemo.xim.ui;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.OnlineListActivity;
import com.lucas.ximdemo.xim.XimUtil;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        BarUtils.setNavBarVisibility(this, false);

        ThreadUtils.runOnUiThreadDelayed(()->{
            if (XimUtil.getInstance().getUser() ==null){
                ActivityUtils.startActivity(LoginActivity.class);
            }else {
                ActivityUtils.startActivity(OnlineListActivity.class);
            }

            finish();
        },1500);

    }




}