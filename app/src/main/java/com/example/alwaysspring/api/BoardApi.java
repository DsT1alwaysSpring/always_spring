package com.example.alwaysspring.api;

import com.example.alwaysspring.model.Board;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoardApi {

    // 보드 조회
    @GET("api/board")
    Call<List<Board>> getAllBoard();

    // 보드 입력
    @POST("api/board")
    Call<Board> createBoard(@Body Board board);


    // 보드 조회수 증가
    @PUT("api/board/{bIdx}/views")
    Call<Void> increaseBoardViews(@Path("bIdx") int bIdx);

    // 보드 인기순 조회
    @GET("api/board/popular")
    Call<List<Board>> getBoardsSortedByViews();

    // 특정 게시물 bIdx로 조회
    @GET("api/board/bIdx")
    Call<Board> getBoardBybIdx(@Query("board") int bIdx);

    // 보드 수정
    @PUT("api/board/{bIdx}")
    Call<Board> updateBoard(@Path("bIdx") int bIdx, @Body Board updatedBoard);

    // 보드 삭제
    @DELETE("api/board/{bIdx}")
    Call<Void> deleteBoard(@Path("bIdx") int bIdx);

    // 내가 작성한 게시글 본인 고유번호로 조회
    @GET("api/board/{userIdx}/boards")
    Call<List<Board>> getBoardsByUserId(@Path("userIdx") int userIdx);
}