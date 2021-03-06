package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.second.CheckUser;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/6/11.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class StartCheckActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.all)
    LinearLayout all;
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
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.et_bz)
    EditText etBz;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ll_check_part)
    LinearLayout llCheckPart;

    private String id;
    private CheckUser info;
    private int status;//0 ?????????  1 ?????? 2 ??????
    private List<String> options;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_start_check);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        getData();
        initSelect();
    }

    private void initSelect() {
        options = new ArrayList<>();
        options.add("??????");
        options.add("??????");
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (options == null || options.size() == 0) {
                    ToastUtil.toast("????????????");
                    return;
                }
                PickerViewUtils.selectOptions(StartCheckActivity.this, "????????????", options, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvStatus.setText(options.get(options1));
                        status = options1 + 1;
                    }
                });
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        if (status == 0) {
            ToastUtil.toast("???????????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etBz.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????????????????");
            return;
        }


        CheckUser i = new CheckUser();
        i.setId(id);
        i.setCheckStatus(1);
        i.setVerFeedback(status);
        i.setExp1(etBz.getText().toString().trim());
        i.setFeedbackId(MyApplication.getInstance().getUserInfo().getId());
        ApiClient.requestNetPost(this, AppConfig.OpVerificationTaskEdit, "?????????", FastJsonUtil.toJSONString(i), new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_CHECK_LIST));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpVerificationTaskInfo, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(json, CheckUser.class);
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = new Date();
            if (df.parse(StringUtil.dealDateFormat(info.getTaskEndTime())).getTime() > d.getTime()) {
                tv8.setText("??????");
                //??????
            } else {
                //?????????
                tv8.setText("??????");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            tv8.setText("??????");
        }
        tv1.setText(StringUtil.isEmpty(info.getTaskName()) ? "" : info.getTaskName());
        tv2.setText(StringUtil.isEmpty(info.getVerPeopleNames()) ? "" : info.getVerPeopleNames());
        tv4.setText(StringUtil.isEmpty(info.getVerContent()) ? "" : info.getVerContent());
        tv5.setText(StringUtil.isEmpty(info.getTaskrequires()) ? "" : info.getTaskrequires());
        tv6.setText(StringUtil.dealDateFormat(info.getTaskStartTime()) + "~" + StringUtil.dealDateFormat(info.getTaskEndTime()));
        tv7.setText(StringUtil.isEmpty(info.getMap().getAddName()) ? "" : info.getMap().getAddName());
        tv9.setText(StringUtil.isEmpty(info.getAddTime()) ? "" : StringUtil.dealDateFormat(info.getAddTime()));
        tvReason.setText(StringUtil.isEmpty(info.getCheckReason()) ? "" : info.getCheckReason());
        //??????????????????????????????????????????????????????????????????????????????????????????
        if (info.getCheckStatus() == 3) {
            llCheckPart.setVisibility(View.VISIBLE);
        } else {
            llCheckPart.setVisibility(View.GONE);
        }
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
