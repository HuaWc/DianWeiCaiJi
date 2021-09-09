package com.hwc.dwcj.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.AdapterAuditUser;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.base.MyApplication;
import com.hwc.dwcj.entity.AuditUserEntity;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.util.BitmapUtil;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.hwc.dwcj.util.RegularCheckUtil;
import com.hwc.dwcj.view.dialog.BaseDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.code.encoding.EncodingHandler;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuditDetailUserActivity extends BaseActivity {
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
    @BindView(R.id.tv_spbh)
    TextView tvSpbh;
    @BindView(R.id.tv_sply)
    TextView tvSply;
    @BindView(R.id.tv_tjsj)
    TextView tvTjsj;
    @BindView(R.id.rv_lc)
    RecyclerView rvLc;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_cx)
    TextView tvCx;
    @BindView(R.id.tv_cb)
    TextView tvCb;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.bar)
    View bar;


    private String uuid;
    private List<AuditUserEntity.Check> users;
    private AdapterAuditUser adapterAuditUser;
    private AuditUserEntity auditUserEntity;

    Bitmap bitmap;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_audit_detail_user);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initAdapter();
        initClick();
    }

    private void initAdapter() {
        users = new ArrayList<>();
        adapterAuditUser = new AdapterAuditUser(users);
        rvLc.setAdapter(adapterAuditUser);
        rvLc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerViewHelper.recyclerviewAndScrollView(rvLc);
        getData();
    }

    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("uuid", uuid);
        ApiClient.requestNetGet(this, AppConfig.selectProcessApproval, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                auditUserEntity = FastJsonUtil.getObject(json, AuditUserEntity.class);
                if (auditUserEntity != null) {
                    initData();
                    AuditUserEntity.Check user1 = new AuditUserEntity.Check();
                    user1.setMe(true);
                    user1.setCheckType(0);
                    user1.setCheckUserName("我");
                    users.add(user1);
                    if (auditUserEntity.getCheck() != null && auditUserEntity.getCheck().size() != 0) {
                        users.addAll(auditUserEntity.getCheck());
                    }
                    adapterAuditUser.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initData() {
        tvName.setText(MyApplication.getInstance().getUserInfo().getRealName() + "提交的审批流程");
        tvTime.setText(StringUtil.isEmpty(auditUserEntity.getCamera().getInstallTime()) ? "" : auditUserEntity.getCamera().getInstallTime().substring(0, 10));
        switch (auditUserEntity.getCamera().getCurrentStatus()) {
            case 1:
                tvStatus.setText("审批中");
                llBtn.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvStatus.setText("已撤销");
                llBtn.setVisibility(View.GONE);
                break;
            case 3:
                tvStatus.setText("已驳回");
                llBtn.setVisibility(View.GONE);
                break;
            case 4:
                tvStatus.setText("已通过");
                llBtn.setVisibility(View.GONE);
                break;
            case 5:
                tvStatus.setText("草稿");
                llBtn.setVisibility(View.GONE);
                break;
        }
        tvDwmc.setText(StringUtil.isEmpty(auditUserEntity.getCamera().getPointName()) ? "" : auditUserEntity.getCamera().getPointName());
        tvSbmc.setText(StringUtil.isEmpty(auditUserEntity.getCamera().getCameraName()) ? "" : auditUserEntity.getCamera().getCameraName());
        tvAzdz.setText(StringUtil.isEmpty(auditUserEntity.getCamera().getAddress()) ? "" : auditUserEntity.getCamera().getAddress());
        //tvSpbh.setText(auditUserEntity.getCamera().getPointName());//审批编号
        //tvSply.setText(auditUserEntity.getCamera().getAddress());//审批理由
        tvTjsj.setText(StringUtil.isEmpty(auditUserEntity.getCamera().getAddDate()) ? "" : auditUserEntity.getCamera().getAddDate().substring(0, 10));


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
                if (StringUtil.isEmpty(auditUserEntity.getCamera().getPositionCode())) {
                    Toast.makeText(AuditDetailUserActivity.this, "生成失败，杆件编码为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AuditDetailUserActivity.this, "二维码生成中，请稍后", Toast.LENGTH_SHORT).show();
                bitmap = EncodingHandler.createQRCode(auditUserEntity.getCamera().getPositionCode(), 143, 143, null);
                if (bitmap != null) {
                    AuditDetailUserActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivCode.setImageBitmap(bitmap);
                            ivCode.setVisibility(View.VISIBLE);
                            tvCode.setVisibility(View.GONE);
                        }
                    });
                    ivCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //点击显示弹窗，弹窗可以保存二维码到本地
                            showDialog();
                        }
                    });
                }
            }
        });
        tvCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cx();
            }
        });
        tvCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb();
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
                        BitmapUtil.saveBitmapInFile(AuditDetailUserActivity.this, bitmap, "BarCode");
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

    private void cb() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraId", uuid);
        ApiClient.requestNetPost(this, AppConfig.expedite, "催办中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (!StringUtil.isEmpty(msg)) {
                    ToastUtil.toast(msg);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    private void cx() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraId", uuid);
        hashMap.put("handleConnent", "撤销审批流程");
        hashMap.put("handleIp", RegularCheckUtil.getIPAddress(this));
        ApiClient.requestNetPost(this, AppConfig.dropCamera, "撤销中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (!StringUtil.isEmpty(msg)) {
                    ToastUtil.toast(msg);
                }
                EventBus.getDefault().post(new EventCenter(EventUtil.FLUSH_LIST_DW));
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
        uuid = extras.getString("uuid");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
