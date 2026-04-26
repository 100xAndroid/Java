package cz.stokratandroid.sqlite2;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ZalozkaVerze extends Fragment {

    public ZalozkaVerze() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zalozka_verze, container, false);

        // nacteme data z DB pres adapter do gridu
        MainActivity mainActivity = ((MainActivity)getActivity());
        mainActivity.fragmentZalozkaVerze=view;
        mainActivity.nacistData();

        return view;
    }
}
