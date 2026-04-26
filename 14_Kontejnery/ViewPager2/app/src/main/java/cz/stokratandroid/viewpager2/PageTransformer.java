package cz.stokratandroid.viewpager2;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class PageTransformer implements ViewPager2.PageTransformer{

    @Override
    public void transformPage(View view, float position) {

        // transformace 3d rotace frame
        view.setRotationY(-position*35f);

        /*
        // jiny typ transformace - zmensovani mizejiciho frame
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        view.setScaleX(normalizedposition / 2 + 0.5f);
        view.setScaleY(normalizedposition / 2 + 0.5f);
        */

    }
}
