package com.example.alwaysspring.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alwaysspring.R;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.api.UserApi;
import com.example.alwaysspring.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends AppCompatActivity {

    private static final String TAG = "BoardDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);
        TextView datetimeTextView = findViewById(R.id.datetimeTextView);
        TextView viewsTextView = findViewById(R.id.viewsTextView);

        String title = getIntent().getStringExtra("title");
        long userIdx = getIntent().getLongExtra("user_idx", -1);
        String content = getIntent().getStringExtra("content");
        String datetime = getIntent().getStringExtra("b_datetime");
        int views = getIntent().getIntExtra("views", 0);

        titleTextView.setText(title);
        contentTextView.setText(content);
        datetimeTextView.setText(datetime);
        viewsTextView.setText(String.valueOf(views));

        // user_idx를 사용하여 사용자 정보를 조회
        UserApi userApi = RetrofitClient.getInstance().create(UserApi.class);
        Call<User> call = userApi.getUserById(userIdx);
        call.enqueue(new Callback<User>() {
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
            }
        });
    }
}
