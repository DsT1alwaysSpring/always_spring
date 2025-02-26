package com.example.alwaysspring.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.alwaysspring.R;
import com.example.alwaysspring.databinding.FragmentGalleryBinding;
import com.example.alwaysspring.ui.viewpage2.VPAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ViewPager2 viewPager = binding.viewpager; // ViewPager2 참조
        VPAdapter adapter = new VPAdapter(getActivity());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = binding.tabLayout; // TabLayout 참조
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("친구목록");
                            break;
                        case 1:
                            tab.setText("채팅창");
                            break;
                        case 2:
                            tab.setText("내 게시물");
                            break;
                        case 3:
                            tab.setText("정보수정");
                            break;
                    }
                }).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
