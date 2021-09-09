package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hwc.dwcj.R;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.second.OpAlarmParam;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.ResultListener;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/8/20.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class TestActivity extends BaseActivity {
    @BindView(R.id.button)
    Button button;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void initLogic() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });
    }

    private void test() {
        List<OpAlarmParam> list = new ArrayList<>();
        OpAlarmParam p = new OpAlarmParam();
        p.setDeviceKind("");
        p.setAssetNature("");
        p.setAlarmTime("2021-08-20 16:56:34");
        p.setGroupId("");
        p.setIp("34.72.59.19");
        p.setPictureUrl("");

        p.setTaskSubId("");
        p.setAlarmName("test");

        p.setDeviceCode("ZC34150214001199130015");
        p.setAlarmCode("");
        p.setDeviceId("");

        p.setDeviceName("");
        p.setAssetType("/server/Linux");
        p.setAlarmStatus("");
        p.setHandlePersonId("");

        p.setAlarmSource("安全系统");
        p.setFaultType("");
        p.setAlarmLevel("次要");

        p.setAlarmPersonId("");
        p.setExp1("");
        p.setAlarmReason("test");
        p.setIsDel(0L);
        p.setTaskId("");
        list.add(p);


        ApiClient.requestNetPost(this, "http://192.168.1.116:9001/v1/wfm/ucm/OpAlarmInfo/AlarmReport", "", FastJsonUtil.toJSONString(list), new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                Log.d("------",json);
            }

            @Override
            public void onFailure(String msg) {
                Log.d("------",msg);

            }
        });
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
