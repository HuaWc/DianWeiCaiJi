package com.hwc.dwcj.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
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
import com.hwc.dwcj.http.GetCameraImgHttp;
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
import org.json.JSONObject;

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

/**
 * ?????????????????????????????????
 * ?????????????????????
 * ??????????????????
 * ?????????????????????????????????
 * <p>
 * <p>
 * <p>
 * Located at "????????????????????????????????????" on 2021.6.3
 */
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
    @BindView(R.id.rl_locate)
    RelativeLayout rlLocate;


    private CommonImageAdapter adapter1;
    private CommonImageAdapter adapter2;
    private CommonImageAdapter adapter3;

    private List<String> img1;//????????????
    private List<String> img2;//????????????
    private List<String> img3;//????????????

    private List<String> images1;//????????????
    private List<String> images2;//????????????
    private List<String> images3;//????????????

    private List<LocalMedia> images;


    private int num = 3;
    private int type = 0;

    private int from;// 0 ????????????  1 ????????????

    private String positionName;
    private String positionCode;

    private CameraDictData dictData;
    private List<PcsDictItem> pcsDictItems;

    private List<String> ssfj;//????????????
    private List<String> sspcs;//???????????????
    private List<String> lwfs;//????????????
    private List<String> qdfs;//????????????
    private List<String> sbzt;//????????????
    private List<String> sbcs;//????????????
    private List<String> sxjlx;//???????????????
    private List<String> sxjgnlx;//?????????????????????
    private List<String> sxjwzlx;//?????????????????????
    private List<String> azwz;//????????????
    private List<String> sfzddw;//??????????????????
    /*    private List<String> sq;//??????
        private List<String> jd;//??????*/
    private List<String> ssbmhy;//??????????????????
    private List<String> xzqy;//????????????
    private List<String> hb;//??????
    private List<String> dwjklx;//??????????????????
    private List<String> psfx;//????????????
    private List<String> bgsx;//????????????
    private List<String> sfysyq;//??????????????????
    private List<String> lxbcts;//??????????????????
    private List<String> jsqs;//????????????
    private List<String> sxjbmgs;//?????????????????????
    private List<String> sxjfbl;//??????????????????

    private String ssfjStr;//????????????
    private String sspcsStr;//???????????????
    private String lwfsStr;//????????????
    private String qdfsStr;//????????????
    private String sbztStr;//????????????
    private String sbcsStr;//????????????
    private String sxjlxStr;//???????????????
    private String sxjgnlxStr;//?????????????????????
    private String sxjwzlxStr;//?????????????????????
    private String azwzStr;//????????????
    private String sfzddwStr;//??????????????????
    /*    private String sqStr;//??????
        private String jdStr;//??????*/
    private String ssbmhyStr;//??????????????????
    private String xzqyStr;//????????????
    private String hbStr;//??????
    private String dwjklxStr;//??????????????????
    private String psfxStr;//????????????
    private String bgsxStr;//????????????
    private String sfysyqStr;//??????????????????
    private String lxbctsStr;//??????????????????
    private String jsqsStr;//????????????
    private String sxjbmgsStr;//?????????????????????
    private String sxjfblStr;//??????????????????
    private Date azsj;//????????????

    private String checkUserId;
    private String notifyUserId;
    private int currentStatus;
    private boolean isEdit;//???????????????
    private boolean isDraft;//???????????????????????????
    private String cameraId;
    private PtCameraInfo entityInfo;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Long orgId;
    private String longitude;
    private String latitude;
    private LatLng l;


    /**
     * ??????->?????????????????????
     * ??????->?????????????????????
     * ???????????????->?????????????????????
     */


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_camera_detail);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        //etLongitude.setFocusable(false);
        //etLatitude.setFocusable(false);
        etDwmc.setFocusable(false);
        if (!isEdit) {
            //??????
            if (from == 1) {
/*                etDwbm.setText(positionCode);
                etDwmc.setText(positionName);
                etDwbm.setFocusable(false);
                etDwmc.setFocusable(false);
                if (!StringUtil.isEmpty(longitude)) {
                    etLongitude.setText(longitude);
                } *//*else {
                    etLatitude.setFocusable(true);
                }*//*
                if (!StringUtil.isEmpty(latitude)) {
                    etLatitude.setText(latitude);
                } *//*else {
                    etLatitude.setFocusable(true);
                }*/
                etDwbm.setFocusable(false);
                etDwmc.setFocusable(false);
                etDwbm.setText(positionCode);
                checkPositionCode();
            }
            getDictData();
            initAdapter();
        } else {
            //??????
            if (isDraft) {
                //?????????????????????????????????
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSave.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                //??????????????????
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSave.setVisibility(View.GONE);
                    }
                });
            }
            initAdapter2();
            tvTitle.setText("???  ???");
            etDwbm.setFocusable(false);
            etDwmc.setText(positionName);
        }

    }

    private void getDictData() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.dictCamera, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                dictData = FastJsonUtil.getObject(json, CameraDictData.class);
                //??????????????????
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
     * ?????????????????????????????????
     */
    private void getDictData2() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.dictCamera, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                dictData = FastJsonUtil.getObject(json, CameraDictData.class);
                //??????????????????
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
     * ?????????????????????????????????
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
        ApiClient.requestNetGet(this, AppConfig.pcsListDict, "?????????", hashMap, new ResultListener() {
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
        rlLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (!StringUtil.isEmpty(etLatitude) && !StringUtil.isEmpty(etLongitude)) {
                    bundle.putDouble("latitude", Double.parseDouble(etLatitude.getText().toString()));
                    bundle.putDouble("longitude", Double.parseDouble(etLongitude.getText().toString()));
                }
                toTheActivity(SelectMapPointActivity.class, bundle);
            }
        });
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
                //?????????
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
                    //????????????
                    if (!StringUtil.isEmpty(etDwbm.getText().toString().trim())) {
                        checkPositionCode();
                    }
                }
            }
        });

    }


    private void checkPositionCode() {
        if (!StringUtil.isEmpty(positionCode) && from == 0) {
            if (positionCode.equals(etDwbm.getText().toString().trim())) {
                return;
            }
        }
        positionCode = "";
        etLongitude.setText("");
        etLatitude.setText("");
        etDwmc.setText("");
        xzqyStr = "";
        tvSelectXzqy.setText("");
        ssfjStr = "";
        if (pcsDictItems != null) {
            pcsDictItems.clear();
        }
        tvSelectSsfj.setText("");
        tvSelectSspcs.setText("");
        sspcsStr = "";
        orgId = null;
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("positionCode", etDwbm.getText().toString().trim());
        ApiClient.requestNetPost(this, AppConfig.getPosition, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtil.isEmpty(json)) {
                    positionCode = etDwbm.getText().toString();
                    ToastUtil.toast("???????????????????????????????????????????????????????????????");
                    etDwbm.setText("");
                    etDwbm.requestFocus();
                    return;
                }
                CheckPositionCodeEntity checkPositionCodeEntity = FastJsonUtil.getObject(FastJsonUtil.getString(json, "model"), CheckPositionCodeEntity.class);
                if (checkPositionCodeEntity == null) {
                    positionCode = etDwbm.getText().toString();
                    ToastUtil.toast("???????????????????????????????????????????????????????????????");
                    etDwbm.setText("");
                    etDwbm.requestFocus();
                } else {
                    ToastUtil.toast("????????????????????????????????????????????????");
                    positionCode = etDwbm.getText().toString();
                    etLongitude.setText(StringUtil.isEmpty(checkPositionCodeEntity.getLongitude()) ? "" : checkPositionCodeEntity.getLongitude());
                    etLatitude.setText(StringUtil.isEmpty(checkPositionCodeEntity.getLatitude()) ? "" : checkPositionCodeEntity.getLatitude());
                    etDwmc.setText(StringUtil.isEmpty(checkPositionCodeEntity.getPositionName()) ? "" : checkPositionCodeEntity.getPositionName());


                    xzqyStr = FastJsonUtil.getString(json, "areaCode");
                    tvSelectXzqy.setText(FastJsonUtil.getString(json, "areaName") == null ? "" : FastJsonUtil.getString(json, "areaName"));
                    ssfjStr = FastJsonUtil.getString(json, "fjId");
                    getPcsData(ssfjStr);
                    tvSelectSsfj.setText(FastJsonUtil.getString(json, "fj") == null ? "" : FastJsonUtil.getString(json, "fj"));
                    sspcsStr = FastJsonUtil.getString(json, "orgName");
                    tvSelectSspcs.setText(FastJsonUtil.getString(json, "orgName") == null ? "" : FastJsonUtil.getString(json, "orgName"));
                    orgId = FastJsonUtil.getString(json, "pcsId") == null ? null : Long.parseLong(FastJsonUtil.getString(json, "pcsId"));
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

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
        //?????????1??????????????????????????? (2)????????????0????????????0??????1??? 11?????????12??????????????????0?????????????????????1?????????
        //(3)???????????????1???????????????2?????????30??????????????????????????????2??????????????????????????????????????????????????????
        hideSoftKeyboard();
        // hideSoftKeyboard2();
        hideSoftKeyboard3();
        Calendar nowDate = Calendar.getInstance();
        //???????????????
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//?????? String
                azsj = date;
                tvSelectAzsj.setText(choiceTime);


            }
        }).setDate(nowDate)//?????????????????????????????????
                .setType(new boolean[]{true, true, true, true, true, true})//???????????????????????????????????? true:?????? false:??????
                //.setLabel("???", "???", "???", "???", "???", "???")
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .setDividerColor(0x1F191F25)//?????????????????????
                .isCyclic(false)//???????????????????????? ???????????????31???????????????1??? ????????????????????????????????????????????????
                .setSubmitColor(0xFFF79D1F)//????????????????????????
                .setCancelColor(0xFFA3A5A8)//????????????????????????
                .setTitleText("????????????")//????????????
                .setTitleColor(0xFF191F25)//??????????????????
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }


    @OnClick({R.id.tv_select_sxjbmgs, R.id.tv_select_sxjfbl, R.id.tv_select_psfx, R.id.tv_select_ssfj, R.id.tv_select_sspcs, R.id.tv_select_lwfs, R.id.tv_select_qdfs, R.id.tv_select_sbzt, R.id.tv_select_sbcs, R.id.tv_select_sxjlx, R.id.tv_select_sxjgnlx, R.id.tv_select_sxjwzlx, R.id.tv_select_azwz, R.id.tv_select_sfzddw, R.id.tv_select_ssbmhy, R.id.tv_select_xzqy, R.id.tv_select_hb, R.id.tv_select_dwjklx, R.id.tv_select_bgsx, R.id.tv_select_sfysyq, R.id.tv_select_azsj, R.id.tv_select_lxbcts, R.id.tv_select_jsqs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_sxjbmgs:
                showSelectDialog(sxjbmgs, "?????????????????????", tvSelectSxjbmgs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjbmgsStr = dictData.getPT_CAMERA_ENCODE_TYPE().get(options1).getDataValue();

                    }
                });
                break;
            case R.id.tv_select_sxjfbl:
                showSelectDialog(sxjfbl, "??????????????????", tvSelectSxjfbl, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjfblStr = dictData.getPT_RESOLUTION().get(options1).getDataValue();

                    }
                });
                break;
            case R.id.tv_select_psfx:
                showSelectDialog(psfx, "????????????", tvSelectPsfx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        psfxStr = dictData.getPT_CAMERA_DIRECTION().get(options1).getDataValue();

                    }
                });
                break;
            case R.id.tv_select_ssfj:
                showSelectDialog(ssfj, "????????????", tvSelectSsfj, new AddSelectResult() {
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
                    ToastUtil.toast("????????????????????????");
                    return;
                }
                if (sspcs == null || sspcs.size() == 0) {
                    ToastUtil.toast("???????????????????????????????????????????????????");
                    return;
                }
                showSelectDialog(sspcs, "???????????????", tvSelectSspcs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sspcsStr = pcsDictItems.get(options1).getOrgName() + "";
                        orgId = Long.parseLong(pcsDictItems.get(options1).getId() + "");
                    }
                });
                break;
            case R.id.tv_select_lwfs:
                showSelectDialog(lwfs, "????????????", tvSelectLwfs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        lwfsStr = dictData.getPT_NETWORK_PROPERTIES().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_qdfs:
                showSelectDialog(qdfs, "????????????", tvSelectQdfs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        qdfsStr = dictData.getPT_POWER_TAKE_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sbzt:
                showSelectDialog(sbzt, "????????????", tvSelectSbzt, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sbztStr = dictData.getPT_DEVICE_STATE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sbcs:
                showSelectDialog(sbcs, "????????????", tvSelectSbcs, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sbcsStr = dictData.getPT_MANUFACTURER().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sxjlx:
                showSelectDialog(sxjlx, "???????????????", tvSelectSxjlx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjlxStr = dictData.getPT_CAMERA_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sxjgnlx:
                showSelectDialog(sxjgnlx, "?????????????????????", tvSelectSxjgnlx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjgnlxStr = dictData.getPT_CAMERA_FUN_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sxjwzlx:
                showSelectDialog(sxjwzlx, "?????????????????????", tvSelectSxjwzlx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        sxjwzlxStr = dictData.getPT_CAMERA_POSITION_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_azwz:
                showSelectDialog(azwz, "????????????", tvSelectAzwz, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        azwzStr = dictData.getPT_INDOOR_OR_NOT().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sfzddw:

                break;
            case R.id.tv_select_ssbmhy:
                showSelectDialog(ssbmhy, "????????????/??????", tvSelectSsbmhy, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        ssbmhyStr = dictData.getPT_DEPARTMENT().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_xzqy:
                showSelectDialog(xzqy, "????????????", tvSelectXzqy, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        xzqyStr = dictData.getPT_ADMINISTRATIVE_EREA().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_hb:
                showSelectDialog(hb, "??????", tvSelectHb, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        hbStr = dictData.getPT_CROSS_ARM1().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_dwjklx:
                showSelectDialog(dwjklx, "??????????????????", tvSelectDwjklx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        dwjklxStr = dictData.getPT_MONITOR_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_bgsx:
                showSelectDialog(bgsx, "????????????", tvSelectBgsx, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        bgsxStr = dictData.getPT_FILL_LIGTH_ATTR().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_sfysyq:
                showSelectDialog(sfysyq, "??????????????????", tvSelectSfysyq, new AddSelectResult() {
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
                showSelectDialog(lxbcts, "??????????????????", tvSelectLxbcts, new AddSelectResult() {
                    @Override
                    public void change(int options1, int options2, int options3) {
                        lxbctsStr = dictData.getPT_RECODE_SAVE_TYPE().get(options1).getDataValue();
                    }
                });
                break;
            case R.id.tv_select_jsqs:
                showSelectDialog(jsqs, "????????????", tvSelectJsqs, new AddSelectResult() {
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
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etDwmc.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etSbmc.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        /*if (StringUtil.isEmpty(etSbbm.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }*/
        if (StringUtil.isEmpty(etIpv4.getText().toString().trim())) {
            ToastUtil.toast("?????????IPV4??????");
            return;
        }
        if (!RegexUtils.isIP(etIpv4.getText().toString().trim())) {
            ToastUtil.toast("????????????????????????IPV4??????");
            return;
        }
        if (StringUtil.isEmpty(tvSelectXzqy.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(tvSelectSsfj.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(tvSelectSspcs.getText().toString().trim())) {
            ToastUtil.toast("????????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etAzdz.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etLongitude.getText().toString().trim())) {
            ToastUtil.toast("???????????????");
            return;
        }
        if (StringUtil.isEmpty(etLatitude.getText().toString().trim())) {
            ToastUtil.toast("???????????????");
            return;
        }
        if (StringUtil.isEmpty(tvSelectLwfs.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etLwrlxfs.getText().toString().trim())) {
            ToastUtil.toast("??????????????????????????????");
            return;
        }
        if (StringUtil.isEmpty(tvSelectQdfs.getText().toString().trim())) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (StringUtil.isEmpty(etQdrlxfs.getText().toString().trim())) {
            ToastUtil.toast("??????????????????????????????");
            return;
        }
        if (images1.size() == 0) {
            ToastUtil.toast("?????????????????????");
            return;
        }
        if (images2.size() == 0) {
            ToastUtil.toast("??????????????????");
            return;
        }
        if (images3.size() == 0) {
            ToastUtil.toast("????????????????????????");
            return;
        }
        this.currentStatus = currentStatus;
        /*if (isEdit && etSbbm.getText().toString().trim().equals(entityInfo.getCameraNo())) {
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
        }*/
        // checkIpv4Address();
        if (isEdit && etIpv4.getText().toString().trim().equals(entityInfo.getCameraIp())) {
            toSelectCheckUser();
        } else {
            checkIpv4Address();
        }

    }

    /*private void checkCameraNo() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraNo", etSbbm.getText().toString().trim());
        ApiClient.requestNetPost(this, AppConfig.toHeavyNumber, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                boolean a = FastJsonUtil.getObject(json, boolean.class);
                if (a) {
                    //ToastUtil.toast("?????????????????????????????????");
                    if (isEdit && etIpv4.getText().toString().trim().equals(entityInfo.getCameraIp())) {
                        toSelectCheckUser();
                    } else {
                        checkIpv4Address();
                    }
                } else {
                    ToastUtil.toast("????????????????????????????????????????????????");
                    etSbbm.requestFocus();
                    etSbbm.setText("");

                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast("???????????????????????????" + msg);
            }
        });
    }*/

    private void checkIpv4Address() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraIp", etIpv4.getText().toString().trim());
        ApiClient.requestNetPost(this, AppConfig.whetherToRepeat, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                boolean a = FastJsonUtil.getObject(json, boolean.class);
                if (a) {
                    //ToastUtil.toast("????????????IPV4???????????????");
                    toSelectCheckUser();
                } else {
                    ToastUtil.toast("????????????IPV4??????????????????????????????");
                    etIpv4.requestFocus();
                    etIpv4.setText("");

                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast("IPV4?????????????????????" + msg);
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
        ApiClient.requestNetPost(this, AppConfig.getCamera, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                entityInfo = FastJsonUtil.getObject(FastJsonUtil.getString(FastJsonUtil.getString(json, "model"), "ptCameraInfo"), PtCameraInfo.class);
                initData();
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
                String s1 = FastJsonUtil.getString(json, "imgPath");
                String s2 = FastJsonUtil.getString(json, "locationPhotoPath");
                String s3 = FastJsonUtil.getString(json, "specialPhotoPath");
                if (!StringUtil.isEmpty(s1)) {
                    img1.addAll(Arrays.asList(s1.split("!")));
                    if (img1.size() < num) {
                        img1.add("");
                    }
                    adapter1.notifyDataSetChanged();
                } else {
                    img1.add("");
                }
                adapter1.notifyDataSetChanged();
                if (!StringUtil.isEmpty(s2)) {
                    img2.addAll(Arrays.asList(s2.split("!")));
                    if (img2.size() < num) {
                        img2.add("");
                    }
                    adapter2.notifyDataSetChanged();
                } else {
                    img2.add("");
                }
                adapter2.notifyDataSetChanged();
                if (!StringUtil.isEmpty(s3)) {
                    img3.addAll(Arrays.asList(s3.split("!")));
                    if (img3.size() < num) {
                        img3.add("");
                    }
                } else {
                    img3.add("");
                }
                adapter3.notifyDataSetChanged();

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

    private void deleteOld() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("cameraId", cameraId);
        ApiClient.requestNetPost(this, AppConfig.dropRecord, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //ToastUtil.toast(msg);
                //submit();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    private void submit() {
        /**
         * ?????????????????????????????????40???
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


        //????????????
        ptCameraInfo.setCameraIp(etIpv4.getText().toString());
        ptCameraInfo.setCameraState(sbztStr);//????????????
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
        //ptCameraInfo.setCameraIp4(sbztStr);//??????????????????
        ptCameraInfo.setImportWatch(etZdjkdw.getText().toString());
        ptCameraInfo.setCommunity(etSq.getText().toString());//??????
        ptCameraInfo.setStreet(etJd.getText().toString());//??????
        ptCameraInfo.setIndustryOwn(ssbmhyStr);
        ptCameraInfo.setAreaCode(xzqyStr);
        ptCameraInfo.setInstallHeight(etJsgd.getText().toString());
        ptCameraInfo.setCrossArm1(hbStr);//??????
        ptCameraInfo.setMonitorType(dwjklxStr);
        ptCameraInfo.setCameraDirection(psfxStr);//????????????
        if (!StringUtil.isEmpty(bgsxStr)) {
            ptCameraInfo.setFillLigthAttr(Long.parseLong(bgsxStr));
        }
        if (!StringUtil.isEmpty(sxjbmgsStr)) {
            ptCameraInfo.setCameraEncodeType(Long.parseLong(sxjbmgsStr));
        }
        if (!StringUtil.isEmpty(sfysyqStr)) {
            ptCameraInfo.setSoundAlarm(Long.parseLong(sfysyqStr));
        }
        ptCameraInfo.setResolution(sxjfblStr);//??????????????????
        ptCameraInfo.setInstallTime(azsj);//????????????
        ptCameraInfo.setPoliceAreaCode(etSsxqgajg.getText().toString());//????????????????????????
        if (!StringUtil.isEmpty(lxbctsStr)) {
            ptCameraInfo.setRecodeSaveType(Long.parseLong(lxbctsStr));
        }
        ptCameraInfo.setAccessModel(etJrfs.getText().toString());//????????????
        if (!StringUtil.isEmpty(jsqsStr)) {
            ptCameraInfo.setBuildPeriod(Long.parseLong(jsqsStr));
        }
        ptCameraInfo.setOsdName(etOsd.getText().toString());//osd
        ptCameraInfo.setManagerUnit(etGldw.getText().toString());
        ptCameraInfo.setManagerUnitTel(etGldwlxfs.getText().toString());
        ptCameraInfo.setMaintainUnit(etWhdw.getText().toString());
        ptCameraInfo.setMaintainUnitTel(etWhdwlxfs.getText().toString());
/*        ptCameraInfo.setAddId(etSbr.getText().toString());//?????????
        ptCameraInfo.setAddTel(etSbrlxfs.getText().toString());//?????????????????????
        ptCameraInfo.setApprovalId(etSpr.getText().toString());//?????????
        ptCameraInfo.setApprovalTel(etSprlxfs.getText().toString());//?????????????????????*/

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
        hashMap.put("handleConnent", "???????????????");
        hashMap.put("handleIp", RegularCheckUtil.getIPAddress(this));
        hashMap.put("entity", FastJsonUtil.toJSONString(ptCameraInfo));
        hashMap.put("checkUserId", checkUserId);
        hashMap.put("notifyUserId", notifyUserId);
        //hashMap.put("currentStatus", currentStatus);

        ApiClient.requestNetPostFile(this, url, "?????????", hashMap, new ResultListener() {
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
        getImgData();

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
                //??????????????? ?????????
                //??????????????? 1.???????????????????????????????????????  2.???????????????????????????????????????  3.???????????????????????????????????????
                //??????->?????????              ????????????
                //??????->?????????              ????????????
                //???????????????->?????????         ????????????
/*                if (isEdit) {
                    //??????
                    //deleteOld();

                } else {
                    //??????
                    //??????????????? ?????????
                    submit();

                }*/
                submit();
                break;
            case EventUtil.SELECT_MAP_POINT:
                l = (LatLng) center.getData();
                if (l != null) {
                    etLatitude.setText(String.valueOf(l.latitude));
                    etLongitude.setText(String.valueOf(l.longitude));
                }
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        positionName = extras.getString("positionName");
        positionCode = extras.getString("positionCode");
        from = extras.getInt("from");
        isEdit = extras.getBoolean("isEdit", false);
        //?????????????????????????????????????????????
        isDraft = extras.getBoolean("isDraft", false);

        cameraId = extras.getString("cameraId");
        longitude = extras.getString("longitude");
        latitude = extras.getString("latitude");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    /**
     * ????????????
     *
     * @param position
     */
    private void showDeleteDialog(int position, int type1) {
        CommonTipDialog.getInstance()
                .addTipData(this, "??????", "??????", "??????")
                .addBtnColor("#FF191F25", "#FF4F77E1")
                .addTipContent("?????????????????????")
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
/*
                        switch (type1) {
                            case 0:
                                img1.remove(position);
                                images1.remove(position);
                                if (img1.size() < num && !img1.contains("")) {
                                    img1.add("");
                                }
                                adapter1.notifyDataSetChanged();
                                break;
                            case 1:
                                img2.remove(position);
                                images2.remove(position);
                                if (img2.size() < num && !img2.contains("")) {
                                    img2.add("");
                                }
                                adapter2.notifyDataSetChanged();
                                break;
                            case 2:
                                img3.remove(position);
                                images3.remove(position);
                                if (img3.size() < num && !img3.contains("")) {
                                    img3.add("");
                                }
                                adapter3.notifyDataSetChanged();
                                break;
                        }*/


                    }
                })
                .show();
    }

    private void deleteFile(String fileName, DeleteResult deleteResult) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileName", fileName);
        if (!StringUtil.isEmpty(cameraId)) {
            hashMap.put("cameraId", cameraId);
        }
        ApiClient.requestNetPost(this, AppConfig.deleteFile, "?????????", hashMap, new ResultListener() {
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
                    ToastUtil.toast("??????????????????" + num + "?????????");
                    return;
                } else if (img1.contains("") && img1.size() == num + 1) {
                    ToastUtil.toast("??????????????????" + num + "?????????");
                    return;
                }
                PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img1.size());
                break;
            case 1:
                if (!img2.contains("") && img2.size() == num) {
                    ToastUtil.toast("??????????????????" + num + "?????????");
                    return;
                } else if (img2.contains("") && img2.size() == num + 1) {
                    ToastUtil.toast("??????????????????" + num + "?????????");
                    return;
                }
                PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img2.size());
                break;
            case 2:
                if (!img3.contains("") && img3.size() == num) {
                    ToastUtil.toast("??????????????????" + num + "?????????");
                    return;
                } else if (img3.contains("") && img3.size() == num + 1) {
                    ToastUtil.toast("??????????????????" + num + "?????????");
                    return;
                }
                PictureSelectDialogUtils.showSelectPictureSelector(this, num + 1 - img3.size());
                break;
        }


    }


    /**
     * ??????????????????
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// ????????????????????????
                images = PictureSelector.obtainMultipleResult(data);
                if (images != null && images.size() != 0) {
                    //??????????????????
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
     * ????????????
     */
    private void uploadImg(int i) {
        int in = i + 1;
        if (i == images.size()) {
            //????????????

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
                hashMap.put("fileType", IMG_PATH1);
                break;
            case 1:
                hashMap.put("fileType", IMG_PATH2);
                break;
            case 2:
                hashMap.put("fileType", IMG_PATH3);
                break;
        }
        hashMap.put("file", file);
        ApiClient.requestNetPostFile(this, AppConfig.upload, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                String fileName = FastJsonUtil.getString(json, "filePath");
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
