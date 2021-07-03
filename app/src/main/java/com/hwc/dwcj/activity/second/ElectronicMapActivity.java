package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.fragment.MapFragment;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/7/1.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ElectronicMapActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    private MapFragment mMapFragment;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_electronic_map);
    }

    @Override
    protected void initLogic() {
        Bundle bundle = new Bundle();
        bundle.putInt("from",1);
        mMapFragment = new MapFragment();
        mMapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mMapFragment).show(mMapFragment)
                .commit();
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
