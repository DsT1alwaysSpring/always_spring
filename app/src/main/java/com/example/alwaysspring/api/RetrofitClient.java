package com.example.alwaysspring.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true) // 네트워크 재시도 허용
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.22:8080/") // 올바른 BASE_URL 설정
                    .client(client) // OkHttp 클라이언트 추가
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
