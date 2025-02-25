package com.example.alwaysspring.api;

import com.example.alwaysspring.model.Board;
import com.example.alwaysspring.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardApi {
//    @GET("api/board")
//    Call<Boolean> login(
//            @Query("title") String title,
//            @Query("content") String content
//    );

    // 보드 조회
    @GET("api/boardSelectAll")
    Call<List<Board>> getAllBoard();

    // 보드 입력
    @POST("api/boardInsert")
    Call<Board> createBoard(@Body Board board);
}
