package com.example.alwaysspring;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alwaysspring.api.BoardApi;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.api.UserApi;
import com.example.alwaysspring.model.Board;
import com.example.alwaysspring.model.User;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity {

    private static final String TAG = "BoardDetailActivity";
    private ProgressBar progressBar;
    private TextView titleTextView, nameTextView, contentTextView, datetimeTextView, viewsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        // XML 요소 연결
        titleTextView = findViewById(R.id.titleTextView);
        nameTextView = findViewById(R.id.nameTextView);
        contentTextView = findViewById(R.id.contentTextView);
        datetimeTextView = findViewById(R.id.datetimeTextView);
        viewsTextView = findViewById(R.id.viewsTextView);
        progressBar = findViewById(R.id.progressBar);

        long b_idx = getIntent().getLongExtra("b_idx", -1);
        Log.d(TAG, "Received b_idx: " + b_idx); // 디버깅 로그 추가
        if (b_idx == -1) {
            Toast.makeText(this, "유효하지 않은 게시글입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        progressBar.setVisibility(View.VISIBLE); // 🔄 로딩 시작

        // 📌 Retrofit으로 서버에서 게시글 데이터 가져오기
        BoardApi boardApi = RetrofitClient.getInstance().create(BoardApi.class);

        // 게시글 조회
        Call<Board> callBoard = boardApi.getBoardBybIdx((int) b_idx);
        callBoard.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Board board = response.body();

                    titleTextView.setText(board.getTitle());
                    contentTextView.setText(board.getContent());

                    if (board.getB_datetime() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String formattedDate = sdf.format(board.getB_datetime());
                        datetimeTextView.setText(formattedDate);
                    } else {
                        datetimeTextView.setText("날짜 정보 없음");
                    }

                    viewsTextView.setText("조회수: " + board.getViews());

                    Long userIdxObj = board.getUser_idx(); // user_idx를 Long으로 안전하게 가져옴
                    if (userIdxObj != null) {
                        long userIdx = userIdxObj.longValue();
                        // 사용자 데이터 가져오기
                        UserApi userApi = RetrofitClient.getInstance().create(UserApi.class);
                        Call<User> callUser = userApi.getUserById(userIdx);
                        callUser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    User user = response.body();
                                    nameTextView.setText(user.getName());
                                } else {
                                    nameTextView.setText("작성자 정보 없음");
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                nameTextView.setText("작성자 정보 없음");
                            }
                        });
                    } else {
                        nameTextView.setText("작성자 정보 없음");
                    }

                    // 보드 조회수 증가
                    increaseBoardViews(board.getB_idx());

                    progressBar.setVisibility(View.GONE); // ✅ 로딩 종료
                } else {
                    Log.e(TAG, "게시글 응답 코드: " + response.code());
                    Toast.makeText(BoardDetailActivity.this, "게시글 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // 로딩 종료
                Log.e(TAG, "게시글 요청 실패: " + t.getMessage());
                Toast.makeText(BoardDetailActivity.this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 조회수 증가 메서드 추가
    private void increaseBoardViews(int bIdx) {
        BoardApi boardApi = RetrofitClient.getInstance().create(BoardApi.class);
        Call<Void> callIncreaseViews = boardApi.increaseBoardViews(bIdx);
        callIncreaseViews.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "조회수 증가 성공");
                } else {
                    Log.e(TAG, "조회수 증가 실패: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "조회수 증가 요청 실패: " + t.getMessage());
            }
        });
    }
}


//            @Override
//            public void onFailure(Call<Board> call, Throwable t) {
//                progressBar.setVisibility(View.GONE); // 로딩 종료
//                Log.e(TAG, "게시글 요청 실패: " + t.getMessage());
//                Toast.makeText(BoardDetailActivity.this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
