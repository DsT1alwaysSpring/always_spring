package com.example.alwaysspring.ui.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alwaysspring.MainActivity;
import com.example.alwaysspring.R;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.api.UserApi;
import com.example.alwaysspring.model.User;
import com.example.alwaysspring.RegisterActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvLoginResult = findViewById(R.id.tvLoginResult);

        UserApi api = RetrofitClient.getInstance().create(UserApi.class);

        // 전체 회원 조회
        api.getAllUsers().enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users != null) {
                        Log.d(TAG, "회원 수: " + users.size());
                        tvLoginResult.setText("회원 수 조회 성공 : " + users.size());
                    } else {
                        Log.e(TAG, "응답 본문이 null입니다.");
                    }
                } else {
                    Log.e(TAG, "응답 실패: " + response.code());
                    tvLoginResult.setText("회원 수 조회 실패");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "네트워크 오류: " + t.getMessage(), t);
            }
        });
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickedLogin(View view) {
        TextView tvMemberNum = findViewById(R.id.etPhone);  // 전화번호
        TextView tvPassword = findViewById(R.id.etPassword);    // 비밀번호
        TextView tvLoginResult = findViewById(R.id.tvLoginResult);

        String phone = tvMemberNum.getText().toString();
        String password = tvPassword.getText().toString();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "전화번호와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        UserApi api = RetrofitClient.getInstance().create(UserApi.class);
        Call<User> call = api.login(phone, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    if (user != null) {
                        // user_idx 확인 로그 추가
                        Log.d("LoginActivity", "Received user_idx: " + user.getUserIdx());
                        Log.d("LoginActivity", "User Name: " + user.getName());
                        Log.d("LoginActivity", "User Phone: " + user.getPhone());

                        tvLoginResult.setText(String.valueOf(user.getName()) + "님 환영합니다.");
                        getSharedPreferences("AppPrefs", MODE_PRIVATE)
                                .edit()
                                .putLong("userIdx", user.getUserIdx()) // SharedPreferences에 저장
                                .apply();

                        // 메인 화면으로 이동
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userIdx", user.getUserIdx()); // user_idx 전달
                        startActivity(intent);
                        finish(); // LoginActivity 종료
                    } else {
                        tvLoginResult.setText("로그인 실패: 전화번호 또는 비밀번호가 올바르지 않습니다.");
                    }
                } else {
                    Log.e("LoginActivity", "Server Error: " + response.code());
                    tvLoginResult.setText("서버 응답 오류: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("LoginActivity", "Network Error: " + t.getMessage());
                tvLoginResult.setText("네트워크 오류: " + t.getMessage());
            }
        });
    }
}
