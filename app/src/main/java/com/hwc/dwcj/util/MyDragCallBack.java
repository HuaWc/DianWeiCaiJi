package com.hwc.dwcj.util;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.ItemTestAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Christ on 2021/10/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class MyDragCallBack extends ItemTouchHelper.Callback {


    private int dragFlags;
    private int swipeFlags;
    private ItemTestAdapter adapter;
    private List<String> videos;
    private boolean up;//手指抬起标记位


    public MyDragCallBack(ItemTestAdapter adapter, List<String> videos) {
        this.adapter = adapter;
        this.videos = videos;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //判断 recyclerView的布局管理器数据

        dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        swipeFlags = 0;//0则不响应事件
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
        int toPosition = target.getAdapterPosition();//得到目标position
        if (toPosition == videos.size() - 1 || videos.size() - 1 == fromPosition) {
            return true;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(videos, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(videos, i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 设置是否支持长按拖拽
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 当用户与item的交互结束并且item也完成了动画时调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        adapter.notifyDataSetChanged();
        initData();
        if (dragListener != null) {
            dragListener.clearView();
        }
    }

    /**
     * 重置
     */
    private void initData() {
        if (dragListener != null) {
            dragListener.playState(false);
            dragListener.dragState(false);
        }
        up = false;
    }

    /**
     * 自定义拖动与滑动交互
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (null == dragListener) {
            return;
        }

        if (dY >= (recyclerView.getHeight()
                - viewHolder.itemView.getBottom()//item底部距离recyclerView顶部高度
                - CommonUtils.getPixelById(R.dimen.article_post_delete))) {//拖到删除处
            dragListener.playState(true);
            if (up) {//在删除处放手，则删除item
                viewHolder.itemView.setVisibility(View.INVISIBLE);//先设置不可见，如果不设置的话，会看到viewHolder返回到原位置时才消失，因为remove会在viewHolder动画执行完成后才将viewHolder删除
                videos.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                initData();
                return;
            }
        } else {//没有到删除处
            if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {//如果viewHolder不可见，则表示用户放手，重置删除区域状态
                dragListener.dragState(false);
            }
            dragListener.playState(false);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && dragListener != null) {
            dragListener.dragState(true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 设置手指离开后ViewHolder的动画时间，在用户手指离开后调用
     *
     * @param recyclerView
     * @param animationType
     * @param animateDx
     * @param animateDy
     * @return
     */
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        //手指放开
        up = true;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    public interface DragListener {
        /**
         * 用户是否将 item拖动到播放处，根据状态改变颜色
         *
         * @param play
         */
        void playState(boolean play);

        /**
         * 是否于拖拽状态
         *
         * @param start
         */
        void dragState(boolean start);

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         */
        void clearView();
    }

    private DragListener dragListener;

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

}
