package com.hwc.dwcj.activity.second;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.ItemTestAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.util.MyDragCallBack;
import com.hwc.dwcj.util.OnRecyclerItemClickListener;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/8/20.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class TestActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.icon_back)
    ImageView iconBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.rel_con)
    RelativeLayout relCon;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    @BindView(R.id.tv_play)
    TextView tvPlay;

    List<String> videos;
    ItemTestAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void initLogic() {
        initBar();
        videos = new ArrayList<>();
        adapter = new ItemTestAdapter(videos);
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        rvVideo.setAdapter(adapter);
        for(int i = 1;i<11;i++){
            videos.add("视频资源" + i);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                return false;
            }
        });

        MyDragCallBack myCallBack = new MyDragCallBack(adapter, videos);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(rvVideo);//绑定RecyclerView

        rvVideo.addOnItemTouchListener(new OnRecyclerItemClickListener(rvVideo) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (vh.getLayoutPosition() != videos.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });

        myCallBack.setDragListener(new MyDragCallBack.DragListener() {
            @Override
            public void playState(boolean play) {
                if (play) {
                    //在区域
                    tvPlay.setText("放手以播放");
                } else {
                    tvPlay.setText("拖动到此处播放");
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    tvPlay.setVisibility(View.VISIBLE);
                } else {
                    tvPlay.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {
                refreshLayout();
            }
        });
    }

    /**
     * 刷新地理位置等布局
     */
    private void refreshLayout() {
/*        //判断提醒谁布局看是否需要下移
        int row = postArticleImgAdapter.getItemCount() / 3;
        row = 0 == postArticleImgAdapter.getItemCount() % 3 ? row : row + 1;
        row = 4 == row ? 3 : row;//row最多为三行
        int marginTop = (getResources().getDimensionPixelSize(R.dimen.article_img_margin_top)
                + getResources().getDimensionPixelSize(R.dimen.article_img_dimens))
                * row
                + getResources().getDimensionPixelSize(R.dimen.article_post_et_h)
                + 10;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLinearLayout.getLayoutParams();
        params.setMargins(0, marginTop, 0, 0);
        mLinearLayout.setLayoutParams(params);*/
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
