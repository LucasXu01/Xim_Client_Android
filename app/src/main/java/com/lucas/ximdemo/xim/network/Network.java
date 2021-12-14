// (c)2016 Flipboard Inc, All Rights Reserved.

package com.lucas.ximdemo.xim.network;


import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class Network {


    private static UtilApi utilApi;
    private static String MyBaseUrl = "http://192.168.1.7:8080/";



    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();



    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();



    public static UtilApi getUtilApi() {
            if (utilApi == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(MyBaseUrl)
//                    .addConverterFactory(gsonConverterFactory)
                        .addCallAdapterFactory(rxJavaCallAdapterFactory)
                        .build();
                utilApi = retrofit.create(UtilApi.class);
            }
            return utilApi;
    }


}
