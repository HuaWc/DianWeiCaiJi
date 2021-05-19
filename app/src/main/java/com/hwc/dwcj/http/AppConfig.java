package com.hwc.dwcj.http;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.hwc.dwcj.BuildConfig;
import com.hwc.dwcj.entity.VersionInfo;
import com.hwc.dwcj.updata.CretinAutoUpdateUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.upDated.interfaces.ForceExitCallBack;
import com.zds.base.upDated.model.UpdateEntity;
import com.zds.base.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 *
 * @author Administrator
 */
public class AppConfig {
    /**
     * 服务器
     */
    public static final String baseService = BuildConfig.BASEURL;
    /**
     * 主地址
     */
    public static final String mainUrl = baseService;


    public static final String qiyImage = "?imageView2/1/w/250/h/250/q/75";


    /**
     * 登录
     */
    public static final String login = mainUrl + "v1/operation/login";

    /**
     * 档案列表
     */
    public static final String filePage = mainUrl + "v1/operation/PtPosition/filePage";

    /**
     * 档案列表获得省市区区划
     */
    public static final String selectArea = mainUrl + "v1/operation/PtDictClass/selectArea";

    /**
     * 省市区划下的派出所列表
     */
    public static final String pcsList = mainUrl + "v1/operation/PtOrgInfo/list";

    /**
     * 审批首页获取数据接口
     */
    public static final String collectorApproval = mainUrl + "v1/operation/OpCameraProcess/collectorApproval";

    /**
     * 获得当前角色状态码
     */
    public static final String getUserRole = mainUrl + "v1/operation/PtUserRole/select";

    /**
     * 档案页面点位相机详情
     */
    public static final String filePositionDetails = mainUrl + "v1/operation/filePositionDetails";

    /**
     * 档案-某一相机详情数据获取接口,所有图片属性返回图片的绝对路径
     */
    public static final String selectCamera = mainUrl + "v1/operation/selectCamera";

    /**
     * 点位采集首页获取数据接口
     */
    public static final String pointToOllect = mainUrl + "v1/operation/OpCameraProcess/pointToOllect";

    /**
     * 地图首页树状菜单信息(一级和二级数据)
     */
    public static final String tree = mainUrl + "v1/operation/tree";

    /**
     * 地图首页相机树信息(三级数据)
     */
    public static final String treeThree = mainUrl + "v1/operation/treeThree";

    /**
     * 地图页关键字查询接口
     */
    public static final String mapKeywords = mainUrl + "v1/operation/mapKeywords";


    /**
     * 新增相机界面所需的字典信息
     */
    public static final String dictCamera = mainUrl + "v1/operation/PtDictClass/CameraList";

    /**
     * 根据分局信息主键id获取所属派出所信息
     */
    public static final String pcsListDict = mainUrl + "v1/operation/PtOrgInfo/pcsList";

    /**
     * 新增摄像机接口,currentStatus =0表示新增一条草稿记录
     */
    public static final String addCamera = mainUrl + "v1/operation/addCamera";

    /**
     * 用户退出
     */
    public static final String logout = mainUrl + "v1/operation/logout";

    /**
     * 某一相机审批流程查看接口
     */
    public static final String selectProcessApproval = mainUrl + "v1/operation/OpCameraProcess/selectProcessApproval";

    /**
     * 根据主键ID查询某一相机实体的全部信息(且将此条审批流程修改为已阅状态)
     */
    public static final String cameradetails = mainUrl + "v1/operation/cameradetails";

    /**
     * 流程催办接口,参数:相机表主键Id
     */
    public static final String expedite = mainUrl + "v1/operation/expedite";

    /**
     * 根据相机实体主键ID撤销某一相机的审批流程
     */

    public static final String dropCamera = mainUrl + "v1/operation/dropCamera";

    /**
     * 获取用于审批权限人员信息
     */
    public static final String getCheckUsers = mainUrl + "v1/operation/PtUserInfo/getCheckUsers";

    /**
     * 获取监理权限人员信息(用于选择抄送人)
     */
    public static final String getSupervisor = mainUrl + "v1/operation/PtUserInfo/getSupervisor";

