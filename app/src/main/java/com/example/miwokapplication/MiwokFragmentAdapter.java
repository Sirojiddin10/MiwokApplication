package com.example.miwokapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MiwokFragmentAdapter extends FragmentStateAdapter {
    public MiwokFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ColorsFragment();
            case 2:
                return new NumbersFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return new FamilyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
