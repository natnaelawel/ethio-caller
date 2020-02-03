package com.example.nathaniel.recyclerview;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    int dir;

    // getDefaultUIUtil() will be used by ItemTouchHelper to
    // detect whenever there is UI change on the view.
    // We use this function to keep the background view in a static
    // position and move the foreground view.

    /*
    * In onChildDrawOver() the x-position of the foreground view is changed while user is
    * swiping the view
    *
    *
    * RecyclerItemTouchHelperListener interface is used to send the callback to implementing activity
    * here the listener will be treggered in mainactivity once the swipe is done
    * */


    private RecyclerItemTouchHelperListener listener;
    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener= listener;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {

        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder) {
        final View foregroundView=((MyLogAdapter.MyLogHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);

//        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView=((MyLogAdapter.MyLogHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX/2,dY,actionState,isCurrentlyActive);
        //        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView=((MyLogAdapter.MyLogHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
//        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder!=null){
            final View forgroundView =((MyLogAdapter.MyLogHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(forgroundView);
        }
//        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());


    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

    }
}
