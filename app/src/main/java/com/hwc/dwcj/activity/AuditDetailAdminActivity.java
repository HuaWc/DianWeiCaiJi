package com.hwc.dwcj.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.AdapterCameraPhoto;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.PtCameraInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.hwc.dwcj.util.RegularCheckUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuditDetailAdminActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_status)
    TextView tvStatus;
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
    @BindView(R.id.tv_wwsx)
    TextView tvWwsx;
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
    @BindView(R.id.tv_jj)
    TextView tvJj;
    @BindView(R.id.tv_ty)
    TextView tvTy;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv_spbh)
    TextView tvSpbh;
    @BindView(R.id.bar)
    View bar;

    private String cameraId;
    private PtCameraInfo entity;
    private String positionName;

    private List<String> photo1;
    private AdapterCameraPhoto adapter1;
    private List<String> photo2;
    private AdapterCameraPhoto adapter2;
    private List<String> photo3;
    private AdapterCameraPhoto adapter3;
    private String userName;
    private List<String> checkUsersTel;
    private List<String> checkUsers;
    /*    private String applicantName;
        private String applicantTel;*/
    private String powerTakeType;
    private String networkProperties;

    private boolean isLook;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_audit_detail_admin);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
        if (isLook) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    llBtn.setVisibility(View.GONE);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    llBtn.setVisibility(View.VISIBLE);
                }
            });
        }
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
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAboutSp(1);
            }
        });
        tvJj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAboutSp(2);

            }
        });
    }

    /**
     * @param type 1 同意  2 驳回
     */
    private void doAboutSp(int type) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("uuid", cameraId);
        hashMap.put("checkType", type);
        hashMap.put("reason","");
        hashMap.put("handleConnent", type == 1 ? "同意" : "驳回");
        hashMap.put("handleIp", RegularCheckUtil.getIPAddress(this));
        ApiClient.requestNetGet(this, AppConfig.checkCamera, "操作中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.FLUSH_LIST_SP));
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
        hashMap.put("cameraId", cameraId);
        ApiClient.requestNetPost(this, AppConfig.cameradetails, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                entity = FastJsonUtil.getObject(FastJsonUtil.getString(json, "ptCameraInfo"), PtCameraInfo.class);
                userName = FastJsonUtil.getString(json, "addName");
                networkProperties = FastJsonUtil.getString(json, "networkProperties");
                powerTakeType = FastJsonUtil.getString(json, "powerTakeType");
                //applicantTel = FastJsonUtil.getString(json, "applicantTel");
                //applicantName = FastJsonUtil.getString(json, "applicantName");
                checkUsers = FastJsonUtil.getList(FastJsonUtil.getString(json, "checkUser"), String.class);
                checkUsersTel = FastJsonUtil.getList(FastJsonUtil.getString(json, "checkUserTel"), String.class);
                String s1 = FastJsonUtil.getString(json, "imgPath");
                String s2 = FastJsonUtil.getString(json, "locationPhotoPath");
                String s3 = FastJsonUtil.getString(json, "specialPhotoPath");
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
                initData();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }

    private void initData() {
        tvName.setText((StringUtil.isEmpty(userName) ? "" : userName) + "提交的审批流程");
        tvSbr.setText(StringUtil.isEmpty(userName) ? "" : userName);//上报人
        tvDwmc.setText(StringUtil.isEmpty(positionName) ? "" : positionName);
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


        if (entity == null) {
            return;
        }
        if (entity.getAddTime() != null) {
            tvTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(entity.getAddTime()));
        }
        if (entity.getCurrentStatus() != null) {
            if (entity.getCurrentStatus() == 1) {
                tvStatus.setText("审批中");
            } else if (entity.getCurrentStatus() == 2) {
                tvStatus.setText("已撤销");
            } else if (entity.getCurrentStatus() == 3) {
                tvStatus.setText("已驳回");
            } else if (entity.getCurrentStatus() == 4) {
                tvStatus.setText("已通过");
            }
        }
        tvSbmc.setText(StringUtil.isEmpty(entity.getCameraName()) ? "" : entity.getCameraName());
        tvAzdz.setText(StringUtil.isEmpty(entity.getAddress()) ? "" : entity.getAddress());

        //tvSpbh.setText(StringUtil.isEmpty(entity.getAddress()) ? "" : entity.getAddress());//审批编号
        tvSbrdh.setText(StringUtil.isEmpty(entity.getAddTel()) ? "" : entity.getAddTel());//上报人电话


        tvDwbm.setText(StringUtil.isEmpty(entity.getPositionCode()) ? "" : entity.getPositionCode());
        tvSsfj.setText(StringUtil.isEmpty(entity.getFenJu()) ? "" : entity.getFenJu());//所属分局
        tvSspcs.setText(StringUtil.isEmpty(entity.getPoliceStation()) ? "" : entity.getPoliceStation());
        tvJwd.setText((StringUtil.isEmpty(entity.getLongitude()) ? "" : entity.getLongitude()) + "," + (StringUtil.isEmpty(entity.getLatitude()) ? "" : entity.getLatitude()));


        tvLwrdh.setText(StringUtil.isEmpty(entity.getNetworkPropertiesTel()) ? "" : entity.getNetworkPropertiesTel());//联网人电话
        tvQdrdh.setText(StringUtil.isEmpty(entity.getPowerTakeTypeTel()) ? "" : entity.getPowerTakeTypeTel());//取电人电话


        tvSbbm.setText(StringUtil.isEmpty(entity.getCameraNo()) ? "" : entity.getCameraNo());
        tvGjbh.setText(StringUtil.isEmpty(entity.getMemberbarCode()) ? "" : entity.getMemberbarCode());
        tvEwmbh.setText(StringUtil.isEmpty(entity.getQrCodeNumber()) ? "" : entity.getQrCodeNumber());
        tvIpv4.setText(StringUtil.isEmpty(entity.getCameraIp()) ? "" : entity.getCameraIp());
        tvIpv6.setText(StringUtil.isEmpty(entity.getCameraIp6()) ? "" : entity.getCameraIp6());
        tvMac.setText(StringUtil.isEmpty(entity.getMacAddress()) ? "" : entity.getMacAddress());
        tvSbcs.setText(StringUtil.isEmpty(entity.getManufacturer()) ? "" : entity.getManufacturer());//设备厂商
        tvSbxh.setText(StringUtil.isEmpty(entity.getCameraModel()) ? "" : entity.getCameraModel());
        tvSxjlx.setText(StringUtil.isEmpty(entity.getCameraType()) ? "" : entity.getCameraType());//摄像机类型

        tvGnlx.setText(StringUtil.isEmpty(entity.getCameraFunType()) ? "" : entity.getCameraFunType());//功能类型
        tvWzlx.setText(StringUtil.isEmpty(entity.getPositionType()) ? "" : entity.getPositionType());//位置类型
        if (entity.getIndoorOrNot() != null) {
            tvAzwz.setText(entity.getIndoorOrNot() == 1 ? "室外" : "室内");

        }
        tvZdjkdw.setText(StringUtil.isEmpty(entity.getImportWatch()) ? "" : entity.getImportWatch());
        // tvWwsx.setText();//外网属性
        tvSzsq.setText(StringUtil.isEmpty(entity.getCommunity()) ? "" : entity.getCommunity());

        tvSzjd.setText(StringUtil.isEmpty(entity.getStreet()) ? "" : entity.getStreet());
        tvSsbmhy.setText(StringUtil.isEmpty(entity.getIndustryOwn()) ? "" : entity.getIndustryOwn());//所属部门/行业
        tvXzqy.setText(StringUtil.isEmpty(entity.getAreaCode()) ? "" : entity.getAreaCode());//行政区域
        tvJsgd.setText(StringUtil.isEmpty(entity.getInstallHeight()) ? "" : entity.getInstallHeight());

        tvHb.setText(StringUtil.isEmpty(entity.getCrossArm1()) ? "" : entity.getCrossArm1());
        tvDwjklx.setText(StringUtil.isEmpty(entity.getMonitorType()) ? "" : entity.getMonitorType());//点位监控类型

/*        if (!StringUtil.isEmpty(entity.getSpecialPhotoPath())) {
            photo1.addAll(Arrays.asList(entity.getSpecialPhotoPath().split(",")));
        }
        if (!StringUtil.isEmpty(entity.getLocationPhotoPath())) {
            photo2.addAll(Arrays.asList(entity.getLocationPhotoPath().split(",")));
        }
        if (!StringUtil.isEmpty(entity.getImgPath())) {
            photo3.addAll(Arrays.asList(entity.getImgPath().split(",")));
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();*/


    }


    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        cameraId = extras.getString("cameraId");
        positionName = extras.getString("positionName");
        isLook = extras.getBoolean("isLook");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
