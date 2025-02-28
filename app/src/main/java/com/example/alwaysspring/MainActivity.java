package com.example.alwaysspring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alwaysspring.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration; // 앱 바 구성 객체
    private ActivityMainBinding binding; // 뷰 바인딩을 위한 객체
    private  long userIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰 바인딩 초기화 및 레이아웃 설정
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // LoginActivity에서 전달된 userIdx 가져오기
        userIdx = getIntent().getLongExtra("userIdx", -1);

        // userIdx 유효성 확인


        // 툴바 설정
        setSupportActionBar(binding.appBarMain.toolbar);

        // 플로팅 액션 버튼 클릭 시 AddPostActivity 실행
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                intent.putExtra("userIdx", userIdx); // userIdx 전달
                startActivity(intent);
            }
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
        // 옵션 메뉴를 생성 (액션 바에 메뉴 추가)
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 내비게이션 컨트롤러를 통해 상위 화면으로 이동
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
