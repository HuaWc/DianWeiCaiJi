package com.hwc.dwcj.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.activity.second.WorkOrderManagementAdminActivity;
import com.hwc.dwcj.activity.second.WorkOrderManagementUserActivity;
import com.hwc.dwcj.adapter.AdapterCameraPhoto;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.Constant;
import com.hwc.dwcj.entity.PtCameraInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.GetCameraImgHttp;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.BitmapUtil;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.hwc.dwcj.view.dialog.BaseDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.code.encoding.EncodingHandler;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 长太息以掩涕兮
 * 哀民生之多艰
 * 余虽好修姱以鞿羁兮
 * 謇朝谇而夕替
 * 既替余以蕙纕兮又申之以揽茝
 */
public class DossierDetailActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_dwmc)
    TextView tvDwmc;
    @BindView(R.id.tv_sbmc)
    TextView tvSbmc;
    @BindView(R.id.tv_azdz)
    TextView tvAzdz;
    @BindView(R.id.tv_dwbm)
    TextView tvDwbm;
    @BindView(R.id.tv_ssfj)
    TextView tvSsfj;
    @BindView(R.id.tv_sspcs)
    TextView tvSspcs;
    @BindView(R.id.tv_jwd)
    TextView tvJwd;
    @BindView(R.id.tv_lwfs)
    TextView tvLwfs;
    @BindView(R.id.tv_qdfs)
    TextView tvQdfs;
    @BindView(R.id.tv_lwrdh)
    TextView tvLwrdh;
    @BindView(R.id.tv_qdrdh)
    TextView tvQdrdh;
    @BindView(R.id.yv_code)
    ImageView yvCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_sbr)
    TextView tvSbr;
    @BindView(R.id.tv_sbrdh)
    TextView tvSbrdh;
    @BindView(R.id.tv_spr)
    TextView tvSpr;
    @BindView(R.id.tv_sprdh)
    TextView tvSprdh;
    @BindView(R.id.tv_sbbm)
    TextView tvSbbm;
    @BindView(R.id.tv_gjbh)
    TextView tvGjbh;
    @BindView(R.id.tv_ewmbh)
    TextView tvEwmbh;
    @BindView(R.id.tv_ipv4)
    TextView tvIpv4;
    @BindView(R.id.tv_ipv6)
    TextView tvIpv6;
    @BindView(R.id.tv_mac)
    TextView tvMac;
    @BindView(R.id.tv_sbcs)
    TextView tvSbcs;
    @BindView(R.id.tv_sbxh)
    TextView tvSbxh;
    @BindView(R.id.tv_sxjlx)
    TextView tvSxjlx;
    @BindView(R.id.tv_gnlx)
    TextView tvGnlx;
    @BindView(R.id.tv_wzlx)
    TextView tvWzlx;
    @BindView(R.id.tv_azwz)
    TextView tvAzwz;
    @BindView(R.id.tv_zdjkdw)
    TextView tvZdjkdw;
    @BindView(R.id.tv_szsq)
    TextView tvSzsq;
    @BindView(R.id.tv_szjd)
    TextView tvSzjd;
    @BindView(R.id.tv_ssbmhy)
    TextView tvSsbmhy;
    @BindView(R.id.tv_xzqy)
    TextView tvXzqy;
    @BindView(R.id.tv_jsgd)
    TextView tvJsgd;
    @BindView(R.id.tv_hb)
    TextView tvHb;
    @BindView(R.id.tv_dwjklx)
    TextView tvDwjklx;
    @BindView(R.id.rv_sxjtx)
    RecyclerView rvSxjtx;
    @BindView(R.id.rv_tzzp)
    RecyclerView rvTzzp;
    @BindView(R.id.rv_qjzp)
    RecyclerView rvQjzp;
    @BindView(R.id.bar)
    View bar;

    private PtCameraInfo entityInfo;
    private String cameraId;
    private String positionName;
    private List<String> photo1;
    private AdapterCameraPhoto adapter1;
    private List<String> photo2;
    private AdapterCameraPhoto adapter2;
    private List<String> photo3;
    private AdapterCameraPhoto adapter3;
    private List<String> checkUsersTel;
    private List<String> checkUsers;
    //private String applicantName;
    //private String applicantTel;
    private String powerTakeType;
    private String networkProperties;

    Bitmap bitmap;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_dossier_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtil.isEmpty(entityInfo.getPositionCode())) {
                    Toast.makeText(DossierDetailActivity.this, "生成失败，杆件编码为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(DossierDetailActivity.this, "二维码生成中，请稍后", Toast.LENGTH_SHORT).show();
                bitmap = EncodingHandler.createQRCode(Constant.URL_PREFIX + entityInfo.getPositionCode(), 143, 143, null);
                if (bitmap != null) {
                    DossierDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            yvCode.setImageBitmap(bitmap);
                            yvCode.setVisibility(View.VISIBLE);
                            tvCode.setVisibility(View.GONE);
                        }
                    });
                    yvCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //点击显示弹窗，弹窗可以保存二维码到本地
                            showDialog();
                        }
                    });
                }
            }
        });
    }

    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_save_code, null);
        View tv_save = view.findViewById(R.id.tv_save);
        View tv_cancel = view.findViewById(R.id.tv_cancel);
        BaseDialog.getInstance()
                .setLayoutView(view, this)
                .dissmissDialog()
                .setWindow(1, 0.5)
                .isCancelable(true)
                .setOnClickListener(tv_save, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        BitmapUtil.saveBitmapInFile(DossierDetailActivity.this, bitmap, "BarCode");
                    }
                })
                .setOnClickListener(tv_cancel, new BaseDialog.OnClickListener() {
                    @Override
                    public void onClick(View view, Dialog dialog) {
                        //取消
                        dialog.dismiss();
                    }
                }).bottomShow();
    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        cameraId = extras.getString("cameraId");
        positionName = extras.getString("positionName");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initAdapter() {
        photo1 = new ArrayList<>();
        adapter1 = new AdapterCameraPhoto(photo1);
        rvSxjtx.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        rvSxjtx.setAdapter(adapter1);
        RecyclerViewHelper.recyclerviewAndScrollView(rvSxjtx);

        photo2 = new ArrayList<>();
        adapter2 = new AdapterCameraPhoto(photo2);
        rvTzzp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        rvTzzp.setAdapter(adapter2);
        RecyclerViewHelper.recyclerviewAndScrollView(rvTzzp);

        photo3 = new ArrayList<>();
        adapter3 = new AdapterCameraPhoto(photo3);
        rvQjzp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        rvQjzp.setAdapter(adapter3);
        RecyclerViewHelper.recyclerviewAndScrollView(rvQjzp);
        getData();
        getImgData();
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraId", cameraId);
        ApiClient.requestNetPost(this, AppConfig.selectCamera, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                entityInfo = FastJsonUtil.getObject(FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "ptCameraInfo"), PtCameraInfo.class);
                networkProperties = FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "networkProperties");
                powerTakeType = FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "powerTakeType");
