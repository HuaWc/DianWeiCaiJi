package com.hwc.dwcj.activity.second;

import android.app.Dialog;
import android.content.Intent;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.AdapterCameraPhoto;
import com.hwc.dwcj.adapter.CommonImageAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.DictInfo;
import com.hwc.dwcj.entity.second.AssetEquipment;
import com.hwc.dwcj.entity.second.FaultMapInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetDictDataHttp;
import com.hwc.dwcj.http.GetWorkOrderImgHttp;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.http.UploadWorkOrderImgHttp;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.hwc.dwcj.view.dialog.CommonTipDialog;
import com.hwc.dwcj.view.dialog.PictureSelectDialogUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 工单处理 界面
 */
public class WorkOrderHandleActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.rb_fix)
    RadioButton rbFix;
    @BindView(R.id.rb_change)
    RadioButton rbChange;
    @BindView(R.id.rb_other)
    RadioButton rbOther;
    @BindView(R.id.rb_problem)
    RadioButton rbProblem;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.ll_change_part)
    LinearLayout llChangePart;
    @BindView(R.id.ll_clr)
    LinearLayout llClr;
    @BindView(R.id.ll_pzrz)
    LinearLayout llPzrz;
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
    @BindView(R.id.et_sm)
    EditText etSm;
    @BindView(R.id.rv_add_img)
    RecyclerView rvAddImg;
    @BindView(R.id.et_pzrz)
    EditText etPzrz;
    @BindView(R.id.tv_pd_remark)
    TextView tvPdRemark;
    @BindView(R.id.rv1)
    RecyclerView rv1;

    private String id;
    private FaultMapInfo info;
    private int type = 0;
    private List<DictInfo> methodList;

    private CommonImageAdapter adapter1;
    private List<String> img1;//用于展示,和adapter绑定
    private List<Object> images1;//用于接口
    private List<String> imagesPath1;//用于删除

    private List<LocalMedia> images;
    private int num = 3;


    private List<DictInfo> statusList;
    private String statusStr = "";
    private List<String> optionList;

    private String newAssetId = "";

    private List<String> ftPhotos;//工单附图
    private AdapterCameraPhoto ftAdapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_handle);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
        getData();
        rbFix.setChecked(true);
    }

    private void initAdapter() {
        statusList = new ArrayList<>();
        optionList = new ArrayList<>();
        getStatusData();
        methodList = new ArrayList<>();
        getMethodData();
        img1 = new ArrayList<>();
        images1 = new ArrayList<>();
        imagesPath1 = new ArrayList<>();
        adapter1 = new CommonImageAdapter(img1);
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position);
                        break;
                }
            }
        });

        rvAddImg.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvAddImg.setAdapter(adapter1);
        RecyclerViewHelper.recyclerviewAndScrollView(rvAddImg);
        img1.add("");
        adapter1.notifyDataSetChanged();
    }

    private void getStatusData() {
        GetDictDataHttp.getDictData(this, "OP_ASSET_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    statusList.addAll(list);
                    for (DictInfo d : list) {
                        optionList.add(d.getDataName());
                    }
                }
            }
        });
    }


    /**
     * 选择图片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data);
                if (images != null && images.size() != 0) {
                    img1.remove("");
                    uploadImg(0);
                }


            }
        }
    }


    /**
     * 装载图片
     */
    private void uploadImg(int i) {
        int in = i + 1;
        if (i == images.size()) {
            //后面没了
            if (img1.size() < num) {
                img1.add("");
            }
            adapter1.notifyDataSetChanged();
            return;
        }
        UploadWorkOrderImgHttp.upload(this, "OpFaultInfo", new File(images.get(i).getPath()), new UploadWorkOrderImgHttp.UploadResult() {
            @Override
            public void onSuccess(Object pic, String path) {
                img1.add(images.get(i).getPath());
                images1.add(pic);
                imagesPath1.add(path);
                uploadImg(in);
            }
        });


    }

    private void showSelectPhotoDialog() {
        if (!img1.contains("") && img1.size() == num) {
            ToastUtil.toast("最多可以选择" + num + "张图片");
            return;
        } else if (img1.contains("") && img1.size() == num + 1) {
            ToastUtil.toast("最多可以选择" + num + "张图片");
            return;
        }
        PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img1.size());
    }

    private void showDeleteDialog(int position) {
        CommonTipDialog.getInstance()
                .addTipData(this, "提示", "取消", "确定")
                .addBtnColor("#FF191F25", "#FF4F77E1")
                .addTipContent("确定要删除吗？")
                .setCancelable(false)
                .addOnClickListener(new CommonTipDialog.OnClickListener() {
                    @Override
                    public void onLeft(View v, Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onRight(View v, Dialog dialog) {
                        //images1.get(position)
                        UploadWorkOrderImgHttp.delete(WorkOrderHandleActivity.this, imagesPath1.get(position), new UploadWorkOrderImgHttp.DeleteResult() {
                            @Override
                            public void onSuccess() {
                                img1.remove(position);
                                images1.remove(position);
                                imagesPath1.remove(position);
                                if (img1.size() < num && !img1.contains("")) {
                                    img1.add("");
                                }
                                adapter1.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .show();
    }

    private void getMethodData() {
        GetDictDataHttp.getDictData(this, "OP_FAULT_METHOD", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    methodList.addAll(list);
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

        tv24.setText(StringUtil.isEmpty(info.getMap().getAssetName()) ? "" : info.getMap().getAssetName());//设备名称
        tv25.setText(StringUtil.isEmpty(info.getMap().getAssetCode()) ? "" : info.getMap().getAssetCode());//设备编号

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

    private void initClick() {
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
                    case R.id.rb_fix:
                        type = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.GONE);
                                llClr.setVisibility(View.GONE);
                                llPzrz.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_other:
                        type = 2;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.GONE);
                                llClr.setVisibility(View.GONE);
                                llPzrz.setVisibility(View.VISIBLE);
                            }
                        });
                        break;
                    case R.id.rb_problem:
                        type = 3;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.GONE);
                                llClr.setVisibility(View.GONE);
                                llPzrz.setVisibility(View.GONE);
                            }
                        });
                        break;
                    case R.id.rb_change:
                        type = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llChangePart.setVisibility(View.VISIBLE);
                                llClr.setVisibility(View.VISIBLE);
                                llPzrz.setVisibility(View.VISIBLE);
                            }
                        });
                }
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        tv26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择原设备状态
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (optionList == null || optionList.size() == 0) {
                    ToastUtil.toast("状态值为空，请稍后再试！");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderHandleActivity.this, "选择状态", optionList, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tv26.setText(optionList.get(options1));
                        statusStr = statusList.get(options1).getDataValue();
                    }
                });
            }
        });
        tv27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择更换设备信息
                //到新页面吧
                Bundle bundle = new Bundle();
                bundle.putString("faultId", id);
                toTheActivity(SelectChangeAssetActivity.class, bundle);

            }
        });
    }


    private void submit() {
        if (type == 1) {
            if (StringUtil.isEmpty(tv26)) {
                ToastUtil.toast("请选择原设备状态");
                return;
            }
            if (StringUtil.isEmpty(tv27)) {
                ToastUtil.toast("请选择更换设备信息");
                return;
            }
        }
        if (StringUtil.isEmpty(etSm.getText().toString().trim())) {
            ToastUtil.toast("请填写说明");
            return;
        }
        if (type != 3) {
            if (StringUtil.isEmpty(etPzrz.getText().toString().trim())) {
                ToastUtil.toast("请填写排障日志");
                return;
            }
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("handleMethod", methodList.get(type).getDataValue());
        hashMap.put("handRemark", etSm.getText().toString().trim());
        if (type == 1) {
            hashMap.put("assetId", info.getAssetId());
            hashMap.put("oldDeviceStatus", statusStr);
            hashMap.put("newAssetId", newAssetId);
        }
        hashMap.put("remarkLog", etPzrz.getText().toString().trim());//排障日志
/*        if (type != 3) {
            if (!StringUtil.isEmpty(etPzrz.getText().toString().trim())) {
                hashMap.put("remarkLog", etPzrz.getText().toString().trim());//排障日志
            }
        }*/
        if (images1 != null && images1.size() != 0) {
            hashMap.put("ptAttachments", FastJsonUtil.toJSONString(images1));
        }

        ApiClient.requestNetPost(this, AppConfig.FaultHandleForYWPerson, "提交中", hashMap, new ResultListener() {
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
        switch (center.getEventCode()) {
            case EventUtil.SELECT_CHANGE_ASSET:
                AssetEquipment a = (AssetEquipment) center.getData();
                tv27.setText(a.getAssetName());
                tv28.setText(a.getAssetCode());
                newAssetId = a.getId();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_FAULT_LIST));
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
