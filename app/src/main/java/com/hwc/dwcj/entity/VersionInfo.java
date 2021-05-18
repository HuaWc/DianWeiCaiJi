package com.hwc.dwcj.entity;


import com.zds.base.upDated.model.LibraryUpdateEntity;

/**
 * Created by liuzhu
 * on 2017/7/6.
 */

public class VersionInfo implements LibraryUpdateEntity {


    /**
     * id : 3
     * platform : 1
     * versions : 1.0.1
     * createtime : 0
     * updatetime : 0
     * logo : http://p6yjoyf2t.bkt.clouddn.com/20180730420795069/9E08495A-4CF4-4d5b-AFCF-0737EF182AE5.png
     * remark : 修复bug
     * isconstraint : 0
     * versionid : 0
     * downloadsurl : null
     */

    private String id;
    private String platform;
    private String versions;
    private String createtime;
    private String updatetime;
    private String logo;
    private String remark;
    private String isconstraint;
    private String versionid;
    private String downloadsurl;

    /**
     * id : 1
     * verNumber : 2
     * verInformat : V1.2.1
     * updateContent : 更新内容：1、调整实人认证现场办理单位及相关提示 2、增加退休人员认证失败提示 3、优化统筹区区域排序功能 4、修复其他BUG
     * updateAddress :
     * updateState : 1
     * verTime : 2018-05-08 14:22:56.0
     */


    @Override
    public int getVersionCodes() {
        int version = 0;
        if (versionid != null) {
            try {
                version = Double.valueOf(versionid.trim()).intValue();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return version;
    }

    @Override
    public int getIsForceUpdates() {
        if (isconstraint != null && isconstraint.equals("0")) {
            return 0;
        } else if (isconstraint != null && isconstraint.equals("1")) {
            return 2;
        }
        return 0;
    }

    @Override
    public int getPreBaselineCodes() {
        return 0;
    }

    @Override
    public String getVersionNames() {
        return versions;
    }

    @Override
    public String getDownurls() {
        return downloadsurl;
    }

    @Override
    public String getUpdateLogs() {
        return remark == null ? ("新版本，欢迎更新") : (remark.equals("null") ? "新版本，欢迎更新" : remark);
    }

    @Override
    public String getApkSizes() {
        return "100";
    }

    @Override
    public String getHasAffectCodess() {
        return "";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsconstraint() {
        return isconstraint;
    }

    public void setIsconstraint(String isconstraint) {
        this.isconstraint = isconstraint;
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getDownloadsurl() {
        return downloadsurl;
    }

    public void setDownloadsurl(String downloadsurl) {
        this.downloadsurl = downloadsurl;
    }
}
