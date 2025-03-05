package com.example.alwaysspring;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.api.UserApi;
import com.example.alwaysspring.model.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText etName, etBirth, etPhone, etAddress, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etBirth = findViewById(R.id.etBirth);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String birth = etBirth.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || birth.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 생년월일을 LocalDate로 변환 (예외 처리 포함)
        LocalDate birthDate = convertToLocalDate(birth);
        if (birthDate == null) {
            Toast.makeText(this, "생년월일 형식이 잘못되었습니다. (예: 1995-03-05)", Toast.LENGTH_SHORT).show();
            return;
        }

        // User 객체 생성 및 API 호출
        User user = new User(name, null, birth, phone, address, password);

        UserApi userApi = RetrofitClient.getInstance().create(UserApi.class);
        Call<User> call = userApi.createUsers(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, "Response Code: " + response.code());
                    Toast.makeText(RegisterActivity.this, "회원가입 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 입력값을 LocalDate로 변환하는 메서드
    private LocalDate convertToLocalDate(String birth) {
        try {
            // 생년월일이 "601212"처럼 입력되었을 경우 변환
            if (birth.matches("\\d{6}")) { // 6자리 숫자인 경우
                String yearPrefix = (Integer.parseInt(birth.substring(0, 2)) > 30) ? "19" : "20";
                birth = yearPrefix + birth.substring(0, 2) + "-" + birth.substring(2, 4) + "-" + birth.substring(4, 6);
            }

            // 날짜 포맷을 yyyy-MM-dd로 변환 후 LocalDate 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(birth, formatter);
        } catch (Exception e) {
            Log.e("RegisterActivity", "날짜 변환 오류: " + e.getMessage());
            return null;
        }
    }
}
