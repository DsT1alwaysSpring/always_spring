package com.example.alwaysspring.api;

import java.util.List;

import com.example.alwaysspring.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface UserApi {
    @GET("api/login")
    Call<User> login(
            @Query("phone") String phone,
            @Query("password") String password

    );

    @GET("api/validateLogin")
    Call<User> loginValidateLogin(
            @Query("phone") String phone,
            @Query("password") String password
    );

    @GET("api/user")
    Call<List<User>> getAllUsers();

    @POST("api/user")
    Call<User> createUsers(@Body User user);
}