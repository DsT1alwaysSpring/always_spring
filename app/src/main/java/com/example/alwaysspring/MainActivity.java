package com.example.alwaysspring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.alwaysspring.databinding.ActivityMainBinding;
import com.example.alwaysspring.ui.home.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private  String name;
    private long userIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰 바인딩 초기화
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SharedPreferences에서 userIdx 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userIdx = sharedPreferences.getLong("userIdx", -1);

        // LoginActivity에서 전달된 userIdx가 있다면 SharedPreferences에 저장
        long intentUserIdx = getIntent().getLongExtra("userIdx", -1);
        if (intentUserIdx != -1) {
            userIdx = intentUserIdx;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("userIdx", userIdx);
            editor.apply();
        }

        // userIdx 유효성 확인
        if (userIdx == -1) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // 툴바 설정
        setSupportActionBar(binding.appBarMain.toolbar);

        // 플로팅 버튼 클릭 시 AddPostActivity 실행
        binding.appBarMain.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            intent.putExtra("userIdx", userIdx); // userIdx 전달
            startActivity(intent);
        });

        // 드로어 레이아웃 및 내비게이션 뷰 설정
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // 각 메뉴 ID를 상위 레벨 목적지로 간주하도록 설정
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        // 내비게이션 컨트롤러 가져오기
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // 앱 바와 내비게이션 컨트롤러 연결
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // 내비게이션 뷰와 내비게이션 컨트롤러 연결
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
