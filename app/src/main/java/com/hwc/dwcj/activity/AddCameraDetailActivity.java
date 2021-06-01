package com.hwc.dwcj.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hwc.dwcj.R;
import com.hwc.dwcj.adapter.CommonImageAdapter;
import com.hwc.dwcj.base.BaseActivity;
import com.hwc.dwcj.entity.CameraDictData;
import com.hwc.dwcj.entity.CheckPositionCodeEntity;
import com.hwc.dwcj.entity.PcsDictItem;
import com.hwc.dwcj.entity.PtCameraInfo;
import com.hwc.dwcj.http.ApiClient;
import com.hwc.dwcj.http.AppConfig;
import com.hwc.dwcj.http.ResultListener;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.util.EventUtil;
import com.hwc.dwcj.util.PickerViewUtils;
import com.hwc.dwcj.util.RecyclerViewHelper;
import com.hwc.dwcj.util.RegularCheckUtil;
import com.hwc.dwcj.view.dialog.CommonTipDialog;
import com.hwc.dwcj.view.dialog.PictureSelectDialogUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.RegexUtils;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hwc.dwcj.util.PickerViewUtils.lineSpace;

public class AddCameraDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_dwbm)
    EditText etDwbm;
    @BindView(R.id.et_dwmc)
    EditText etDwmc;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.et_sbmc)
    EditText etSbmc;
    @BindView(R.id.tv_select_ssfj)
    TextView tvSelectSsfj;
    @BindView(R.id.tv_select_sspcs)
    TextView tvSelectSspcs;
    @BindView(R.id.et_azdz)
    EditText etAzdz;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.tv_select_lwfs)
    TextView tvSelectLwfs;
    @BindView(R.id.et_lwrlxfs)
    EditText etLwrlxfs;
    @BindView(R.id.tv_select_qdfs)
    TextView tvSelectQdfs;
    @BindView(R.id.et_qdrlxfs)
    EditText etQdrlxfs;
    @BindView(R.id.rv_sgtz)
    RecyclerView rvSgtz;
    @BindView(R.id.rv_qjt)
    RecyclerView rvQjt;
    @BindView(R.id.rv_sxjtx)
    RecyclerView rvSxjtx;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.close)
    LinearLayout close;
    @BindView(R.id.et_ipv4)
    EditText etIpv4;
    @BindView(R.id.tv_select_sbzt)
    TextView tvSelectSbzt;
    @BindView(R.id.et_sbbm)
    EditText etSbbm;
    @BindView(R.id.et_gjbh)
    EditText etGjbh;
    @BindView(R.id.et_ewmbh)
    EditText etEwmbh;
    @BindView(R.id.et_ipv6)
    EditText etIpv6;
    @BindView(R.id.et_mac)
    EditText etMac;
    @BindView(R.id.tv_select_sbcs)
    TextView tvSelectSbcs;
    @BindView(R.id.et_sbxh)
    EditText etSbxh;
    @BindView(R.id.tv_select_sxjlx)
    TextView tvSelectSxjlx;
    @BindView(R.id.tv_select_sxjgnlx)
    TextView tvSelectSxjgnlx;
    @BindView(R.id.tv_select_sxjwzlx)
    TextView tvSelectSxjwzlx;
    @BindView(R.id.tv_select_azwz)
    TextView tvSelectAzwz;
    @BindView(R.id.tv_select_sfzddw)
    TextView tvSelectSfzddw;
    @BindView(R.id.et_zdjkdw)
    EditText etZdjkdw;
    @BindView(R.id.et_sq)
    EditText etSq;
    @BindView(R.id.et_jd)
    EditText etJd;
    @BindView(R.id.tv_select_ssbmhy)
    TextView tvSelectSsbmhy;
    @BindView(R.id.tv_select_xzqy)
    TextView tvSelectXzqy;
    @BindView(R.id.et_jsgd)
    EditText etJsgd;
    @BindView(R.id.tv_select_hb)
    TextView tvSelectHb;
    @BindView(R.id.tv_select_dwjklx)
    TextView tvSelectDwjklx;
    @BindView(R.id.tv_select_psfx)
    TextView tvSelectPsfx;
    @BindView(R.id.tv_select_bgsx)
    TextView tvSelectBgsx;
    @BindView(R.id.tv_select_sxjbmgs)
    TextView tvSelectSxjbmgs;
    @BindView(R.id.tv_select_sfysyq)
    TextView tvSelectSfysyq;
    @BindView(R.id.tv_select_sxjfbl)
    TextView tvSelectSxjfbl;
    @BindView(R.id.tv_select_azsj)
    TextView tvSelectAzsj;
    @BindView(R.id.et_ssxqgajg)
    EditText etSsxqgajg;
    @BindView(R.id.tv_select_lxbcts)
    TextView tvSelectLxbcts;
    @BindView(R.id.et_jrfs)
    EditText etJrfs;
    @BindView(R.id.tv_select_jsqs)
    TextView tvSelectJsqs;
    @BindView(R.id.et_osd)
    EditText etOsd;
    @BindView(R.id.et_gldw)
    EditText etGldw;
    @BindView(R.id.et_gldwlxfs)
    EditText etGldwlxfs;
    @BindView(R.id.et_whdw)
    EditText etWhdw;
    @BindView(R.id.et_whdwlxfs)
    EditText etWhdwlxfs;
    @BindView(R.id.et_sbr)
    EditText etSbr;
    @BindView(R.id.et_sbrlxfs)
    EditText etSbrlxfs;
    @BindView(R.id.et_spr)
    EditText etSpr;
    @BindView(R.id.et_sprlxfs)
    EditText etSprlxfs;
    @BindView(R.id.ll_expand)
    LinearLayout llExpand;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bar)
    View bar;
    private CommonImageAdapter adapter1;
    private CommonImageAdapter adapter2;
    private CommonImageAdapter adapter3;

    private List<String> img1;//用于展示
    private List<String> img2;//用于展示
    private List<String> img3;//用于展示

    private List<String> images1;//用于接口
    private List<String> images2;//用于接口
    private List<String> images3;//用于接口

    private List<LocalMedia> images;


    private int num = 3;
    private int type = 0;

    private int from;// 0 新增进入  1 扫码进入

    // private String positionName;
    private String positionCode;

    private CameraDictData dictData;
    private List<PcsDictItem> pcsDictItems;

    private List<String> ssfj;//所属分局
    private List<String> sspcs;//所属派出所
    private List<String> lwfs;//联网方式
    private List<String> qdfs;//取电方式
    private List<String> sbzt;//设备状态
    private List<String> sbcs;//设备厂商
    private List<String> sxjlx;//摄像机类型
    private List<String> sxjgnlx;//摄像机功能类型
    private List<String> sxjwzlx;//摄像机位置类型
    private List<String> azwz;//安装位置
    private List<String> sfzddw;//是否重点点位
    /*    private List<String> sq;//社区
        private List<String> jd;//街道*/
    private List<String> ssbmhy;//所属部门行业
    private List<String> xzqy;//行政区域
    private List<String> hb;//横臂
    private List<String> dwjklx;//点位监控类型
    private List<String> psfx;//拍摄方向
    private List<String> bgsx;//补光属性
    private List<String> sfysyq;//是否有拾音器
    private List<String> lxbcts;//录像保存天数
    private List<String> jsqs;//建设期数
    private List<String> sxjbmgs;//摄像机编码格式
    private List<String> sxjfbl;//摄像机分辨率

    private String ssfjStr;//所属分局
    private String sspcsStr;//所属派出所
    private String lwfsStr;//联网方式
    private String qdfsStr;//取电方式
    private String sbztStr;//设备状态
    private String sbcsStr;//设备厂商
    private String sxjlxStr;//摄像机类型
    private String sxjgnlxStr;//摄像机功能类型
    private String sxjwzlxStr;//摄像机位置类型
    private String azwzStr;//安装位置
    private String sfzddwStr;//是否重点点位
    /*    private String sqStr;//社区
        private String jdStr;//街道*/
    private String ssbmhyStr;//所属部门行业
    private String xzqyStr;//行政区域
    private String hbStr;//横臂
    private String dwjklxStr;//点位监控类型
    private String psfxStr;//拍摄方向
    private String bgsxStr;//补光属性
    private String sfysyqStr;//是否有拾音器
    private String lxbctsStr;//录像保存天数
    private String jsqsStr;//建设期数
    private String sxjbmgsStr;//摄像机编码格式
    private String sxjfblStr;//摄像机分辨率
    private Date azsj;//安装时间

    private String checkUserId;
    private String notifyUserId;
    private int currentStatus;
    private boolean isEdit;
    private String cameraId;
    private PtCameraInfo entityInfo;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Long orgId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_camera_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        etLongitude.setFocusable(false);
        etLatitude.setFocusable(false);
        if (!isEdit) {
            if (from == 1) {
                etDwbm.setText(positionCode);
                //etDwmc.setText(positionName);
                etDwbm.setFocusable(false);
                //etDwmc.setFocusable(false);
            }
            getDictData();
            initAdapter();
        } else {
            initAdapter2();
            tvTitle.setText("修  改");
            etDwbm.setFocusable(false);
            //etDwmc.setText(positionName);
        }

    }

    private void getDictData() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.dictCamera, "请求中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                dictData = FastJsonUtil.getObject(json, CameraDictData.class);
                //装配字典信息
                if (dictData != null) {
                    initDictData();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 修改定制的获取字典方法
     */
    private void getDictData2() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.dictCamera, "请求中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                dictData = FastJsonUtil.getObject(json, CameraDictData.class);
                //装配字典信息
                if (dictData != null) {
                    initDictData2();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    private void initDictData() {
/*        sfzddw = new ArrayList<>();
        sq = new ArrayList<>();
        jd = new ArrayList<>();*/

        sxjbmgs = new ArrayList<>();
        for (CameraDictData.PTCAMERAENCODETYPE item : dictData.getPT_CAMERA_ENCODE_TYPE()) {
            sxjbmgs.add(item.getDataName());
        }

        sxjfbl = new ArrayList<>();
        for (CameraDictData.PTRESOLUTION item : dictData.getPT_RESOLUTION()) {
            sxjfbl.add(item.getDataName());
        }

        psfx = new ArrayList<>();
        for (CameraDictData.PTCAMERADIRECTION item : dictData.getPT_CAMERA_DIRECTION()) {
            psfx.add(item.getDataName());
        }


        ssfj = new ArrayList<>();
        for (CameraDictData.PTFENJU item : dictData.getPT_FEN_JU()) {
            ssfj.add(item.getDataName());
        }

        lwfs = new ArrayList<>();
        for (CameraDictData.PTNETWORKPROPERTIES item : dictData.getPT_NETWORK_PROPERTIES()) {
            lwfs.add(item.getDataName());
        }

        qdfs = new ArrayList<>();
        for (CameraDictData.PTPOWERTAKETYPE item : dictData.getPT_POWER_TAKE_TYPE()) {
            qdfs.add(item.getDataName());
        }

        sbzt = new ArrayList<>();
        for (CameraDictData.PTDEVICESTATE item : dictData.getPT_DEVICE_STATE()) {
            sbzt.add(item.getDataName());
        }

        sbcs = new ArrayList<>();
        for (CameraDictData.PTMANUFACTURER item : dictData.getPT_MANUFACTURER()) {
            sbcs.add(item.getDataName());
        }

        sxjlx = new ArrayList<>();
        for (CameraDictData.PTCAMERATYPE item : dictData.getPT_CAMERA_TYPE()) {
            sxjlx.add(item.getDataName());
        }

        sxjgnlx = new ArrayList<>();
        for (CameraDictData.PTCAMERAFUNTYPE item : dictData.getPT_CAMERA_FUN_TYPE()) {
            sxjgnlx.add(item.getDataName());
        }

        sxjwzlx = new ArrayList<>();
        for (CameraDictData.PTCAMERAPOSITIONTYPE item : dictData.getPT_CAMERA_POSITION_TYPE()) {
            sxjwzlx.add(item.getDataName());
        }

        azwz = new ArrayList<>();
        for (CameraDictData.PTINDOORORNOT item : dictData.getPT_INDOOR_OR_NOT()) {
            azwz.add(item.getDataName());
        }

        ssbmhy = new ArrayList<>();
        for (CameraDictData.PTDEPARTMENT item : dictData.getPT_DEPARTMENT()) {
            ssbmhy.add(item.getDataName());
        }

        xzqy = new ArrayList<>();
        for (CameraDictData.PTADMINISTRATIVEEREA item : dictData.getPT_ADMINISTRATIVE_EREA()) {
            xzqy.add(item.getDataName());
        }

        hb = new ArrayList<>();
        for (CameraDictData.PTCROSSARM1 item : dictData.getPT_CROSS_ARM1()) {
            hb.add(item.getDataName());
        }

        dwjklx = new ArrayList<>();
        for (CameraDictData.PTMONITORTYPE item : dictData.getPT_MONITOR_TYPE()) {
            dwjklx.add(item.getDataName());
        }

        bgsx = new ArrayList<>();
        for (CameraDictData.PTFILLLIGTHATTR item : dictData.getPT_FILL_LIGTH_ATTR()) {
            bgsx.add(item.getDataName());
        }

        sfysyq = new ArrayList<>();
        for (CameraDictData.PTSOUNDALARM item : dictData.getPT_SOUND_ALARM()) {
            sfysyq.add(item.getDataName());
        }

        lxbcts = new ArrayList<>();
        for (CameraDictData.PTRECODESAVETYPE item : dictData.getPT_RECODE_SAVE_TYPE()) {
            lxbcts.add(item.getDataName());
        }

        jsqs = new ArrayList<>();
        for (CameraDictData.PTBUILDPERIOD item : dictData.getPT_BUILD_PERIOD()) {
            jsqs.add(item.getDataName());
        }
    }

    /**
     * 修改定制的插入字典信息
     */
    private void initDictData2() {

        sxjbmgs = new ArrayList<>();
        for (CameraDictData.PTCAMERAENCODETYPE item : dictData.getPT_CAMERA_ENCODE_TYPE()) {
            sxjbmgs.add(item.getDataName());
            if (!StringUtil.isEmpty(sxjbmgsStr) && sxjbmgsStr.equals(item.getDataValue())) {
                tvSelectSxjbmgs.setText(item.getDataName());
            }
        }

        sxjfbl = new ArrayList<>();
        for (CameraDictData.PTRESOLUTION item : dictData.getPT_RESOLUTION()) {
            sxjfbl.add(item.getDataName());
            if (!StringUtil.isEmpty(sxjfblStr) && sxjfblStr.equals(item.getDataValue())) {
                tvSelectSxjfbl.setText(item.getDataName());
            }
        }

        psfx = new ArrayList<>();
        for (CameraDictData.PTCAMERADIRECTION item : dictData.getPT_CAMERA_DIRECTION()) {
            psfx.add(item.getDataName());
            if (!StringUtil.isEmpty(psfxStr) && psfxStr.equals(item.getDataValue())) {
                tvSelectPsfx.setText(item.getDataName());
            }
        }


        ssfj = new ArrayList<>();
        for (CameraDictData.PTFENJU item : dictData.getPT_FEN_JU()) {
            ssfj.add(item.getDataName());
            if (!StringUtil.isEmpty(ssfjStr) && ssfjStr.equals(item.getDataValue())) {
                tvSelectSsfj.setText(item.getDataName());
            }
        }

        lwfs = new ArrayList<>();
        for (CameraDictData.PTNETWORKPROPERTIES item : dictData.getPT_NETWORK_PROPERTIES()) {
            lwfs.add(item.getDataName());
            if (!StringUtil.isEmpty(lwfsStr) && lwfsStr.equals(item.getDataValue())) {
                tvSelectLwfs.setText(item.getDataName());
            }
        }

        qdfs = new ArrayList<>();
        for (CameraDictData.PTPOWERTAKETYPE item : dictData.getPT_POWER_TAKE_TYPE()) {
            qdfs.add(item.getDataName());
            if (!StringUtil.isEmpty(qdfsStr) && qdfsStr.equals(item.getDataValue())) {
                tvSelectQdfs.setText(item.getDataName());
            }
        }

        sbzt = new ArrayList<>();
        for (CameraDictData.PTDEVICESTATE item : dictData.getPT_DEVICE_STATE()) {
            sbzt.add(item.getDataName());
            if (!StringUtil.isEmpty(sbztStr) && sbztStr.equals(item.getDataValue())) {
                tvSelectSbzt.setText(item.getDataName());
            }
        }

        sbcs = new ArrayList<>();
        for (CameraDictData.PTMANUFACTURER item : dictData.getPT_MANUFACTURER()) {
            sbcs.add(item.getDataName());
            if (!StringUtil.isEmpty(sbcsStr) && sbcsStr.equals(item.getDataValue())) {
                tvSelectSbcs.setText(item.getDataName());
            }
        }

        sxjlx = new ArrayList<>();
        for (CameraDictData.PTCAMERATYPE item : dictData.getPT_CAMERA_TYPE()) {
            sxjlx.add(item.getDataName());
            if (!StringUtil.isEmpty(sxjlxStr) && sxjlxStr.equals(item.getDataValue())) {
                tvSelectSxjlx.setText(item.getDataName());
            }
        }

        sxjgnlx = new ArrayList<>();
        for (CameraDictData.PTCAMERAFUNTYPE item : dictData.getPT_CAMERA_FUN_TYPE()) {
            sxjgnlx.add(item.getDataName());
            if (!StringUtil.isEmpty(sxjgnlxStr) && sxjgnlxStr.equals(item.getDataValue())) {
                tvSelectSxjgnlx.setText(item.getDataName());
            }
        }

        sxjwzlx = new ArrayList<>();
        for (CameraDictData.PTCAMERAPOSITIONTYPE item : dictData.getPT_CAMERA_POSITION_TYPE()) {
            sxjwzlx.add(item.getDataName());
            if (!StringUtil.isEmpty(sxjwzlxStr) && sxjwzlxStr.equals(item.getDataValue())) {
                tvSelectSxjwzlx.setText(item.getDataName());
            }
        }

        azwz = new ArrayList<>();
        for (CameraDictData.PTINDOORORNOT item : dictData.getPT_INDOOR_OR_NOT()) {
            azwz.add(item.getDataName());
            if (!StringUtil.isEmpty(azwzStr) && azwzStr.equals(item.getDataValue())) {
                tvSelectAzwz.setText(item.getDataName());
            }
        }

        ssbmhy = new ArrayList<>();
        for (CameraDictData.PTDEPARTMENT item : dictData.getPT_DEPARTMENT()) {
            ssbmhy.add(item.getDataName());
            if (!StringUtil.isEmpty(ssbmhyStr) && ssbmhyStr.equals(item.getDataValue())) {
                tvSelectSsbmhy.setText(item.getDataName());
            }
        }

        xzqy = new ArrayList<>();
        for (CameraDictData.PTADMINISTRATIVEEREA item : dictData.getPT_ADMINISTRATIVE_EREA()) {
            xzqy.add(item.getDataName());
            if (!StringUtil.isEmpty(xzqyStr) && xzqyStr.equals(item.getDataValue())) {
                tvSelectXzqy.setText(item.getDataName());
            }
        }

        hb = new ArrayList<>();
        for (CameraDictData.PTCROSSARM1 item : dictData.getPT_CROSS_ARM1()) {
            hb.add(item.getDataName());
            if (!StringUtil.isEmpty(hbStr) && hbStr.equals(item.getDataValue())) {
                tvSelectHb.setText(item.getDataName());
            }
        }

        dwjklx = new ArrayList<>();
        for (CameraDictData.PTMONITORTYPE item : dictData.getPT_MONITOR_TYPE()) {
            dwjklx.add(item.getDataName());
            if (!StringUtil.isEmpty(dwjklxStr) && dwjklxStr.equals(item.getDataValue())) {
                tvSelectDwjklx.setText(item.getDataName());
            }
        }

        bgsx = new ArrayList<>();
        for (CameraDictData.PTFILLLIGTHATTR item : dictData.getPT_FILL_LIGTH_ATTR()) {
            bgsx.add(item.getDataName());
            if (!StringUtil.isEmpty(bgsxStr) && bgsxStr.equals(item.getDataValue())) {
                tvSelectBgsx.setText(item.getDataName());
            }
        }

        sfysyq = new ArrayList<>();
        for (CameraDictData.PTSOUNDALARM item : dictData.getPT_SOUND_ALARM()) {
            sfysyq.add(item.getDataName());
            if (!StringUtil.isEmpty(sfysyqStr) && sfysyqStr.equals(item.getDataValue())) {
                tvSelectSfysyq.setText(item.getDataName());
            }
        }

        lxbcts = new ArrayList<>();
        for (CameraDictData.PTRECODESAVETYPE item : dictData.getPT_RECODE_SAVE_TYPE()) {
            lxbcts.add(item.getDataName());
            if (!StringUtil.isEmpty(lxbctsStr) && lxbctsStr.equals(item.getDataValue())) {
                tvSelectLxbcts.setText(item.getDataName());
            }
        }

        jsqs = new ArrayList<>();
        for (CameraDictData.PTBUILDPERIOD item : dictData.getPT_BUILD_PERIOD()) {
            jsqs.add(item.getDataName());
            if (!StringUtil.isEmpty(jsqsStr) && jsqsStr.equals(item.getDataValue())) {
                tvSelectJsqs.setText(item.getDataName());
            }
        }
    }


    private void getPcsData(String id) {
        if (pcsDictItems == null) {
            pcsDictItems = new ArrayList<>();
        }
        pcsDictItems.clear();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        ApiClient.requestNetGet(this, AppConfig.pcsListDict, "选择中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<PcsDictItem> list = FastJsonUtil.getList(json, PcsDictItem.class);
                if (list != null) {
                    pcsDictItems.addAll(list);
                    initPcs();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initPcs() {
        if (sspcs == null) {
            sspcs = new ArrayList<>();
        }
        sspcs.clear();
        for (PcsDictItem item : pcsDictItems) {
            sspcs.add(item.getOrgName());
        }
    }

    private void initClick() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCameraDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        llExpand.setVisibility(llExpand.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        ivExpand.setSelected(llExpand.getVisibility() == View.VISIBLE);
                    }
                });
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testPlace(0);

            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先验空
                testPlace(1);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etDwbm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    //失去焦点
                    if (!StringUtil.isEmpty(etDwbm.getText().toString().trim())) {
                        checkPositionCode();
                    }
                }
            }
        });

    }


    private void checkPositionCode() {
        if (!StringUtil.isEmpty(positionCode)) {
            if (positionCode.equals(etDwbm.getText().toString().trim())) {
                return;
            }
        }
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("positionCode", etDwbm.getText().toString().trim());
        ApiClient.requestNetPost(this, AppConfig.getPosition, "检测中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtil.isEmpty(json)) {
                    ToastUtil.toast("点位编码不存在，请重新输入正确的点位编码！");
                    etDwbm.setText("");
                    etDwbm.requestFocus();
                    return;
                }
                CheckPositionCodeEntity checkPositionCodeEntity = FastJsonUtil.getObject(json, CheckPositionCodeEntity.class);
                if (checkPositionCodeEntity == null) {
                    ToastUtil.toast("点位编码不存在，请重新输入正确的点位编码！");
                    etDwbm.setText("");
                    etDwbm.requestFocus();
                } else {
                    ToastUtil.toast("点位编码正确，为您填入经纬度！");
                    positionCode = etDwbm.getText().toString();
                    etLongitude.setText(StringUtil.isEmpty(checkPositionCodeEntity.getLongitude()) ? "" : checkPositionCodeEntity.getLongitude());
                    etLatitude.setText(StringUtil.isEmpty(checkPositionCodeEntity.getLatitude()) ? "" : checkPositionCodeEntity.getLatitude());
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void showSelectDialog(List<String> options, String title, TextView goal, AddSelectResult addSelectResult) {
        hideSoftKeyboard();
        //hideSoftKeyboard2();
        hideSoftKeyboard3();
        PickerViewUtils.selectOptions(this, title, options, null, null, new PickerViewSelectOptionsResult() {
            @Override
            public void getOptionsResult(int options1, int options2, int options3) {
                goal.setText(options.get(options1));
                addSelectResult.change(options1, options2, options3);
            }
        });
    }

    private void showSelectTimeDialog() {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        hideSoftKeyboard();
        // hideSoftKeyboard2();
        hideSoftKeyboard3();
        Calendar nowDate = Calendar.getInstance();
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                azsj = date;
                tvSelectAzsj.setText(choiceTime);


            }
        }).setDate(nowDate)//设置系统时间为当前时间
                .setType(new boolean[]{true, true, true, true, true, true})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText("安装时间")//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }


    @OnClick({R.id.tv_select_sxjbmgs, R.id.tv_select_sxjfbl, R.id.tv_select_psfx, R.id.tv_select_ssfj, R.id.tv_select_sspcs, R.id.tv_select_lwfs, R.id.tv_select_qdfs, R.id.tv_select_sbzt, R.id.tv_select_sbcs, R.id.tv_select_sxjlx, R.id.tv_select_sxjgnlx, R.id.tv_select_sxjwzlx, R.id.tv_select_azwz, R.id.tv_select_sfzddw, R.id.tv_select_ssbmhy, R.id.tv_select_xzqy, R.id.tv_select_hb, R.id.tv_select_dwjklx, R.id.tv_select_bgsx, R.id.tv_select_sfysyq, R.id.tv_select_azsj, R.id.tv_select_lxbcts, R.id.tv_select_jsqs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_sxjbmgs:
                showSelectDialog(sxjbmgs, "摄像机编码格式", tvSelectSxjbmgs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjbmgsStr = dictData.getPT_CAMERA_ENCODE_TYPE().get(options1).getDataValue();

                    }
                });
                break;
            case R.id.tv_select_sxjfbl:
                showSelectDialog(sxjfbl, "摄像机分辨率", tvSelectSxjfbl, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjfblStr = dictData.getPT_RESOLUTION().get(options1).getDataValue();

                    }
                });
                break;
            case R.id.tv_select_psfx:
                showSelectDialog(psfx, "拍摄方向", tvSelectPsfx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        psfxStr = dictData.getPT_CAMERA_DIRECTION().get(options1).getDataValue();

                    }
                });
                break;
            case R.id.tv_select_ssfj:
                showSelectDialog(ssfj, "所属分局", tvSelectSsfj, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        ssfjStr = dictData.getPT_FEN_JU().get(options1).getDataValue();
                        tvSelectSspcs.setText("");
                        sspcsStr = "";
                        getPcsData(ssfjStr);
                    }
                });
                break;
            case R.id.tv_select_sspcs:
                if (StringUtil.isEmpty(tvSelectSsfj.getText().toString())) {
                    ToastUtil.toast("请先选择所属分局");
                    return;
                }
                if (sspcs == null || sspcs.size() == 0) {
                    ToastUtil.toast("该分局下暂无派出所信息，请重新选择");
                    return;
                }
                showSelectDialog(sspcs, "所属派出所", tvSelectSspcs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sspcsStr = pcsDictItems.get(options1).getOrgName() + "";
                        orgId = Long.parseLong(pcsDictItems.get(options1).getId() + "");
                    }
                });
                break;
            case R.id.tv_select_lwfs:
                showSelectDialog(lwfs, "联网方式", tvSelectLwfs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        lwfsStr = dictData.getPT_NETWORK_PROPERTIES().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_qdfs:
                showSelectDialog(qdfs, "取电方式", tvSelectQdfs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        qdfsStr = dictData.getPT_POWER_TAKE_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sbzt:
                showSelectDialog(sbzt, "设备状态", tvSelectSbzt, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sbztStr = dictData.getPT_DEVICE_STATE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sbcs:
                showSelectDialog(sbcs, "设备厂商", tvSelectSbcs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sbcsStr = dictData.getPT_MANUFACTURER().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sxjlx:
                showSelectDialog(sxjlx, "摄像机类型", tvSelectSxjlx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjlxStr = dictData.getPT_CAMERA_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sxjgnlx:
                showSelectDialog(sxjgnlx, "摄像机功能类型", tvSelectSxjgnlx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjgnlxStr = dictData.getPT_CAMERA_FUN_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sxjwzlx:
                showSelectDialog(sxjwzlx, "摄像机位置类型", tvSelectSxjwzlx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjwzlxStr = dictData.getPT_CAMERA_POSITION_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_azwz:
                showSelectDialog(azwz, "安装位置", tvSelectAzwz, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        azwzStr = dictData.getPT_INDOOR_OR_NOT().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sfzddw:

                break;
            case R.id.tv_select_ssbmhy:
                showSelectDialog(ssbmhy, "所属部门/行业", tvSelectSsbmhy, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        ssbmhyStr = dictData.getPT_DEPARTMENT().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_xzqy:
                showSelectDialog(xzqy, "行政区域", tvSelectXzqy, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        xzqyStr = dictData.getPT_ADMINISTRATIVE_EREA().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_hb:
                showSelectDialog(hb, "横臂", tvSelectHb, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        hbStr = dictData.getPT_CROSS_ARM1().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_dwjklx:
                showSelectDialog(dwjklx, "点位监控类型", tvSelectDwjklx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        dwjklxStr = dictData.getPT_MONITOR_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_bgsx:
                showSelectDialog(bgsx, "补光属性", tvSelectBgsx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        bgsxStr = dictData.getPT_FILL_LIGTH_ATTR().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sfysyq:
                showSelectDialog(sfysyq, "是否有拾音器", tvSelectSfysyq, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sfysyqStr = dictData.getPT_SOUND_ALARM().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_azsj:
                showSelectTimeDialog();
                break;
            case R.id.tv_select_lxbcts:
                showSelectDialog(lxbcts, "录像保存天数", tvSelectLxbcts, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        lxbctsStr = dictData.getPT_RECODE_SAVE_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_jsqs:
                showSelectDialog(jsqs, "建设期数", tvSelectJsqs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        jsqsStr = dictData.getPT_BUILD_PERIOD().get(options1).getDataValue();
                    }
                });
                break;
        }
    }

    private interface AddSelectResult {
        void change(int options1, int options2, int options3);
    }


    private void testPlace(int currentStatus) {
        if (StringUtil.isEmpty(etDwbm.getText().toString().trim())) {
            ToastUtil.toast("请填写点位编码");
            return;
        }
        if (StringUtil.isEmpty(etDwmc.getText().toString().trim())) {
            ToastUtil.toast("请填写点位名称");
            return;
        }
        if (StringUtil.isEmpty(etSbmc.getText().toString().trim())) {
            ToastUtil.toast("请填写设备名称");
            return;
        }
        if (StringUtil.isEmpty(etSbbm.getText().toString().trim())) {
            ToastUtil.toast("请填写设备编码");
            return;
        }
        if (StringUtil.isEmpty(etIpv4.getText().toString().trim())) {
            ToastUtil.toast("请填写IPV4地址");
            return;
        }
        if (!RegexUtils.isIP(etIpv4.getText().toString().trim())) {
            ToastUtil.toast("请填写格式正确的IPV4地址");
            return;
        }
        if (StringUtil.isEmpty(tvSelectSsfj.getText().toString().trim())) {
            ToastUtil.toast("请填写所属分局");
            return;
        }
        if (StringUtil.isEmpty(tvSelectSspcs.getText().toString().trim())) {
            ToastUtil.toast("请填写所属派出所");
            return;
        }
        if (StringUtil.isEmpty(etAzdz.getText().toString().trim())) {
            ToastUtil.toast("请填写安装地址");
            return;
        }
        if (StringUtil.isEmpty(etLongitude.getText().toString().trim())) {
            ToastUtil.toast("请填写经度");
            return;
        }
        if (StringUtil.isEmpty(etLatitude.getText().toString().trim())) {
            ToastUtil.toast("请填写纬度");
            return;
        }
        if (StringUtil.isEmpty(tvSelectLwfs.getText().toString().trim())) {
            ToastUtil.toast("请填写联网方式");
            return;
        }
        if (StringUtil.isEmpty(etLwrlxfs.getText().toString().trim())) {
            ToastUtil.toast("请填写联网人联系方式");
            return;
        }
        if (StringUtil.isEmpty(tvSelectQdfs.getText().toString().trim())) {
            ToastUtil.toast("请填写取电方式");
            return;
        }
        if (StringUtil.isEmpty(etQdrlxfs.getText().toString().trim())) {
            ToastUtil.toast("请填写取电人联系方式");
            return;
        }
        if (images1.size() == 0) {
            ToastUtil.toast("请上传施工图纸");
            return;
        }
        if (images2.size() == 0) {
            ToastUtil.toast("请上传全景图");
            return;
        }
        if (images3.size() == 0) {
            ToastUtil.toast("请上传摄像机特写");
            return;
        }
        this.currentStatus = currentStatus;
        if (isEdit && etSbbm.getText().toString().trim().equals(entityInfo.getCameraNo())) {
            // checkIpv4Address();
            if (isEdit && etIpv4.getText().toString().trim().equals(entityInfo.getCameraIp())) {
                toSelectCheckUser();
            } else {
                checkIpv4Address();
            }
        } else {
            if (isEdit && etIpv4.getText().toString().trim().equals(entityInfo.getCameraIp())) {
                toSelectCheckUser();
            } else {
                checkCameraNo();
            }
        }
    }

    private void checkCameraNo() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraNo", etSbbm.getText().toString().trim());
        ApiClient.requestNetPost(this, AppConfig.toHeavyNumber, "校验中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                boolean a = FastJsonUtil.getObject(json, boolean.class);
                if (a) {
                    //ToastUtil.toast("当前填写设备编码可用！");
                    if (isEdit && etIpv4.getText().toString().trim().equals(entityInfo.getCameraIp())) {
                        toSelectCheckUser();
                    } else {
                        checkIpv4Address();
                    }
                } else {
                    ToastUtil.toast("当前填写设备编码已重复，请重填！");
                    etSbbm.requestFocus();
                    etSbbm.setText("");

                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast("IPV4地址校验失败：" + msg);
            }
        });
    }

    private void checkIpv4Address() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraIp", etIpv4.getText().toString().trim());
        ApiClient.requestNetPost(this, AppConfig.whetherToRepeat, "校验中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                boolean a = FastJsonUtil.getObject(json, boolean.class);
                if (a) {
                    //ToastUtil.toast("当前填写IPV4地址可用！");
                    toSelectCheckUser();
                } else {
                    ToastUtil.toast("当前填写IPV4地址已重复，请重填！");
                    etIpv4.requestFocus();
                    etIpv4.setText("");

                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast("IPV4地址校验失败：" + msg);
            }
        });
    }

    private void toSelectCheckUser() {
        Bundle bundle = new Bundle();
        bundle.putString("positionName", etDwmc.getText().toString());
        bundle.putString("cameraName", etSbmc.getText().toString());
        toTheActivity(AddCheckUserActivity.class, bundle);
    }


    private void getData() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraId", cameraId);
        ApiClient.requestNetPost(this, AppConfig.getCamera, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                entityInfo = FastJsonUtil.getObject(FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "ptCameraInfo"), PtCameraInfo.class);
                String s1 = FastJsonUtil.getString(json, "imgPath");
                String s2 = FastJsonUtil.getString(json, "locationPhotoPath");
                String s3 = FastJsonUtil.getString(json, "specialPhotoPath");
                if (!StringUtil.isEmpty(s1)) {
                    img1.addAll(Arrays.asList(s1.split("!")));
                    if (img1.size() < num) {
                        img1.add("");
                    }
                    adapter1.notifyDataSetChanged();
                }
                if (!StringUtil.isEmpty(s2)) {
                    img2.addAll(Arrays.asList(s2.split("!")));
                    if (img2.size() < num) {
                        img2.add("");
                    }
                    adapter2.notifyDataSetChanged();
                }
                if (!StringUtil.isEmpty(s3)) {
                    img3.addAll(Arrays.asList(s3.split("!")));
                    if (img3.size() < num) {
                        img3.add("");
                    }
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
        if (entityInfo == null) {
            return;
        }
        etDwmc.setText(StringUtil.isEmpty(entityInfo.getPointName()) ? "" : entityInfo.getPointName());
        etDwbm.setText(StringUtil.isEmpty(entityInfo.getPositionCode()) ? "" : entityInfo.getPositionCode());
        etSbmc.setText(StringUtil.isEmpty(entityInfo.getCameraName()) ? "" : entityInfo.getCameraName());
        etSbbm.setText(StringUtil.isEmpty(entityInfo.getCameraNo()) ? "" : entityInfo.getCameraNo());
        ssfjStr = StringUtil.isEmpty(entityInfo.getFenJu()) ? "" : entityInfo.getFenJu();
        if (!StringUtil.isEmpty(ssfjStr)) {
            getPcsData(ssfjStr);
        }
        sspcsStr = StringUtil.isEmpty(entityInfo.getPoliceStation()) ? "" : entityInfo.getPoliceStation();
        orgId = entityInfo.getOrgId();
        tvSelectSspcs.setText(StringUtil.isEmpty(entityInfo.getPoliceStation()) ? "" : entityInfo.getPoliceStation());
        etAzdz.setText(StringUtil.isEmpty(entityInfo.getAddress()) ? "" : entityInfo.getAddress());
        etLongitude.setText(StringUtil.isEmpty(entityInfo.getLongitude()) ? "" : entityInfo.getLongitude());
        etLatitude.setText(StringUtil.isEmpty(entityInfo.getLatitude()) ? "" : entityInfo.getLatitude());
        lwfsStr = entityInfo.getNetworkProperties() == null ? "" : String.valueOf(entityInfo.getNetworkProperties());
        etLwrlxfs.setText(StringUtil.isEmpty(entityInfo.getNetworkPropertiesTel()) ? "" : entityInfo.getNetworkPropertiesTel());
        qdfsStr = entityInfo.getPowerTakeType() == null ? "" : String.valueOf(entityInfo.getPowerTakeType());
        etQdrlxfs.setText(StringUtil.isEmpty(entityInfo.getPowerTakeTypeTel()) ? "" : entityInfo.getPowerTakeTypeTel());


        etIpv4.setText(StringUtil.isEmpty(entityInfo.getCameraIp()) ? "" : entityInfo.getCameraIp());
        sbztStr = StringUtil.isEmpty(entityInfo.getCameraState()) ? "" : entityInfo.getCameraState();

        etGjbh.setText(StringUtil.isEmpty(entityInfo.getMemberbarCode()) ? "" : entityInfo.getMemberbarCode());
        etEwmbh.setText(StringUtil.isEmpty(entityInfo.getQrCodeNumber()) ? "" : entityInfo.getQrCodeNumber());
        etIpv6.setText(StringUtil.isEmpty(entityInfo.getCameraIp6()) ? "" : entityInfo.getCameraIp6());
        etMac.setText(StringUtil.isEmpty(entityInfo.getMacAddress()) ? "" : entityInfo.getMacAddress());
        sbcsStr = StringUtil.isEmpty(entityInfo.getManufacturer()) ? "" : entityInfo.getManufacturer();
        etSbxh.setText(StringUtil.isEmpty(entityInfo.getCameraModel()) ? "" : entityInfo.getCameraModel());

        sxjlxStr = StringUtil.isEmpty(entityInfo.getCameraType()) ? "" : entityInfo.getCameraType();
        sxjgnlxStr = StringUtil.isEmpty(entityInfo.getCameraFunType()) ? "" : entityInfo.getCameraFunType();
        sxjwzlxStr = StringUtil.isEmpty(entityInfo.getPositionType()) ? "" : entityInfo.getPositionType();
        azwzStr = entityInfo.getIndoorOrNot() == null ? "" : String.valueOf(entityInfo.getIndoorOrNot());
        etZdjkdw.setText(StringUtil.isEmpty(entityInfo.getImportWatch()) ? "" : entityInfo.getImportWatch());
        etSq.setText(StringUtil.isEmpty(entityInfo.getCommunity()) ? "" : entityInfo.getCommunity());
        etJd.setText(StringUtil.isEmpty(entityInfo.getStreet()) ? "" : entityInfo.getStreet());
        ssbmhyStr = StringUtil.isEmpty(entityInfo.getIndustryOwn()) ? "" : entityInfo.getIndustryOwn();
        xzqyStr = StringUtil.isEmpty(entityInfo.getAreaCode()) ? "" : entityInfo.getAreaCode();
        etJsgd.setText(StringUtil.isEmpty(entityInfo.getInstallHeight()) ? "" : entityInfo.getInstallHeight());
        hbStr = StringUtil.isEmpty(entityInfo.getCrossArm1()) ? "" : entityInfo.getCrossArm1();

        dwjklxStr = StringUtil.isEmpty(entityInfo.getMonitorType()) ? "" : entityInfo.getMonitorType();
        psfxStr = StringUtil.isEmpty(entityInfo.getCameraDirection()) ? "" : entityInfo.getCameraDirection();
        bgsxStr = entityInfo.getFillLigthAttr() == null ? "" : String.valueOf(entityInfo.getFillLigthAttr());
        sxjbmgsStr = entityInfo.getCameraEncodeType() == null ? "" : String.valueOf(entityInfo.getCameraEncodeType());
        sfysyqStr = entityInfo.getSoundAlarm() == null ? "" : String.valueOf(entityInfo.getSoundAlarm());
        sxjfblStr = StringUtil.isEmpty(entityInfo.getResolution()) ? "" : entityInfo.getResolution();
        tvSelectAzsj.setText(entityInfo.getInstallTime() == null ? "" : formatter.format(entityInfo.getInstallTime()));
        azsj = entityInfo.getInstallTime();
        etSsxqgajg.setText(StringUtil.isEmpty(entityInfo.getPoliceAreaCode()) ? "" : entityInfo.getPoliceAreaCode());
        lxbctsStr = entityInfo.getRecodeSaveType() == null ? "" : String.valueOf(entityInfo.getRecodeSaveType());
        etJrfs.setText(StringUtil.isEmpty(entityInfo.getAccessModel()) ? "" : entityInfo.getAccessModel());
        jsqsStr = entityInfo.getBuildPeriod() == null ? "" : String.valueOf(entityInfo.getBuildPeriod());
        etOsd.setText(StringUtil.isEmpty(entityInfo.getOsdName()) ? "" : entityInfo.getOsdName());

        etGldw.setText(StringUtil.isEmpty(entityInfo.getManagerUnit()) ? "" : entityInfo.getManagerUnit());
        etGldwlxfs.setText(StringUtil.isEmpty(entityInfo.getManagerUnitTel()) ? "" : entityInfo.getManagerUnitTel());
        etWhdw.setText(StringUtil.isEmpty(entityInfo.getMaintainUnit()) ? "" : entityInfo.getMaintainUnit());
        etWhdwlxfs.setText(StringUtil.isEmpty(entityInfo.getManagerUnitTel()) ? "" : entityInfo.getMaintainUnitTel());
/*        etSbr.setText(StringUtil.isEmpty(entityInfo.getAddId()) ? "" : entityInfo.getAddId());
        etSbrlxfs.setText(StringUtil.isEmpty(entityInfo.getAddTel()) ? "" : entityInfo.getAddTel());
        etSpr.setText(StringUtil.isEmpty(entityInfo.getApprovalId()) ? "" : entityInfo.getApprovalId());
        etSprlxfs.setText(StringUtil.isEmpty(entityInfo.getApprovalTel()) ? "" : entityInfo.getApprovalTel());*/

        if (!StringUtil.isEmpty(entityInfo.getImgPath())) {
            images1.addAll(Arrays.asList(entityInfo.getImgPath().split(",")));
        }
        if (!StringUtil.isEmpty(entityInfo.getLocationPhotoPath())) {
            images2.addAll(Arrays.asList(entityInfo.getLocationPhotoPath().split(",")));
        }
        if (!StringUtil.isEmpty(entityInfo.getSpecialPhotoPath())) {
            images3.addAll(Arrays.asList(entityInfo.getSpecialPhotoPath().split(",")));
        }
        getDictData2();

    }

    private void submit() {
        /**
         * 东二环路淝河路西南角西40米
         * XLBH-FH-0197
         */
        PtCameraInfo ptCameraInfo = new PtCameraInfo();
        ptCameraInfo.setPositionCode(etDwbm.getText().toString().trim());
        ptCameraInfo.setPointName(etDwmc.getText().toString().trim());
        ptCameraInfo.setCameraName(etSbmc.getText().toString());
        ptCameraInfo.setCameraNo(etSbbm.getText().toString());
        ptCameraInfo.setFenJu(ssfjStr);
        ptCameraInfo.setPoliceStation(sspcsStr);
        ptCameraInfo.setOrgId(orgId);
        ptCameraInfo.setAddress(etAzdz.getText().toString());
        ptCameraInfo.setLatitude(etLatitude.getText().toString());
        ptCameraInfo.setLongitude(etLongitude.getText().toString());
        ptCameraInfo.setNetworkProperties(Long.parseLong(lwfsStr));
        ptCameraInfo.setNetworkPropertiesTel(etLwrlxfs.getText().toString());
        ptCameraInfo.setPowerTakeType(Long.parseLong(qdfsStr));
        ptCameraInfo.setPowerTakeTypeTel(etQdrlxfs.getText().toString());
        ptCameraInfo.setImgPath(takeImgPathString(images1));
        ptCameraInfo.setLocationPhotoPath(takeImgPathString(images2));
        ptCameraInfo.setSpecialPhotoPath(takeImgPathString(images3));


        //下面选填
        ptCameraInfo.setCameraIp(etIpv4.getText().toString());
        ptCameraInfo.setCameraState(sbztStr);//设备状态
        ptCameraInfo.setMemberbarCode(etGjbh.getText().toString());
        ptCameraInfo.setQrCodeNumber(etEwmbh.getText().toString());
        ptCameraInfo.setCameraIp6(etIpv6.getText().toString());
        ptCameraInfo.setMacAddress(etMac.getText().toString());
        ptCameraInfo.setManufacturer(sbcsStr);
        ptCameraInfo.setCameraModel(etSbxh.getText().toString());
        ptCameraInfo.setCameraType(sxjlxStr);
        ptCameraInfo.setCameraFunType(sxjgnlxStr);
        ptCameraInfo.setPositionType(sxjwzlxStr);
        if (!StringUtil.isEmpty(azwzStr)) {
            ptCameraInfo.setIndoorOrNot(Long.parseLong(azwzStr));
        }
        //ptCameraInfo.setCameraIp4(sbztStr);//是否重点点位
        ptCameraInfo.setImportWatch(etZdjkdw.getText().toString());
        ptCameraInfo.setCommunity(etSq.getText().toString());//社区
        ptCameraInfo.setStreet(etJd.getText().toString());//街道
        ptCameraInfo.setIndustryOwn(ssbmhyStr);
        ptCameraInfo.setAreaCode(xzqyStr);
        ptCameraInfo.setInstallHeight(etJsgd.getText().toString());
        ptCameraInfo.setCrossArm1(hbStr);//横臂
        ptCameraInfo.setMonitorType(dwjklxStr);
        ptCameraInfo.setCameraDirection(psfxStr);//拍摄方向
        if (!StringUtil.isEmpty(bgsxStr)) {
            ptCameraInfo.setFillLigthAttr(Long.parseLong(bgsxStr));
        }
        if (!StringUtil.isEmpty(sxjbmgsStr)) {
            ptCameraInfo.setCameraEncodeType(Long.parseLong(sxjbmgsStr));
        }
        if (!StringUtil.isEmpty(sfysyqStr)) {
            ptCameraInfo.setSoundAlarm(Long.parseLong(sfysyqStr));
        }
        ptCameraInfo.setResolution(sxjfblStr);//摄像机分辨率
        ptCameraInfo.setInstallTime(azsj);//安装时间
        ptCameraInfo.setPoliceAreaCode(etSsxqgajg.getText().toString());//所属辖区公安机关
        if (!StringUtil.isEmpty(lxbctsStr)) {
            ptCameraInfo.setRecodeSaveType(Long.parseLong(lxbctsStr));
        }
        ptCameraInfo.setAccessModel(etJrfs.getText().toString());//接入方式
        if (!StringUtil.isEmpty(jsqsStr)) {
            ptCameraInfo.setBuildPeriod(Long.parseLong(jsqsStr));
        }
        ptCameraInfo.setOsdName(etOsd.getText().toString());//osd
        ptCameraInfo.setManagerUnit(etGldw.getText().toString());
        ptCameraInfo.setManagerUnitTel(etGldwlxfs.getText().toString());
        ptCameraInfo.setMaintainUnit(etWhdw.getText().toString());
        ptCameraInfo.setMaintainUnitTel(etWhdwlxfs.getText().toString());
/*        ptCameraInfo.setAddId(etSbr.getText().toString());//上报人
        ptCameraInfo.setAddTel(etSbrlxfs.getText().toString());//上报人联系方式
        ptCameraInfo.setApprovalId(etSpr.getText().toString());//审批人
        ptCameraInfo.setApprovalTel(etSprlxfs.getText().toString());//审批人联系方式*/

        ptCameraInfo.setCurrentStatus((long) currentStatus);
        String url;
        if (isEdit) {
            ptCameraInfo.setId(cameraId);
            url = AppConfig.updateCamera;
        } else {
            url = AppConfig.addCamera;
        }
        Map<String, Object> hashMap = new HashMap<>();
/*        hashMap.put("imgFiles", takeImgPathString(images1));
        hashMap.put("locationFiles", takeImgPathString(images2));
        hashMap.put("specialFiles", takeImgPathString(images3));*/
        hashMap.put("handleConnent", "新增摄像机");
        hashMap.put("handleIp", RegularCheckUtil.getIPAddress(this));
        hashMap.put("entity", FastJsonUtil.toJSONString(ptCameraInfo));
        hashMap.put("checkUserId", checkUserId);
        hashMap.put("notifyUserId", notifyUserId);
        //hashMap.put("currentStatus", currentStatus);

        ApiClient.requestNetPostFile(this, url, "提交中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.FLUSH_LIST_DW));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    private String takeImgPathString(List<String> list) {
        StringBuilder img = new StringBuilder();
        if (list.size() != 0) {
            for (String s : list) {
                img.append(s).append(",");
            }
            img.deleteCharAt(img.length() - 1);
        }
        return img.toString();
    }


    private void initAdapter2() {
        img1 = new ArrayList<>();
        img2 = new ArrayList<>();
        img3 = new ArrayList<>();

        images1 = new ArrayList<>();
        images2 = new ArrayList<>();
        images3 = new ArrayList<>();

        adapter1 = new CommonImageAdapter(img1);
        adapter2 = new CommonImageAdapter(img2);
        adapter3 = new CommonImageAdapter(img3);

        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        type = 0;
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position, 0);
                        break;
                }
            }
        });
        adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        type = 1;
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position, 1);
                        break;
                }
            }
        });
        adapter3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        type = 2;
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position, 2);
                        break;
                }
            }
        });

        rvSgtz.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvQjt.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvSxjtx.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        rvSgtz.setAdapter(adapter1);
        rvQjt.setAdapter(adapter2);
        rvSxjtx.setAdapter(adapter3);


        RecyclerViewHelper.recyclerviewAndScrollView(rvSgtz);
        RecyclerViewHelper.recyclerviewAndScrollView(rvQjt);
        RecyclerViewHelper.recyclerviewAndScrollView(rvSxjtx);

        getData();

    }


    private void initAdapter() {
        img1 = new ArrayList<>();
        img2 = new ArrayList<>();
        img3 = new ArrayList<>();

        images1 = new ArrayList<>();
        images2 = new ArrayList<>();
        images3 = new ArrayList<>();

        adapter1 = new CommonImageAdapter(img1);
        adapter2 = new CommonImageAdapter(img2);
        adapter3 = new CommonImageAdapter(img3);

        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        type = 0;
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position, 0);
                        break;
                }
            }
        });
        adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        type = 1;
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position, 1);
                        break;
                }
            }
        });
        adapter3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        type = 2;
                        showSelectPhotoDialog();
                        break;
                    case R.id.iv_delete:
                        showDeleteDialog(position, 2);
                        break;
                }
            }
        });

        rvSgtz.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvQjt.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvSxjtx.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        rvSgtz.setAdapter(adapter1);
        rvQjt.setAdapter(adapter2);
        rvSxjtx.setAdapter(adapter3);


        RecyclerViewHelper.recyclerviewAndScrollView(rvSgtz);
        RecyclerViewHelper.recyclerviewAndScrollView(rvQjt);
        RecyclerViewHelper.recyclerviewAndScrollView(rvSxjtx);

        img1.add("");
        adapter1.notifyDataSetChanged();
        img2.add("");
        adapter2.notifyDataSetChanged();
        img3.add("");
        adapter3.notifyDataSetChanged();

    }

    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.SELECT_CHECK_SEND_RESULT:
                Map<String, Object> hashMap = (Map<String, Object>) center.getData();
                checkUserId = (String) hashMap.get("check");
                notifyUserId = (String) hashMap.get("send");
                submit();
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        //positionName = extras.getString("positionName");
        positionCode = extras.getString("positionCode");
        from = extras.getInt("from");
        isEdit = extras.getBoolean("isEdit", false);
        cameraId = extras.getString("cameraId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * 删除弹窗
     *
     * @param position
     */
    private void showDeleteDialog(int position, int type1) {
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
                        dialog.dismiss();
                        switch (type1) {
                            case 0:
                                deleteFile(images1.get(position), new DeleteResult() {
                                    @Override
                                    public void onSuccess() {
                                        img1.remove(position);
                                        images1.remove(position);
                                        if (img1.size() < num && !img1.contains("")) {
                                            img1.add("");
                                        }
                                        adapter1.notifyDataSetChanged();
                                    }
                                });
                                break;
                            case 1:
                                deleteFile(images2.get(position), new DeleteResult() {
                                    @Override
                                    public void onSuccess() {
                                        img2.remove(position);
                                        images2.remove(position);
                                        if (img2.size() < num && !img2.contains("")) {
                                            img2.add("");
                                        }
                                        adapter2.notifyDataSetChanged();
                                    }
                                });

                                break;
                            case 2:
                                deleteFile(images3.get(position), new DeleteResult() {
                                    @Override
                                    public void onSuccess() {
                                        img3.remove(position);
                                        images3.remove(position);
                                        if (img3.size() < num && !img3.contains("")) {
                                            img3.add("");
                                        }
                                        adapter3.notifyDataSetChanged();
                                    }
                                });
                                break;
                        }


                    }
                })
                .show();
    }

    private void deleteFile(String fileName, DeleteResult deleteResult) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileName", fileName);
        ApiClient.requestNetPost(this, AppConfig.deleteFile, "删除中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                deleteResult.onSuccess();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }

    private interface DeleteResult {
        void onSuccess();
    }

    private void showSelectPhotoDialog() {
        switch (type) {
            case 0:
                if (!img1.contains("") && img1.size() == num) {
                    ToastUtil.toast("最多可以选择" + num + "张图片");
                    return;
                } else if (img1.contains("") && img1.size() == num + 1) {
                    ToastUtil.toast("最多可以选择" + num + "张图片");
                    return;
                }
                PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img1.size());
                break;
            case 1:
                if (!img2.contains("") && img2.size() == num) {
                    ToastUtil.toast("最多可以选择" + num + "张图片");
                    return;
                } else if (img2.contains("") && img2.size() == num + 1) {
                    ToastUtil.toast("最多可以选择" + num + "张图片");
                    return;
                }
                PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img2.size());
                break;
            case 2:
                if (!img3.contains("") && img3.size() == num) {
                    ToastUtil.toast("最多可以选择" + num + "张图片");
                    return;
                } else if (img3.contains("") && img3.size() == num + 1) {
                    ToastUtil.toast("最多可以选择" + num + "张图片");
                    return;
                }
                PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img3.size());
                break;
        }


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
                    //一张一张上传
                    switch (type) {
                        case 0:
                            img1.remove("");
                            uploadImg(0);
                            break;
                        case 1:
                            img2.remove("");
                            uploadImg(0);
                            break;
                        case 2:
                            img3.remove("");
                            uploadImg(0);
                            break;
                    }

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

            switch (type) {
                case 0:
                    if (img1.size() < num) {
                        img1.add("");
                    }
                    adapter1.notifyDataSetChanged();
                    break;
                case 1:
                    if (img2.size() < num) {
                        img2.add("");
                    }
                    adapter2.notifyDataSetChanged();
                    break;
                case 2:
                    if (img3.size() < num) {
                        img3.add("");
                    }
                    adapter3.notifyDataSetChanged();
                    break;
            }
            return;
        }
        switch (type) {
            case 0:
                upload(0, new File(images.get(i).getPath()), new UploadResult() {
                    @Override
                    public void onSuccess(String imgPath) {
                        img1.add(images.get(i).getPath());
                        images1.add(imgPath);
                        uploadImg(in);
                    }
                });
                break;
            case 1:
                upload(1, new File(images.get(i).getPath()), new UploadResult() {
                    @Override
                    public void onSuccess(String imgPath) {
                        img2.add(images.get(i).getPath());
                        images2.add(imgPath);
                        uploadImg(in);
                    }
                });
                break;
            case 2:
                upload(2, new File(images.get(i).getPath()), new UploadResult() {
                    @Override
                    public void onSuccess(String imgPath) {
                        img3.add(images.get(i).getPath());
                        images3.add(imgPath);
                        uploadImg(in);
                    }
                });
                break;
        }

    }

    private static final String IMG_PATH1 = "IMG_PATH";
    private static final String IMG_PATH2 = "LOCATION_PHOTO_PATH";
    private static final String IMG_PATH3 = "SPECIAL_PHOTO_PATH";

    private void upload(int type, File file, UploadResult uploadResult) {
        Map<String, Object> hashMap = new HashMap<>();
        switch (type) {
            case 0:
                hashMap.put("imgType", IMG_PATH1);
                break;
            case 1:
                hashMap.put("imgType", IMG_PATH2);
                break;
            case 2:
                hashMap.put("imgType", IMG_PATH3);
                break;
        }
        hashMap.put("img", file);
        ApiClient.requestNetPostFile(this, AppConfig.upload, "上传中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                String fileName = FastJsonUtil.getString(json, "fileName");
                if (!StringUtil.isEmpty(fileName)) {
                    uploadResult.onSuccess(fileName);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private interface UploadResult {
        void onSuccess(String imgPath);
    }

}
