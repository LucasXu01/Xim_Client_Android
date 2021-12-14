package com.lucas.ximdemo.xim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucas.xim.XimDispatcher;
import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.v1.IMManager;
import com.lucas.xim.ximcore.client.console.LoginConsoleCommand;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.network.Network;

import org.json.JSONObject;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView et_register_nickname;
    TextView et_register_password;
    TextView et_register_mobile;
    TextView et_login_password;
    TextView et_login_mobile;
    Button bt_login;
    Button bt_register;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_register_nickname = (TextView) findViewById(R.id.et_register_nickname);
        et_register_password = (TextView) findViewById(R.id.et_register_password);
        et_register_mobile = (TextView) findViewById(R.id.et_register_mobile);
        et_login_mobile = (TextView) findViewById(R.id.et_login_mobile);
        et_login_password = (TextView) findViewById(R.id.et_login_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_register.setOnClickListener(v -> {
            String nickname = et_register_nickname.getText().toString().trim();
            String password = et_register_password.getText().toString().trim();
            String mobile = et_register_mobile.getText().toString().trim();

            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("nickname", nickname);
            paramsMap.put("password", password);
            paramsMap.put("mobile", mobile);

            register(paramsMap);

        });

        bt_login.setOnClickListener(v -> {
            String password = et_login_password.getText().toString().trim();
            String mobile = et_login_mobile.getText().toString().trim();

            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("password", password);
            paramsMap.put("mobile", mobile);

            login(paramsMap);

        });

    }


    // 注册
    public void register(HashMap<String, String> map) {
        //添加contentType
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), GsonUtils.toJson(map));
        disposable = Network.getUtilApi()
                .register(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        String res = responseBody.string();
                        LogUtils.json(res);
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.optString("status").equals("200")){
                            ToastUtils.showLong("注册成功");
                        }else{
                            ToastUtils.showLong(jsonObject.optString("message",""));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        String res = throwable.getMessage().toString();
                        LogUtils.e(res);
                    }
                });
    }

    public void login(Map<String, String> map){
        ActivityUtils.startActivity(OnlineListActivity.class);
//        IMManager.getInstance().login("123", "123", new XIMCallback() {
//            @Override
//            public void onSuccess() {
//                ToastUtils.showLong("登陆成功");
//            }
//
//            @Override
//            public void onError(int var1, String var2) {
//                ToastUtils.showLong("登陆失败");
//            }
//        });


    }

}