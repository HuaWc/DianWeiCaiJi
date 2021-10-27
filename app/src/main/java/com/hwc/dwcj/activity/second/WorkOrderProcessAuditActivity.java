package com.hwc.dwcj.activity.second;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.AdapterCameraPhoto;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.FaultAssetInfo;
import com.hwc.dwcj.entity.second.FaultMapInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetCameraImgHttp;
import com.hwc.dwcj.http.GetDictDataHttp;
import com.hwc.dwcj.http.GetWorkOrderImgHttp;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Christ on 2021/6/8.
 * By an amateur android developer
 * Email 627447123@qq.com
 * <p>
 * <p>
 * 工单处理（管理员） - 事件审核 - 流程审核
 */
public class WorkOrderProcessAuditActivity extends BaseActivity {


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
    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;
    @BindView(R.id.tv14)
    TextView tv14;
    @BindView(R.id.tv15)
    TextView tv15;
    @BindView(R.id.tv16)
    TextView tv16;
    @BindView(R.id.tv17)
    TextView tv17;
    @BindView(R.id.tv18)
    TextView tv18;
    @BindView(R.id.tv19)
    TextView tv19;
    @BindView(R.id.tv20)
    TextView tv20;
    @BindView(R.id.tv21)
    TextView tv21;
    @BindView(R.id.tv22)
    TextView tv22;
    @BindView(R.id.tv23)
    TextView tv23;
    @BindView(R.id.rb_agree)
    RadioButton rbAgree;
    @BindView(R.id.rb_refuse)
    RadioButton rbRefuse;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.tv_e1)
    TextView tvE1;
    @BindView(R.id.iv_up1)
    ImageView ivUp1;
    @BindView(R.id.iv_down1)
    ImageView ivDown1;
    @BindView(R.id.tv_e2)
    TextView tvE2;
    @BindView(R.id.iv_up2)
    ImageView ivUp2;
    @BindView(R.id.iv_down2)
    ImageView ivDown2;
    @BindView(R.id.tv_e3)
    TextView tvE3;
    @BindView(R.id.iv_up3)
    ImageView ivUp3;
    @BindView(R.id.iv_down3)
    ImageView ivDown3;
    @BindView(R.id.tv_e4)
    TextView tvE4;
    @BindView(R.id.iv_up4)
    ImageView ivUp4;
    @BindView(R.id.Iv_down4)
    ImageView IvDown4;
    @BindView(R.id.ll_evaluate_part)
    LinearLayout llEvaluatePart;
    @BindView(R.id.et_refuse)
    EditText etRefuse;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv_pd_remark)
    TextView tvPdRemark;
    @BindView(R.id.tv24)
    TextView tv24;
    @BindView(R.id.tv25)
    TextView tv25;
    @BindView(R.id.tv26)
    TextView tv26;
    @BindView(R.id.tv27)
    TextView tv27;
    @BindView(R.id.tv28)
    TextView tv28;
    @BindView(R.id.tv29)
    TextView tv29;
    @BindView(R.id.ll_change_part)
    LinearLayout llChangePart;
    @BindView(R.id.tv30)
    TextView tv30;
    @BindView(R.id.tv31)
    TextView tv31;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv1)
    RecyclerView rv1;

    private String id;
    private FaultMapInfo info;
    private FaultAssetInfo asset;

    private int maxScore = 10;
    private int minScore = 1;
    private int type = 0;


    private List<String> photo1;
    private AdapterCameraPhoto adapter1;
    private List<String> photo2;
    private AdapterCameraPhoto adapter2;

    private List<String> ftPhotos;//工单附图
    private AdapterCameraPhoto ftAdapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_process_audit);

    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    private void initAdapter() {
        photo1 = new ArrayList<>();
        adapter1 = new AdapterCameraPhoto(photo1);
        rv1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv1.setAdapter(adapter1);
        RecyclerViewHelper.recyclerviewAndScrollView(rv1);

        photo2 = new ArrayList<>();
        adapter2 = new AdapterCameraPhoto(photo2);
        rv2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv2.setAdapter(adapter2);
        RecyclerViewHelper.recyclerviewAndScrollView(rv2);

        getData();
        getImgData();
    }

    private void getImgData() {
            GetWorkOrderImgHttp.getImg(id, this, new GetWorkOrderImgHttp.ImgDataListener() {
                @Override
                public void result(String json) {
                    String str = FastJsonUtil.getString(json, "imgPath");
                    if (!StringUtil.isEmpty(str)) {
                        photo2.addAll(Arrays.asList(str.split("!")));
                        adapter2.notifyDataSetChanged();
                    }

                }
            });
    }


    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoInfo, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                info = FastJsonUtil.getObject(FastJsonUtil.getString(json, "OpFaultInfoModel"), FaultMapInfo.class);
                asset = FastJsonUtil.getObject(FastJsonUtil.getString(json, "ofia"), FaultAssetInfo.class);
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
        tvPdRemark.setText(StringUtil.isEmpty(info.getRemark2()) ? "" : info.getRemark2());

        tv1.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());
        tv2.setText(StringUtil.isEmpty(info.getAlarmCode()) ? "" : info.getAlarmCode());
        tv3.setText(StringUtil.isEmpty(info.getMap().getAssetNatureName()) ? "" : info.getMap().getAssetNatureName());
        tv4.setText(StringUtil.isEmpty(info.getMap().getAssetTypeName()) ? "" : info.getMap().getAssetTypeName());
        tv5.setText(StringUtil.isEmpty(info.getMap().getAssetClassName()) ? "" : info.getMap().getAssetClassName());
        tv6.setText(StringUtil.isEmpty(info.getMap().getAlarmSourceName()) ? "" : info.getMap().getAlarmSourceName());
        tv7.setText(StringUtil.isEmpty(info.getMap().getAlarmGradeName()) ? "" : info.getMap().getAlarmGradeName());
        //tv8.setText(StringUtil.isEmpty(info.getCameraFaultType() + "") ? "" : info.getCameraFaultType() + "");
        tv9.setText(StringUtil.isEmpty(info.getMap().getOrgName()) ? "" : info.getMap().getOrgName());
        tv10.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
        tv11.setText(StringUtil.isEmpty(info.getMap().getPositionCode()) ? "" : info.getMap().getPositionCode());
        tv12.setText(StringUtil.isEmpty(info.getMap().getManageIp()) ? "" : info.getMap().getManageIp());
        tv13.setText(StringUtil.isEmpty(info.getAlarmTime()) ? "" : StringUtil.dealDateFormat(info.getAlarmTime()));//发生时间
        tv14.setText(StringUtil.isEmpty(info.getAddTime()) ? "" : StringUtil.dealDateFormat(info.getAddTime()));//派单时间
        tv15.setText(StringUtil.isEmpty(info.getRecoverTime()) ? "" : StringUtil.dealDateFormat(info.getRecoverTime()));//恢复时间
        tv16.setText(info.getMap().getFaultTime() + "");//故障时长
        if (info.getHandleStatus() != null && info.getHandleStatus().equals("DEAL_DONE")) {
            tv17.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());//实际处理人
            tv18.setText(StringUtil.isEmpty(info.getHandleTel()) ? "" : info.getHandleTel());//联系电话
        }

        //tv19.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//协同处理人
        //tv20.setText(StringUtil.isEmpty(info.getAlarmName()) ? "" : info.getAlarmName());//联系电话


        tv21.setText(StringUtil.isEmpty(info.getMap().getClosedLoopStatusName()) ? "工单还未闭环" : info.getMap().getClosedLoopStatusName());//闭环状态
        tv22.setText(StringUtil.isEmpty(info.getMap().getTimeoutTime()) ? "" : info.getMap().getTimeoutTime());//超时闭环时间
        tv23.setText(StringUtil.isEmpty(info.getAlarmRemark()) ? "" : info.getAlarmRemark());//告警发生原因

        if (info.getExp1() != null) {
            getStatus();
        } else {
            llChangePart.setVisibility(View.GONE);
        }
        tv30.setText(StringUtil.isEmpty(info.getMap().getHandlePersionName()) ? "" : info.getMap().getHandlePersionName());
        tv31.setText(StringUtil.isEmpty(info.getRemark()) ? "" : info.getRemark());

        getPhotoData();
    }

    private void getPhotoData() {
        if (StringUtil.isEmpty(info.getMap().getPicture())) {
            return;
        }
        ftPhotos = new ArrayList<>();
        ftAdapter = new AdapterCameraPhoto(ftPhotos);
        rv1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rv1.setAdapter(ftAdapter);
        GetWorkOrderImgHttp.getImgByFtpAddress(info.getMap().getPicture(), this, new GetWorkOrderImgHttp.ImgDataListener() {
            @Override
            public void result(String json) {
                String str = FastJsonUtil.getString(json, "imgPath");
                if("null".equals(str)){
                    ToastUtil.toast("服务器中没有对应图片，获取失败！");
                    return;
                }
                if (!StringUtil.isEmpty(str)) {
                    ftPhotos.addAll(Arrays.asList(str.split("!")));
                    ftAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getStatus() {
        GetDictDataHttp.getDictData(this, "OP_FAULT_METHOD", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (info.getExp1().equals(list.get(i).getDataValue())) {
                            tv24.setText(list.get(i).getDataName());
                            break;
                        }
                    }

                    if (info.getExp1().equals("REPLACE")) {
                        //更换
                        llChangePart.setVisibility(View.VISIBLE);
                        tv25.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());
                        tv26.setText(StringUtil.isEmpty(info.getMap().getAssetCode()) ? "" : info.getMap().getAssetCode());
                        tv27.setText(StringUtil.isEmpty(info.getMap().getDeviceStatusName()) ? "" : info.getMap().getDeviceStatusName());//原设备状态
                        tv28.setText(StringUtil.isEmpty(asset.getMap().getNewAssetName()) ? "" : asset.getMap().getNewAssetName());//更换设备名称
                        tv29.setText(StringUtil.isEmpty(asset.getMap().getNewAssetCode()) ? "" : asset.getMap().getNewAssetCode());//更换设备编号

                    } else {
                        llChangePart.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    private void initClick() {
        tvE1.setText(String.valueOf(maxScore));
        tvE2.setText(String.valueOf(maxScore));
        tvE3.setText(String.valueOf(maxScore));
        tvE4.setText(String.valueOf(maxScore));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_agree:
                        type = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llEvaluatePart.setVisibility(View.VISIBLE);
                                etRefuse.setVisibility(View.GONE);
                            }
                        });
                        break;
                    case R.id.rb_refuse:
                        type = 2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llEvaluatePart.setVisibility(View.GONE);
                                etRefuse.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                }
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck();
            }
        });

    }


    private void doCheck() {
        if (type == 0) {
            ToastUtil.toast("请选择同意或者拒绝");
            return;
        }
        if (type == 2 && StringUtil.isEmpty(etRefuse.getText().toString().trim())) {
            ToastUtil.toast("请填写拒绝理由");
            return;
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("verifyStatus", type == 1 ? "PASS" : "REJECT");
        if (type == 1) {
            hashMap.put("serviceRating", tvE1.getText().toString());
            hashMap.put("serviceRating2", tvE2.getText().toString());
            hashMap.put("serviceRating3", tvE3.getText().toString());
            hashMap.put("serviceRating4", tvE4.getText().toString());
        } else {
            hashMap.put("verifyRemark", etRefuse.getText().toString().trim());

        }
        ApiClient.requestNetPost(this, AppConfig.OpFaultInfoCheck, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_FAULT_LIST));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
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

    @OnClick({R.id.iv_up1, R.id.iv_down1, R.id.iv_up2, R.id.iv_down2, R.id.iv_up3, R.id.iv_down3, R.id.iv_up4, R.id.Iv_down4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_up1:
                checkScore(true, tvE1);
                break;
            case R.id.iv_down1:
                checkScore(false, tvE1);
                break;
            case R.id.iv_up2:
                checkScore(true, tvE2);
                break;
            case R.id.iv_down2:
                checkScore(false, tvE2);
                break;
            case R.id.iv_up3:
                checkScore(true, tvE3);
                break;
            case R.id.iv_down3:
                checkScore(false, tvE3);
                break;
            case R.id.iv_up4:
                checkScore(true, tvE4);
                break;
            case R.id.Iv_down4:
                checkScore(false, tvE4);
                break;
        }
    }


    private void checkScore(boolean plus, TextView v) {
        int scoreGet = Integer.parseInt(v.getText().toString());
        if (plus) {
            if (scoreGet == maxScore) {
                ToastUtil.toast("已经达到最高分，不能再高了！");
                return;
            }
            v.setText(String.valueOf(scoreGet + 1));
        } else {
            if (scoreGet == minScore) {
                ToastUtil.toast("已经达到最低分，不能再低了！");
                return;
            }
            v.setText(String.valueOf(scoreGet - 1));
        }
    }
}
