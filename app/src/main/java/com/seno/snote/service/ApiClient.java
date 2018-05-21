package com.seno.snote.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seno.snote.utility.StringConverter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){

        //membuat interceptor
        Interceptor interceptor =new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("X-Api-Key", "218B41B886DCFC41F90A15A8354080EC").build();
                return chain.proceed(newRequest);
            }
        };
        //membuat client Http
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //membuat gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class,new StringConverter());
        Gson gson = gsonBuilder.create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.104:8080/cicool/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return  retrofit;
    }

}
