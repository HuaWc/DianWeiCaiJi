package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.KnowledgeModel;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LookUpKnowledgeBaseActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.all)
    LinearLayout all;

    private String id;
    private KnowledgeModel info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_look_up_knowledge_base);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        getData();
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpKnowledgeManagerShow, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(FastJsonUtil.getString(json, "model"), KnowledgeModel.class);
                initData();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initData() {
        if (info == null) {
            return;
        }
        tv1.setText(StringUtil.isEmpty(info.getTheme()) ? "" : info.getTheme());
        tv2.setText(StringUtil.isEmpty(info.getCode()) ? "" : info.getCode());
        tv3.setText(StringUtil.isEmpty(info.getMap().getKnowledgeType()) ? "" : info.getMap().getKnowledgeType());//事件分类定义
        tv4.setText(StringUtil.isEmpty(info.getMap().getKnowledgeClass()) ? "" : info.getMap().getKnowledgeClass());//故障类型
        tv5.setText(StringUtil.isEmpty(info.getCreateTime()) ? "" : info.getCreateTime());
        tv6.setText(StringUtil.isEmpty(info.getMap().getAuthor()) ? "" : info.getMap().getAuthor());
        tv7.setText(info.getCount() + "");
        tv8.setText(StringUtil.isEmpty(info.getAppearance()) ? "" : info.getAppearance());
        //tv9.setText(StringUtil.isEmpty(info.getTheme())?"":info.getTheme());//故障原因
        tv10.setText(StringUtil.isEmpty(info.getSolution()) ? "" : info.getSolution());

    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getString("id");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
