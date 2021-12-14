package com.lucas.ximdemo.xim.network;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Description:
 * @Auther: LucasXu
 * @email: 18140041@bjtu.edu.cn
 * @github: https://github.com/LucasXu01
 * @Date: 2021/12/14  11:30 上午
 */
public interface UtilApi {
    //关注导师
    @POST("user/register/")
    Observable<ResponseBody> register(@Body RequestBody info);

}
