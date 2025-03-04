package com.example.alwaysspring;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alwaysspring.R;
import com.example.alwaysspring.api.BoardApi;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.api.UserApi;
import com.example.alwaysspring.model.Board;
import com.example.alwaysspring.model.User;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity {

    private static final String TAG = "BoardDetailActivity";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);
        TextView datetimeTextView = findViewById(R.id.datetimeTextView);
        TextView viewsTextView = findViewById(R.id.viewsTextView);
        progressBar = findViewById(R.id.progressBar);

        long b_idx = getIntent().getLongExtra("b_idx", -1);
        if (b_idx == -1) {
            Toast.makeText(this, "유효하지 않은 게시글입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        progressBar.setVisibility(View.VISIBLE); // 로딩 시작

        // b_idx를 사용하여 게시글 상세 정보 조회
        BoardApi boardApi = RetrofitClient.getInstance().create(BoardApi.class);
        Call<Board> callBoard = boardApi.getBoardById(b_idx);
        callBoard.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                progressBar.setVisibility(View.GONE); // 로딩 종료

                if (response.isSuccessful() && response.body() != null) {
                    Board board = response.body();

                    titleTextView.setText(board.getTitle());
                    contentTextView.setText(board.getContent());

                    // 날짜 형식 변환
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = sdf.format(board.getB_datetime());
                    datetimeTextView.setText(formattedDate);

                    viewsTextView.setText(String.valueOf(board.getViews()));

                    long userIdx = board.getUser_idx();

                    // user_idx를 사용하여 사용자 정보를 조회
                    UserApi userApi = RetrofitClient.getInstance().create(UserApi.class);
                    Call<User> callUser = userApi.getUserById(userIdx);
                    callUser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                User user = response.body();
                                nameTextView.setText(user.getName());
                            } else {
                                Log.e(TAG, "Response Code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e(TAG, "Error: " + t.getMessage());
                            Toast.makeText(BoardDetailActivity.this, "사용자 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.e(TAG, "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // 로딩 종료
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(BoardDetailActivity.this, "게시글 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
