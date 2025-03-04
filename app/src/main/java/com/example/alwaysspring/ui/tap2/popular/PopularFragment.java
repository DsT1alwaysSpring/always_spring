package com.example.alwaysspring.ui.tap2.popular;

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
import com.example.alwaysspring.api.BoardApi;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.model.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopularFragment extends Fragment {

    private static final String TAG = "PopularFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_popular, container, false);

        final LinearLayout popularBoardContainer = root.findViewById(R.id.popularBoardContainer);

        // Retrofit 인스턴스 생성
        Retrofit retrofit = RetrofitClient.getInstance();
        BoardApi boardApi = retrofit.create(BoardApi.class);

        // API 호출
        Call<List<Board>> call = boardApi.getBoardsSortedByViews();
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Board> boardList = response.body();
                    Log.d(TAG, "Response: " + boardList.toString());
                    // 게시물 리스트를 순회하며 LinearLayout에 TextView 추가
                    for (Board board : boardList) {
                        View boardView = LayoutInflater.from(getContext()).inflate(R.layout.board_item, popularBoardContainer, false);
                        TextView titleTextView = boardView.findViewById(R.id.FriendNameTextView);
                        TextView contentTextView = boardView.findViewById(R.id.contentTextView);

                        titleTextView.setText(board.getTitle());
                        contentTextView.setText(board.getContent());

                        popularBoardContainer.addView(boardView);
                    }
                } else {
                    Log.e(TAG, "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });

        return root;
    }
}
