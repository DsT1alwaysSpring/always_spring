package com.example.alwaysspring.ui.viewpage2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.alwaysspring.ui.tab.chat.ChatFragment;
import com.example.alwaysspring.ui.tab.friend.FriendFragment;
import com.example.alwaysspring.ui.tab.post.PostFragment;
import com.example.alwaysspring.ui.tab.profile.ProfileFragment;

import java.util.ArrayList;

public class VPAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> items;

    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        items = new ArrayList<Fragment>();
        items.add(new FriendFragment());
        items.add(new ChatFragment());
        items.add(new PostFragment());
        items.add(new ProfileFragment());
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
