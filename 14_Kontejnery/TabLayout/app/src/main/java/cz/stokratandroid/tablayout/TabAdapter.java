package cz.stokratandroid.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter  extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ZalozkaAndroid();
            case 1: return new ZalozkaVerze();
            case 2: return new ZalozkaApi();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}