package com.lucas.ximdemo.xim.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lucas.xim.XIMManager;
import com.lucas.xim.common.XIMCallback;
import com.lucas.xim.v1.IMCallback;
import com.lucas.ximdemo.R;
import com.lucas.ximdemo.xim.MyUtils;
import com.lucas.ximdemo.xim.OnlineListActivity;
import com.lucas.ximdemo.xim.bean.User;
import com.lucas.ximdemo.xim.network.Network;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    TextView et_login_password;
    TextView et_login_mobile;
    Button bt_login;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_login_mobile = (TextView) findViewById(R.id.et_login_mobile);
        et_login_password = (TextView) findViewById(R.id.et_login_password);
        bt_login = (Button) findViewById(R.id.bt_login);


        bt_login.setOnClickListener(v -> {
            String password = et_login_password.getText().toString().trim();
            String mobile = et_login_mobile.getText().toString().trim();

            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("password", password);
            paramsMap.put("mobile", mobile);

            login(paramsMap);

        });

    }



    /**
     * 登陆
     *
     * @param map
     */
    public void login(Map<String, String> map) {
        //添加contentType
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), GsonUtils.toJson(map));
        disposable = Network.getUtilApi()
                .login(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        String res = responseBody.string();
                        LogUtils.json(res);
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.optString("code").equals("200")) {

                            LogUtils.e("外围系统登陆成功， 开始im长链接登陆");

                            User user = new Gson().fromJson(jsonObject.optString("data"), User.class);
                            MyUtils.getInstance().setUser(user);

                            XIMManager.getInstance().login(user.get_id(), user.getToken(), new IMCallback(new XIMCallback() {
                                @Override
                                public void onSuccess() {
                                    ToastUtils.showLong("登陆成功");
                                    ActivityUtils.startActivity(OnlineListActivity.class);
                                }

                                @Override
                                public void onError(int var1, String var2) {
                                    ToastUtils.showLong("登陆失败");
                                }
                            }));


                        } else {
                            ToastUtils.showLong(jsonObject.optString("message", ""));
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

}