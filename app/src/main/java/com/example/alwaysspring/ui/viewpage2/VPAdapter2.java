package com.example.alwaysspring.ui.viewpage2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.alwaysspring.ui.tap2.myposts.MyPostsFragment;
import com.example.alwaysspring.ui.tap2.popular.PopularFragment;
import com.example.alwaysspring.ui.tap2.recent.RecentFragment;

import java.util.ArrayList;

public class VPAdapter2 extends FragmentStateAdapter {
    private ArrayList<Fragment> items;

    public VPAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        items = new ArrayList<>();
        items.add(new RecentFragment());
        items.add(new PopularFragment());
        items.add(new MyPostsFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
