package cz.stokratandroid.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    private String [] data;

    public PageAdapter(@NonNull FragmentActivity fragmentActivity, String [] predanaData) {
        super(fragmentActivity);

        data=predanaData;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Stranka fragment = new Stranka();

        // ziskani retezce z pole, podle pozice
        String strItem = data[position];

        // rozdeleni retezce na casti
        String[] strVersion = strItem.split(":");

        // jmeno resource s logem Androidu
        String resName = strVersion[1].replaceAll(" ", "").toLowerCase();

        // odeslani dat do fragmentu
        Bundle bundle=new Bundle();
        bundle.putString("logo", resName);
        bundle.putString("nazev", strVersion [1]);
        bundle.putString("verze", strVersion [0]);
        bundle.putString("data", strVersion [2]);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
