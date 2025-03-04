package com.example.alwaysspring.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alwaysspring.R;
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
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textViewLogin;
        final LinearLayout boardContainer = binding.boardContainer;

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
                    Log.d(TAG, "Response: " + boardList.toString());
                    // 게시물 리스트를 순회하며 LinearLayout에 TextView 추가
                    for (Board board : boardList) {
                        View boardView = LayoutInflater.from(getContext()).inflate(R.layout.board_item, boardContainer, false);
                        TextView titleTextView = boardView.findViewById(R.id.FriendNameTextView);
                        TextView contentTextView = boardView.findViewById(R.id.contentTextView);

                        titleTextView.setText(board.getTitle());
                        contentTextView.setText(board.getContent());

                        boardContainer.addView(boardView);
                    }
                } else {
                    Log.e(TAG, "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
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
