package com.example.alwaysspring.api;

import com.example.alwaysspring.model.User;
import com.example.alwaysspring.ui.tab.friend.Friends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FriendsApi {
    // 사용자 친구(대기/승인 모두) 조회 (GET /api/friends)
    @GET("/api/friends/all/{userIdx}")
    Call<List<Friends>> getFriendsByUserId(@Path("userIdx") int userIdx);

    // 사용자 친구(승락) 조회 (GET /api/friends)
    @GET("/api/friends/{userIdx}")
    Call<List<com.example.alwaysspring.ui.tab.friend.Friends>> getConfirmedFriends(@Path("userIdx") int userIdx);

    // 주소 기반으로 친구 추천
    @GET("/api/friends/FriendRecommendation/{userIdx}")
    Call<List<User>> getFriendRecommendation(@Path("userIdx") int userIdx);

    // 친구 생성 (POST /api/friends)
    @POST("/api/friends")
    Call<Friends> createFriend(@Body Friends friends);

    // [대기친구 승인 시 사용] 친구 설정 업데이트
    @PUT("/api/friends/{id}")
    Call<Friends> updateFriendRequestStatus(@Path("id") int id);

    // [친구신청상태 대기 -> 승락] 본인 FriendRequestStatus 1로 변경과 동시에 (신청자: 친구, 승락자: 본인, FRS: 1) 생성
    @PUT("/api/friends/{userIdx}/{friendIdx}")
    Call<Friends> confirmFriendship(
            @Path("userIdx") int userIdx,
            @Path("friendIdx") int friendIdx
    );

    // idx로 삭제
    @DELETE("/api/friends/{id}")
    Call<Void> deleteFriend(@Path("id") int id);
}
