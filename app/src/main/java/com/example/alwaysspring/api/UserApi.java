package com.example.alwaysspring.api;

import java.util.List;

import com.example.alwaysspring.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Path;
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

    @POST("api/user/create")
    Call<User> createUsers(@Body User user);

   @GET("/api/user/{userIdx}")
   Call<User> getUserById(@Path("userIdx") long userIdx);


    // 회원 정보 수정 (PUT 요청 추가)
    @PUT("/api/user/{userIdx}")
    Call<User> updateUser(@Path("userIdx") long userIdx, @Body User user);

    // 회원 탈퇴 (DELETE 요청 추가)
    @DELETE("/api/user/{userIdx}")
    Call<Void> deleteUser(@Path("userIdx") long userIdx);



}