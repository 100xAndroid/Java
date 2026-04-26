package cz.stokratandroid.recyclerview4;


import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PresouvaniHelper extends ItemTouchHelper.Callback {

    private PresunUkoncenUpozorneni contract;

    public PresouvaniHelper(PresunUkoncenUpozorneni contract) {
        this.contract = contract;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        contract.polozkaPresunuta(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    // interface ktery slouzi k upozorneni adapteru, ze doslo ke zmene v umisteni polozek
    public interface PresunUkoncenUpozorneni {
        void polozkaPresunuta(int oldPosition, int newPosition);

        void polozkaOdsunuta(int position);
    }
}
