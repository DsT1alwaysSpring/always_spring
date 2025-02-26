package com.example.alwaysspring.api;

import com.example.alwaysspring.model.Board;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BoardApi {

    // 모든 보드 조회
    @GET("api/board")
    Call<List<Board>> getAllBoard();

    // 특정 보드 조회
    @GET("api/board/{bIdx}")
    Call<Board> getBoardById(@Path("bIdx") int bIdx);

    // 보드 생성
    @POST("api/board")
    Call<Board> createBoard(@Body Board board);
}
