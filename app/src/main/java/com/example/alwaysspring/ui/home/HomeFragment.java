package com.example.alwaysspring.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alwaysspring.api.BoardApi;
import com.example.alwaysspring.api.RetrofitClient;
import com.example.alwaysspring.databinding.FragmentHomeBinding;
import com.example.alwaysspring.model.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textViewLogin;
        final TextView titleTextView = binding.titleTextView;
        final TextView contentTextView = binding.contentTextView;

        // textViewLogin을 클릭했을 때 LoginActivity로 이동
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        // Retrofit 인스턴스 생성
        Retrofit retrofit = RetrofitClient.getInstance();
        BoardApi boardApi = retrofit.create(BoardApi.class);

        // API 호출
        Call<List<Board>> call = boardApi.getAllBoard();
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Board> boardList = response.body();
                    // 첫 번째 보드 데이터를 가져옴
                    if (!boardList.isEmpty()) {
                        Board firstBoard = boardList.get(0);
                        titleTextView.setText(firstBoard.getTitle());
                        contentTextView.setText(firstBoard.getContent());
                    }
                } else {
                    // 응답이 성공적이지 않을 때 처리
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                // 요청 실패 시 처리
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
