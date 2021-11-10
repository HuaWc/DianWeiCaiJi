package com.hwc.dwcj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.fragment.FirstHomeFragment;
import com.hwc.dwcj.fragment.FirstPersonalFragment;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.poll.PollingService;
import com.hwc.dwcj.poll.PollingUtils;
import com.hwc.dwcj.updata.CretinAutoUpdateUtils;
import com.hwc.dwcj.util.EventUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 */

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_tab_main)
    ImageView ivTabMain;
    @BindView(R.id.tv_tab_main)
    TextView tvTabMain;
    @BindView(R.id.rl_tab_main)
    RelativeLayout rlTabMain;
    @BindView(R.id.iv_tab_mine)
    ImageView ivTabMine;
    @BindView(R.id.tv_tab_mine)
    TextView tvTabMine;
    @BindView(R.id.rl_tab_mine)
    RelativeLayout rlTabMine;
    @BindView(R.id.main_bottom)
    LinearLayout mainBottom;
    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    /**
     * fragment
     */
    private FirstHomeFragment mHomeFragment;
    private FirstPersonalFragment personalFragment;

    private ImageView[] icons;
    private TextView[] texts;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initLogic() {
        initTab();
        MyApplication.getInstance().checkVersion(this);
        //getRole();
        PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        icons = new ImageView[2];
        icons[0] = ivTabMain;
        icons[1] = ivTabMine;

        texts = new TextView[2];
        texts[0] = tvTabMain;
        texts[1] = tvTabMine;

        // select first tab
        icons[0].setSelected(true);
        texts[0].setSelected(true);

        mHomeFragment = new FirstHomeFragment();
        personalFragment = new FirstPersonalFragment();
        fragments = new Fragment[]{mHomeFragment, personalFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mHomeFragment)
                .add(R.id.fragment_container, personalFragment).hide(personalFragment).show(mHomeFragment)
                .commit();
    }

    /**
     * on tab clicked
     *
     * @param view
     */
    public void onFirstTabClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_main:
                index = 0;
                break;
            case R.id.rl_tab_mine:
                index = 1;
                break;
            default:
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        icons[currentTabIndex].setSelected(false);
        texts[currentTabIndex].setSelected(false);
        // set current tab selected
        icons[index].setSelected(true);
        texts[index].setSelected(true);
        currentTabIndex = index;
    }

    public void change(int indexs) {
        index = indexs;
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                icons[currentTabIndex].setSelected(false);
                texts[currentTabIndex].setSelected(false);
                // set current tab selected
                icons[index].setSelected(true);
                texts[index].setSelected(true);
                currentTabIndex = index;
            }
        });


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
        EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_MESSAGE_NUMBER));
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(mContext).destroy();
        PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
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

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    //重写onBackPressed()方法,继承自退出的方法
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            // 退出
            finish();
        }

    }

    /**
     * 提前调用下
     * 防止进入具体功能页面之后才检测到token失效，多重弹窗
     */
    private void getRole() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.getUserRole, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }



}
