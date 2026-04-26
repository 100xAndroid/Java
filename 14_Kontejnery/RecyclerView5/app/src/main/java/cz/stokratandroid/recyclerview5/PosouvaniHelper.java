package cz.stokratandroid.recyclerview5;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PosouvaniHelper extends ItemTouchHelper.SimpleCallback {

    public static final int BUTTON_WIDTH = 200;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    private int odsunutaPolozka = -1;
    private int minuleOdsunutaPolozka = -1;

    NakreslitTlacitko tlacitko1;
    NakreslitTlacitko tlacitko2;
    // private List<UnderlayButton> buttons = new ArrayList<>();

    public PosouvaniHelper(Context context) {
        super(0, ItemTouchHelper.LEFT);
        gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        minuleOdsunutaPolozka = odsunutaPolozka;
        odsunutaPolozka = viewHolder.getAdapterPosition();
        if (odsunutaPolozka == minuleOdsunutaPolozka)
            minuleOdsunutaPolozka=-1;

        vratitOdsunutouPolozku();
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        // hodnota, o kolik musi uzivatel polozku posunout, aby zustala posunuta
        return 200;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        // minimaleni rychlost posouvani
        return 0.1f * defaultValue;
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        // rychlost, kterou musi uzivatel polozku posunout, aby zustala posunuta
        return 5.0f * defaultValue;
    }

    // private int direction = 0;
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        int odsouvanaPolozka = viewHolder.getAdapterPosition();
        if (odsouvanaPolozka < 0) {
            odsunutaPolozka = -1;
            return;
        }
        Log.d("myTag", String.format("dx: %f", dX));
        // Log.d("myTag", String.format("dx: %f, direction: %d", dX, direction));
        float posunX = dX;

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX <= 0) {  // posouvani doleva
            tlacitko1=nakreslitTlacitko1(viewHolder);
            tlacitko2=nakreslitTlacitko2(viewHolder);

            View itemView = viewHolder.itemView;
            posunX = dX * 2 * BUTTON_WIDTH / itemView.getWidth();
            kreslitTlacitka(c, itemView, tlacitko1, tlacitko2, odsouvanaPolozka, posunX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, posunX, dY, actionState, isCurrentlyActive);
    }

    private void kreslitTlacitka(Canvas c, View itemView, NakreslitTlacitko tl1, NakreslitTlacitko tl2, int pos, float posunX) {

        // roztahovani tlacitek
        // float dButtonWidth = (-1) * posunX / 2;
        // tl1.kreslitTlacitko(c, new RectF(itemView.getRight()-dButtonWidth, itemView.getTop(), itemView.getRight(), itemView.getBottom()), pos);
        // tl2.kreslitTlacitko(c, new RectF(itemView.getRight()-dButtonWidth-dButtonWidth, itemView.getTop(), itemView.getRight()-dButtonWidth, itemView.getBottom()), pos);

        // posouvani tlacitek spolu s polozkou
        float hranaPolozky = itemView.getRight()+posunX;
        tl1.kreslitTlacitko(c, new RectF(hranaPolozky, itemView.getTop(), hranaPolozky+200, itemView.getBottom()), pos);
        tl2.kreslitTlacitko(c, new RectF(hranaPolozky+200, itemView.getTop(), hranaPolozky+400, itemView.getBottom()), pos);
    }


    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            tlacitko1.onClick(e.getX(), e.getY());
            tlacitko2.onClick(e.getX(), e.getY());
            return true;
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent e) {
            if (odsunutaPolozka < 0)
                return false;

            // zjistit o kterou polozku jde
            RecyclerView.ViewHolder swipedViewHolder = recyclerView.findViewHolderForAdapterPosition(odsunutaPolozka);
            View swipedItem = swipedViewHolder.itemView;

            // udelat nad polozkou pomyslny obdelnik
            Rect rect = new Rect();
            swipedItem.getGlobalVisibleRect(rect);

            // test, jestli jde o posun a jestli je posun provadeny stale nad polozkou.
            if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_MOVE) {
                Point point = new Point((int) e.getRawX(), (int) e.getRawY());
                if (rect.top < point.y && rect.bottom > point.y)
                    gestureDetector.onTouchEvent(e);
                else {
                    minuleOdsunutaPolozka=odsunutaPolozka;
                    odsunutaPolozka = -1;
                    vratitOdsunutouPolozku();
                }
            }
            return false;
        }
    };

    private void vratitOdsunutouPolozku() {
        if (minuleOdsunutaPolozka > -1) {
            recyclerView.getAdapter().notifyItemChanged(minuleOdsunutaPolozka);
            minuleOdsunutaPolozka = -1;
        }
    }

    public void pripojitRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setOnTouchListener(onTouchListener);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    // public abstract void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons);
    public abstract NakreslitTlacitko nakreslitTlacitko1(RecyclerView.ViewHolder viewHolder);
    public abstract NakreslitTlacitko nakreslitTlacitko2(RecyclerView.ViewHolder viewHolder);

    public interface NakreslitTlacitkoClickListener {
        void onClick(int pos);
    }

    public static class NakreslitTlacitko {
        private String textTlacitka;
        private int barvaTlacitka;
        private NakreslitTlacitkoClickListener clickListener;

        public NakreslitTlacitko(String textTlacitka, int barvaTlacitka, NakreslitTlacitkoClickListener clickListener) {
            this.textTlacitka = textTlacitka;
            this.barvaTlacitka = barvaTlacitka;
            this.clickListener = clickListener;
        }

        private int pozice;
        private RectF clickRegion;

        public boolean onClick(float x, float y) {
            if (clickRegion != null && clickRegion.contains(x, y)) {
                clickListener.onClick(pozice);
                return true;
            }
            return false;
        }

        // vykresleni tlacitka
        public void kreslitTlacitko(Canvas c, RectF umisteni, int pozice) {
            Paint p = new Paint();

            // vykresleni pozadi tlacitka
            p.setColor(barvaTlacitka);
            c.drawRect(umisteni, p);

            // vykresleni textu tlacitka
            p.setColor(Color.WHITE);
            p.setTextSize(Resources.getSystem().getDisplayMetrics().density * 12);
            Rect r = new Rect();
            p.setTextAlign(Paint.Align.LEFT);
            p.getTextBounds(textTlacitka, 0, textTlacitka.length(), r);
            float x = umisteni.width() / 2f - r.width() / 2f;
            float y = umisteni.height() / 2f + r.height() / 2f;
            c.drawText(textTlacitka, umisteni.left + x, umisteni.top + y, p);

            clickRegion = umisteni;
            this.pozice = pozice;
        }
    }
}