//                applicantTel = FastJsonUtil.getString(json, "applicantTel");
//                applicantName = FastJsonUtil.getString(json, "applicantName");
                checkUsers = FastJsonUtil.getList(FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "checkUsers"), String.class);
                checkUsersTel = FastJsonUtil.getList(FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "checkUsersTel"), String.class);

                initInfo();


            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void getImgData() {
        GetCameraImgHttp.getImg(cameraId, this, new GetCameraImgHttp.ImgDataListener() {
            @Override
            public void result(String json) {
                String s1 = FastJsonUtil.getString(json, "specialPhotoPath");
                String s2 = FastJsonUtil.getString(json, "imgPath");
                String s3 = FastJsonUtil.getString(json, "locationPhotoPath");
                if (!StringUtil.isEmpty(s1)) {
                    photo1.addAll(Arrays.asList(s1.split("!")));
                    adapter1.notifyDataSetChanged();
                }
                if (!StringUtil.isEmpty(s2)) {
                    photo2.addAll(Arrays.asList(s2.split("!")));
                    adapter2.notifyDataSetChanged();
                }
                if (!StringUtil.isEmpty(s3)) {
                    photo3.addAll(Arrays.asList(s3.split("!")));
                    adapter3.notifyDataSetChanged();
                }
            }
        });
    }

    private void initInfo() {
        tvLwfs.setText(StringUtil.isEmpty(networkProperties) ? "" : networkProperties);//联网方式
        tvQdfs.setText(StringUtil.isEmpty(powerTakeType) ? "" : powerTakeType);//取电方式
        if (checkUsers != null && checkUsers.size() != 0) {
            StringBuilder builder = new StringBuilder();
            for (String s : checkUsers) {
                builder.append(s);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            tvSpr.setText(builder.toString());//审批人

        }
        if (checkUsersTel != null && checkUsersTel.size() != 0) {
            StringBuilder builder = new StringBuilder();
            for (String s : checkUsersTel) {
                builder.append(s);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            tvSprdh.setText(builder.toString());//审批人电话

        }

        if (entityInfo == null) {
            return;
        }
        tvSbr.setText(StringUtil.isEmpty(entityInfo.getAddId()) ? "" : entityInfo.getAddId());//上报人
        tvDwmc.setText(StringUtil.isEmpty(entityInfo.getPointName()) ? "" : entityInfo.getPointName());
        if (StringUtil.isEmpty(positionName)) {
            tvDwmc.setText(StringUtil.isEmpty(entityInfo.getPointName()) ? "" : entityInfo.getPointName());
        }
        tvSbrdh.setText(StringUtil.isEmpty(entityInfo.getAddTel()) ? "" : entityInfo.getAddTel());//上报人电话
        tvSbmc.setText(StringUtil.isEmpty(entityInfo.getCameraName()) ? "" : entityInfo.getCameraName());
        tvAzdz.setText(StringUtil.isEmpty(entityInfo.getAddress()) ? "" : entityInfo.getAddress());
        tvDwbm.setText(StringUtil.isEmpty(entityInfo.getPositionCode()) ? "" : entityInfo.getPositionCode());
        tvSsfj.setText(StringUtil.isEmpty(entityInfo.getFenJu()) ? "" : entityInfo.getFenJu());//所属分局
        tvSspcs.setText(StringUtil.isEmpty(entityInfo.getPoliceStation()) ? "" : entityInfo.getPoliceStation());
        tvJwd.setText((StringUtil.isEmpty(entityInfo.getLongitude()) ? "" : entityInfo.getLongitude()) + "," + (StringUtil.isEmpty(entityInfo.getLatitude()) ? "" : entityInfo.getLatitude()));


        tvLwrdh.setText(StringUtil.isEmpty(entityInfo.getNetworkPropertiesTel()) ? "" : entityInfo.getNetworkPropertiesTel());//联网人电话
        tvQdrdh.setText(StringUtil.isEmpty(entityInfo.getNetworkPropertiesTel()) ? "" : entityInfo.getPowerTakeTypeTel());//取电人电话


        tvSbbm.setText(StringUtil.isEmpty(entityInfo.getCameraNo()) ? "" : entityInfo.getCameraNo());
        tvGjbh.setText(StringUtil.isEmpty(entityInfo.getMemberbarCode()) ? "" : entityInfo.getMemberbarCode());
        tvEwmbh.setText(StringUtil.isEmpty(entityInfo.getQrCodeNumber()) ? "" : entityInfo.getQrCodeNumber());
        tvIpv4.setText(StringUtil.isEmpty(entityInfo.getCameraIp()) ? "" : entityInfo.getCameraIp());
        tvIpv6.setText(StringUtil.isEmpty(entityInfo.getCameraIp6()) ? "" : entityInfo.getCameraIp6());
        tvMac.setText(StringUtil.isEmpty(entityInfo.getMacAddress()) ? "" : entityInfo.getMacAddress());
        tvSbcs.setText(StringUtil.isEmpty(entityInfo.getManufacturer()) ? "" : entityInfo.getManufacturer());//设备厂商
        tvSbxh.setText(StringUtil.isEmpty(entityInfo.getCameraModel()) ? "" : entityInfo.getCameraModel());
        tvSxjlx.setText(StringUtil.isEmpty(entityInfo.getCameraType()) ? "" : entityInfo.getCameraType());//摄像机类型

        tvGnlx.setText(StringUtil.isEmpty(entityInfo.getCameraFunType()) ? "" : entityInfo.getCameraFunType());//功能类型
        tvWzlx.setText(StringUtil.isEmpty(entityInfo.getPositionType()) ? "" : entityInfo.getPositionType());//位置类型
        if (entityInfo.getIndoorOrNot() != null) {
            tvAzwz.setText(entityInfo.getIndoorOrNot() == 1 ? "室外" : "室内");

        }
        tvZdjkdw.setText(StringUtil.isEmpty(entityInfo.getImportWatch()) ? "" : entityInfo.getImportWatch());
        tvSzsq.setText(StringUtil.isEmpty(entityInfo.getCommunity()) ? "" : entityInfo.getCommunity());

        tvSzjd.setText(StringUtil.isEmpty(entityInfo.getStreet()) ? "" : entityInfo.getStreet());
        tvSsbmhy.setText(StringUtil.isEmpty(entityInfo.getIndustryOwn()) ? "" : entityInfo.getIndustryOwn());//所属部门/行业
        tvXzqy.setText(StringUtil.isEmpty(entityInfo.getAreaCode()) ? "" : entityInfo.getAreaCode());//行政区域
        tvJsgd.setText(StringUtil.isEmpty(entityInfo.getInstallHeight()) ? "" : entityInfo.getInstallHeight());

        tvHb.setText(StringUtil.isEmpty(entityInfo.getCrossArm1()) ? "" : entityInfo.getCrossArm1());
        tvDwjklx.setText(StringUtil.isEmpty(entityInfo.getMonitorType()) ? "" : entityInfo.getMonitorType());//点位监控类型

/*        if (!StringUtil.isEmpty(entityInfo.getSpecialPhotoPath())) {
            photo1.addAll(Arrays.asList(entityInfo.getSpecialPhotoPath().split("!")));
        }
        if (!StringUtil.isEmpty(entityInfo.getLocationPhotoPath())) {
            photo2.addAll(Arrays.asList(entityInfo.getLocationPhotoPath().split("!")));
        }
        if (!StringUtil.isEmpty(entityInfo.getImgPath())) {
            photo3.addAll(Arrays.asList(entityInfo.getImgPath().split("!")));
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();*/

    }


    // 方法三
    public static String listToString3(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
