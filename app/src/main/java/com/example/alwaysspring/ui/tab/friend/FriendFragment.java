package com.example.alwaysspring.ui.tab.friend;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alwaysspring.R;
import com.example.alwaysspring.api.FriendsApi;
import com.example.alwaysspring.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendFragment extends Fragment {

    private static final String TAG = "MyFriendsFragment";
    private long userIdx; // 로그인한 사용자의 userIdx

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.fragment_friend, container, false);
        final LinearLayout myFriendsListContainer = root.findViewById(R.id.myFriendsContainer);

        // Retrofit 인스턴스 생성
        Retrofit retrofit = RetrofitClient.getInstance();
        FriendsApi friendsApi = retrofit.create(FriendsApi.class);

        userIdx = getActivity().getIntent().getLongExtra("userIdx", -1);

        // API 호출
        Call<List<Friends>> call = friendsApi.getConfirmedFriends((int) userIdx);
        call.enqueue(new Callback<List<Friends>>() {
            @Override
            public void onResponse(Call<List<Friends>> call, Response<List<Friends>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Friends> friendList = response.body();
                    Log.d(TAG, "Response: " + friendList.toString());

                    List<String> friendsItem = new ArrayList<>();

                    myFriendsListContainer.removeAllViews();

                    for (Friends friend : friendList) {
                        friendsItem.add(friend.getFriendName());

                        // friendName을 TextView에 설정하여 LinearLayout에 추가
                        View friendView = LayoutInflater.from(getContext()).inflate(R.layout.my_friend_item, myFriendsListContainer, false);

                        TextView friendNameTextView = friendView.findViewById(R.id.FriendNameTextView);
                        friendNameTextView.setText(friend.getFriendName());

                        myFriendsListContainer.addView(friendView);
                    }
                    Log.d(TAG, "Friend Names: " + friendsItem.toString());

                } else {
                    Log.e(TAG, "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Friends>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });


        return root;
    }

}
