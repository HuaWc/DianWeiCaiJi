package com.hwc.dwcj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.fragment.AuditFragment;
import com.hwc.dwcj.fragment.DossierFragment;
import com.hwc.dwcj.fragment.DossierNextFragment;
import com.hwc.dwcj.fragment.MapFragment;
import com.hwc.dwcj.fragment.SettingFragment;
import com.hwc.dwcj.updata.CretinAutoUpdateUtils;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */

public class PointCheckActivity extends BaseActivity {


    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.iv_tab_main)
    ImageView ivTabMain;
    @BindView(R.id.tv_tab_main)
    TextView tvTabMain;
    @BindView(R.id.rl_tab_main)
    RelativeLayout rlTabMain;
    @BindView(R.id.iv_tab_main2)
    ImageView ivTabMain2;
    @BindView(R.id.tv_tab_main2)
    TextView tvTabMain2;
    @BindView(R.id.rl_tab_main2)
    RelativeLayout rlTabMain2;
    @BindView(R.id.iv_tab_main3)
    ImageView ivTabMain3;
    @BindView(R.id.tv_tab_main3)
    TextView tvTabMain3;
    @BindView(R.id.rl_tab_main3)
    RelativeLayout rlTabMain3;
    @BindView(R.id.iv_tab_mine)
    ImageView ivTabMine;
    @BindView(R.id.tv_tab_mine)
    TextView tvTabMine;
    @BindView(R.id.rl_tab_mine)
    RelativeLayout rlTabMine;
    @BindView(R.id.mainbuttom)
    LinearLayout mainbuttom;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    /**
     * fragment
     */
    private MapFragment mMapFragment;
    private DossierFragment dossierFragment;
    private AuditFragment auditFragment;
    private SettingFragment settingFragment;
    private DossierNextFragment dossierNextFragment;


    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private boolean dossierIsNext = false;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_point_check);
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
        ivTabMain.setImageResource(R.mipmap.dts);
        tvTabMain.setTextColor(getResources().getColor(R.color.homeblue));
        mMapFragment = new MapFragment();
        dossierFragment = new DossierFragment();
        auditFragment = new AuditFragment();
        settingFragment = new SettingFragment();
        dossierNextFragment = new DossierNextFragment();
        fragments = new Fragment[]{mMapFragment, dossierFragment, auditFragment, settingFragment, dossierNextFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mMapFragment)
                .add(R.id.fragment_container, dossierFragment).add(R.id.fragment_container, auditFragment).add(R.id.fragment_container, settingFragment).add(R.id.fragment_container, dossierNextFragment).hide(dossierFragment).hide(auditFragment).hide(settingFragment).hide(dossierNextFragment).show(mMapFragment)
                .commit();
    }

    @OnClick({R.id.rl_tab_main, R.id.rl_tab_mine, R.id.rl_tab_main2, R.id.rl_tab_main3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_main:
                index = 0;
                setTabPosision();
                break;
            case R.id.rl_tab_main2:
                index = 1;
                setTabPosision();
                break;
            case R.id.rl_tab_main3:
                index = 2;
                setTabPosision();
                break;
            case R.id.rl_tab_mine:
                index = 3;
                setTabPosision();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        if (MyApplication.getInstance().checkUser()) {
            JPushInterface.setAlias(getApplicationContext(), 1, MyApplication.getInstance().getUserInfo().getMobile());
        } else {
            JPushInterface.setAlias(getApplicationContext(), 1, "");
        }

        AppConfig.checkVersion(mContext, true);*/
    }

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.OPEN_DA_NEXT1:
                String code = (String) center.getData();
                EventBus.getDefault().post(new EventCenter(EventUtil.OPEN_DA_NEXT2, code));
                dossierIsNext = true;
                setDossierNextPage();
                break;
            case EventUtil.EXIT_DA_NEXT:
                dossierIsNext = false;
                setDossierNextPage();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(mContext).destroy();
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

    private void setDossierNextPage() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        if (dossierIsNext) {
            trx.hide(fragments[1]);
            trx.show(fragments[4]).commit();
        } else {
            trx.hide(fragments[4]);
            trx.show(fragments[1]).commit();
        }

    }

    private void setTabPosision() {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            if (currentTabIndex == 1 && dossierIsNext) {
                trx.hide(fragments[4]);
            } else {
                trx.hide(fragments[currentTabIndex]);
            }
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            if (index == 1 && dossierIsNext) {
                trx.show(fragments[4]).commit();
            } else {
                trx.show(fragments[index]).commit();
            }
            currentTabIndex = index;
            switch (currentTabIndex) {
                case 0:
                    ivTabMain.setImageResource(R.mipmap.dts);
                    ivTabMain2.setImageResource(R.mipmap.da);
                    ivTabMain3.setImageResource(R.mipmap.sp);
                    ivTabMine.setImageResource(R.mipmap.dwcj);

                    tvTabMain.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTabMain2.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMain3.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMine.setTextColor(getResources().getColor(R.color.homegray));
                    break;
                case 1:
                    ivTabMain.setImageResource(R.mipmap.dt);
                    ivTabMain2.setImageResource(R.mipmap.das);
                    ivTabMain3.setImageResource(R.mipmap.sp);
                    ivTabMine.setImageResource(R.mipmap.dwcj);

                    tvTabMain.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMain2.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTabMain3.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMine.setTextColor(getResources().getColor(R.color.homegray));
                    break;
                case 2:
                    ivTabMain.setImageResource(R.mipmap.dt);
                    ivTabMain2.setImageResource(R.mipmap.da);
                    ivTabMain3.setImageResource(R.mipmap.sps);
                    ivTabMine.setImageResource(R.mipmap.dwcj);

                    tvTabMain.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMain2.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMain3.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTabMine.setTextColor(getResources().getColor(R.color.homegray));
                    break;
                case 3:
                    ivTabMain.setImageResource(R.mipmap.dt);
                    ivTabMain2.setImageResource(R.mipmap.da);
                    ivTabMain3.setImageResource(R.mipmap.sp);
                    ivTabMine.setImageResource(R.mipmap.dwcjs);

                    tvTabMain.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMine.setTextColor(getResources().getColor(R.color.homeblue));
                    tvTabMain3.setTextColor(getResources().getColor(R.color.homegray));
                    tvTabMain2.setTextColor(getResources().getColor(R.color.homegray));
                    break;
            }

        }


    }

    private static final int REQUEST_CODE_SCAN = 0X01;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // Obtain the return value of HmsScan from the value returned by the onActivityResult method by using ScanUtil.RESULT as the key value.
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    String info = ((HmsScan) obj).getOriginalValue();
                    Bundle bundle = new Bundle();
                    bundle.putString("positionCode", info);
                    toTheActivity(AddCameraActivity.class, bundle);
//                    String uuid = FastJsonUtil.getString(info, "uuid");
//                    String ip = FastJsonUtil.getString(info, "ip");
//                    if (StringUtil.isEmpty(uuid) || StringUtil.isEmpty(ip)) {
//                        MyToastUtils.refreshToast("无效的二维码，请重试");
//                        return;
//                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString("uuid", uuid);
//                    bundle.putString("ip", ip);
//                    bundle.putInt("from", 2);
//                    toTheActivity(ScanConfirmLoginActivity.class, bundle);
                }
                return;
            }
        }
    }


}
