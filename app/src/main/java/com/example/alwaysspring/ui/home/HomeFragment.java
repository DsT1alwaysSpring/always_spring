package com.example.alwaysspring.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alwaysspring.BoardDetailActivity;
import com.example.alwaysspring.R;
import com.example.alwaysspring.api.BoardApi;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.model.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private TextView textViewLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        textViewLogin = root.findViewById(R.id.textViewLogin);
        LinearLayout boardContainer = root.findViewById(R.id.boardContainer);

        // 로그인 상태 체크 후 UI 업데이트
        updateLoginStatus();

        // Retrofit으로 데이터 가져오기
        Retrofit retrofit = RetrofitClient.getInstance();
        BoardApi boardApi = retrofit.create(BoardApi.class);

        Call<List<Board>> call = boardApi.getAllBoard();
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Board> boardList = response.body();
                    Log.d(TAG, "Response: " + boardList.toString());

                    for (Board board : boardList) {
                        View boardView = LayoutInflater.from(getContext()).inflate(R.layout.board_item, boardContainer, false);
                        TextView titleTextView = boardView.findViewById(R.id.FriendNameTextView);
                        TextView contentTextView = boardView.findViewById(R.id.contentTextView);

                        titleTextView.setText(board.getTitle());
                        contentTextView.setText(board.getContent());

                        boardView.setOnClickListener(v -> {
                            Intent intent = new Intent(getActivity(), BoardDetailActivity.class);
                            intent.putExtra("b_idx", board.getB_idx());
                            startActivity(intent);
                        });

                        boardContainer.addView(boardView);
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

    @Override
    public void onResume() {
        super.onResume();
        // 화면이 다시 보일 때 로그인 상태 업데이트
        updateLoginStatus();
    }

    private void updateLoginStatus() {
        if (getActivity() == null) return;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        long userIdx = sharedPreferences.getLong("userIdx", -1);
        String name = sharedPreferences.getString("name", null);

        if (userIdx != -1 && name != null) { // 로그인 상태 체크
            Log.d(TAG, "Loaded user name: " + name); // 확인을 위한 로그
            textViewLogin.setText(name + "님");
            textViewLogin.setOnClickListener(null);
        } else {
            textViewLogin.setText("로그인/회원가입");
            textViewLogin.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            });
        }
    }
}
