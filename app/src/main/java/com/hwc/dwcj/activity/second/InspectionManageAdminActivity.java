package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.fragment.second.ChangeAdminFragment;
import com.hwc.dwcj.fragment.second.ChangeFragment;
import com.hwc.dwcj.fragment.second.CheckAdminFragment;
import com.hwc.dwcj.fragment.second.CheckFragment;
import com.hwc.dwcj.fragment.second.GuaranteeAdminFragment;
import com.hwc.dwcj.fragment.second.GuaranteeFragment;
import com.hwc.dwcj.fragment.second.InspectionAdminFragment;
import com.hwc.dwcj.fragment.second.InspectionFragment;
import com.zds.base.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 巡检管理
 */
public class InspectionManageAdminActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.iv_tab1)
    ImageView ivTab1;
    @BindView(R.id.tv_tab1)
    TextView tvTab1;
    @BindView(R.id.rl_tab1)
    RelativeLayout rlTab1;
    @BindView(R.id.iv_tab2)
    ImageView ivTab2;
    @BindView(R.id.tv_tab2)
    TextView tvTab2;
    @BindView(R.id.rl_tab2)
    RelativeLayout rlTab2;
    @BindView(R.id.iv_tab3)
    ImageView ivTab3;
    @BindView(R.id.tv_tab3)
    TextView tvTab3;
    @BindView(R.id.rl_tab3)
    RelativeLayout rlTab3;
    @BindView(R.id.iv_tab4)
    ImageView ivTab4;
    @BindView(R.id.tv_tab4)
    TextView tvTab4;
    @BindView(R.id.rl_tab4)
    RelativeLayout rlTab4;
    @BindView(R.id.main_button)
    LinearLayout mainButton;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    private InspectionAdminFragment inspectionFragment;
    private ChangeAdminFragment changeFragment;
    private CheckAdminFragment checkFragment;
    private GuaranteeAdminFragment guaranteeFragment;

    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_inspection_management_admin);
    }

    @Override
    protected void initLogic() {
        initTab();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        currentTabIndex = 0;
        index = 0;
        ivTab1.setImageResource(R.mipmap.dts);
        tvTab1.setTextColor(getResources().getColor(R.color.homeblue));
        inspectionFragment = new InspectionAdminFragment();
        changeFragment = new ChangeAdminFragment();
        checkFragment = new CheckAdminFragment();
        guaranteeFragment = new GuaranteeAdminFragment();
        fragments = new Fragment[]{inspectionFragment, changeFragment, checkFragment, guaranteeFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, inspectionFragment)
                .add(R.id.fragment_container, changeFragment).add(R.id.fragment_container, checkFragment).add(R.id.fragment_container, guaranteeFragment).hide(changeFragment).hide(checkFragment).hide(guaranteeFragment).show(inspectionFragment)
                .commit();
    }

    @OnClick({R.id.rl_tab1, R.id.rl_tab2, R.id.rl_tab3, R.id.rl_tab4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tab1:
                index = 0;
                setTabPosition();
                break;
            case R.id.rl_tab2:
                index = 1;
                setTabPosition();
                break;
            case R.id.rl_tab3:
                index = 2;
                setTabPosition();
                break;
            case R.id.rl_tab4:
                index = 3;
                setTabPosition();
                break;
        }
    }

    private void setTabPosition() {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
            switch (currentTabIndex) {
                case 0:
                    ivTab1.setImageResource(R.mipmap.dts);
                    ivTab2.setImageResource(R.mipmap.da);
                    ivTab3.setImageResource(R.mipmap.sp);
                    ivTab4.setImageResource(R.mipmap.dwcj);

                    tvTab1.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTab2.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab3.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab4.setTextColor(getResources().getColor(R.color.homegray));
                    break;
                case 1:
                    ivTab1.setImageResource(R.mipmap.dt);
                    ivTab2.setImageResource(R.mipmap.das);
                    ivTab3.setImageResource(R.mipmap.sp);
                    ivTab4.setImageResource(R.mipmap.dwcj);

                    tvTab1.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab2.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTab3.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab4.setTextColor(getResources().getColor(R.color.homegray));
                    break;
                case 2:
                    ivTab1.setImageResource(R.mipmap.dt);
                    ivTab2.setImageResource(R.mipmap.da);
                    ivTab3.setImageResource(R.mipmap.sps);
                    ivTab4.setImageResource(R.mipmap.dwcj);

                    tvTab1.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab2.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab3.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTab4.setTextColor(getResources().getColor(R.color.homegray));
                    break;
                case 3:
                    ivTab1.setImageResource(R.mipmap.dt);
                    ivTab2.setImageResource(R.mipmap.da);
                    ivTab3.setImageResource(R.mipmap.sp);
                    ivTab4.setImageResource(R.mipmap.dwcjs);

                    tvTab1.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab2.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTab3.setTextColor(getResources().getColor(R.color.homegray));
                    tvTab4.setTextColor(getResources().getColor(R.color.homegray));
                    break;
            }

        }


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