    /**
     * 对某条记录进行审批操作
     */
    public static final String checkCamera = mainUrl + "v1/operation/OpCameraProcess/checkCamera";

    /**
     * 某一相机详情数据获取接口,所有图片属性返回BASE64编码
     */
    public static final String getCamera = mainUrl + "v1/operation/getCamera";

    /**
     * 单图上传ftp .返回ftp中存储路径
     */
    public static final String upload = mainUrl + "v1/operation/upload";

    /**
     * 单图删除ftp .参数:图片存储的路径,返回boolean值
     */
    public static final String deleteFile = mainUrl + "v1/operation/deleteFile";

    /**
     * 根据相机Id修改相机信息 CURRENT_STATUS属性 =1
     */
    public static final String updateCamera = mainUrl + "v1/operation/updateCamera";










































    /**
     * 图片地址
     */
    public static final String ImageMainUrl = "";
    public static final String goodsList = "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=goods&cate=";
    public static final String shoreUrl = "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=goods.merchindex&merchid";
    public static final String goodDetail = "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=groups.goods";

    /**
     * 注册
     */
    public static final String registerUrl = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=account.register";
    /**
     * 忘记密码
     */
    public static final String forgetPasswordUrl = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=account.forget&httpsource=fromapp";

    /**
     * 获取验证码
     */
    public static final String getPhoneCodeUrl = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=account.verifycode&httpsource=fromapp";


    /**
     * 商品种类搜索
     */
    public static final String searchTypeId = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=goods&cate=";


    /**
     * 注册协议
     */
    public static final String zcxy = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=qa.detail&id=13";


    /**
     * 余额
     */
    public static final String yue = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=member";


    /**
     * 版本更新
     */
    public static String checkVersion = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=goods.api.versioninfo&httpsource=fromapp";


    /**
     * 检查版本
     */
    public static void checkVersion(final Context context, boolean isInge) {
        if (isInge) {
            CretinAutoUpdateUtils.getInstance(context).check(new ForceExitCallBack() {
                @Override
                public void exit() {
                    ((Activity) context).finish();
                }

                @Override
                public void isHaveVersion(boolean isHave) {

                }

                @Override
                public void cancel() {

                }
            });
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("versions", SystemUtil.getAppVersionNumber());
            map.put("platform", 1);
            ApiClient.requestNetGet(context, checkVersion, "正在检测...", map, new ResultListener() {
                @Override
                public void onSuccess(String json, String msg) {
                    String mes = FastJsonUtil.getString(json, "newversion");
                    final VersionInfo versionInfo = FastJsonUtil.getObject(mes, VersionInfo.class);
                    if (versionInfo != null) {
                        if (versionInfo.getVersionCodes() > SystemUtil.getAppVersionNumber()) {
                            new AlertDialog.Builder(context).setTitle("新版本" + versionInfo.getVersionNames()).setMessage(versionInfo.getUpdateLogs()).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface anInterface, int i) {
                                    UpdateEntity updateEntity = new UpdateEntity();
                                    updateEntity.setVersionCode(versionInfo.getVersionCodes());
                                    updateEntity.setIsForceUpdate(versionInfo.getIsForceUpdates());
                                    updateEntity.setPreBaselineCode(versionInfo.getPreBaselineCodes());
                                    updateEntity.setVersionName(versionInfo.getVersionNames());
                                    updateEntity.setDownurl(versionInfo.getDownurls());
                                    updateEntity.setUpdateLog(versionInfo.getUpdateLogs());
                                    updateEntity.setSize(versionInfo.getApkSizes());
                                    updateEntity.setHasAffectCodes(versionInfo.getHasAffectCodess());
                                    UpdateEntity var8 = updateEntity;
                                    CretinAutoUpdateUtils.getInstance(context).startUpdate(var8);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface anInterface, int i) {
                                    anInterface.dismiss();
                                }
                            }).show();

                        } else {
                            ToastUtil.toast("当前已是最新版本");
                        }

                    } else {
                        ToastUtil.toast("请求数据失败");
                    }
                }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.toast(msg);
                }
            });
        }
    }

}
