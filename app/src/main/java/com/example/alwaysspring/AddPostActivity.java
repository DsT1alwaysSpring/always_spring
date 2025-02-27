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
    private int userIdx = 1; // 예시로 설정한 userIdx. 실제로는 로그인한 사용자 ID로 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSubmit = findViewById(R.id.buttonSubmit);

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

        Board board = new Board();
        board.setUser_idx(1L); // userIdx 설정
        board.setTitle(title);
        board.setContent(content);

        Call<Board> call = boardApi.createBoard(board);
        call.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AddPostActivity.this, "Post created successfully", Toast.LENGTH_SHORT).show();
                    finish(); // 액티비티 종료
                } else {
                    Log.e("AddPostActivity", "Response code: " + response.code());
                    Toast.makeText(AddPostActivity.this, "Failed to create post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {
                Log.e("AddPostActivity", "Error: " + t.getMessage());
                Toast.makeText(AddPostActivity.this, "Error occurred while creating post", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
