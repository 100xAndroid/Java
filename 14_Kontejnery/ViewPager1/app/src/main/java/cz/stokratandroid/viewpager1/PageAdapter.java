package cz.stokratandroid.viewpager1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new StrankaNougat();
            case 1: return new StrankaOreo();
            case 2: return new StrankaPie();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}