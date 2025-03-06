package com.example.alwaysspring;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alwaysspring.api.BoardApi;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.model.Board;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPostActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSubmit;
    private long userIdx; // userIdx를 로그인에서 받아옴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        // View 초기화
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // LoginActivity에서 전달된 userIdx 가져오기
        userIdx = getIntent().getLongExtra("userIdx", userIdx);

        if (userIdx == -1) {
            Toast.makeText(this, "User ID is invalid. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("AddPostActivity", "Received userIdx: " + userIdx); // userIdx 확인용 로그

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddPostActivity.this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    createBoard(title, content);
                }
            }
        });
    }

    private void createBoard(String title, String content) {
        // Retrofit 인스턴스 생성
        Retrofit retrofit = RetrofitClient.getInstance();
        BoardApi boardApi = retrofit.create(BoardApi.class);

        // Board 객체 생성 및 데이터 설정
        Board board = new Board();
        board.setUser_idx(userIdx); // 로그인에서 전달된 userIdx 설정
        board.setTitle(title);
        board.setContent(content);

        // 디버깅용 로그 (전송 데이터 확인)
        Log.d("AddPostActivity", "Sending data to server: ");
        Log.d("AddPostActivity", "userIdx: " + userIdx);
        Log.d("AddPostActivity", "Title: " + title);
        Log.d("AddPostActivity", "Content: " + content);

        // 서버로 요청
        Call<Board> call = boardApi.createBoard(board);
        call.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 서버 응답 데이터 처리
                    Board responseBody = response.body();
                    Log.d("AddPostActivity", "Response success:");
                    Log.d("AddPostActivity", "b_datetime: " + responseBody.getB_datetime().toString());

                    Toast.makeText(AddPostActivity.this, "Post created successfully", Toast.LENGTH_SHORT).show();
                    finish(); // 액티비티 종료
                } else {
                    // 실패 로그
                    Log.e("AddPostActivity", "Response failed. Code: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("AddPostActivity", "Error body: " + errorBody);
                            Toast.makeText(AddPostActivity.this, "Server Error: " + errorBody, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("AddPostActivity", "Error parsing error body", e);
                            Toast.makeText(AddPostActivity.this, "Error parsing server response.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddPostActivity.this, "Failed to create post: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                // 네트워크 오류 로그
                Log.e("AddPostActivity", "Network error: " + t.getMessage(), t);
                Toast.makeText(AddPostActivity.this, "Error occurred while creating post. Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
