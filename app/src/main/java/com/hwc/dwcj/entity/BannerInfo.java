package com.hwc.dwcj.entity;

/**
 * @author Administrator
 *         日期 2018/6/26
 *         描述 banner
 */

public class BannerInfo {


    /**
     * name : 车险预约
     * haveUrl : 1
     * urlContent :
     * haveDetail : 1
     * detailContent : <p>&nbsp;</p>
     * orderDesc : 5
     * type : 1
     */

    private String name;
    private String haveUrl;
    private String urlContent;
    private String haveDetail;
    private String detailContent;
    private String orderDesc;
    private int type;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHaveUrl() {
        return haveUrl;
    }

    public void setHaveUrl(String haveUrl) {
        this.haveUrl = haveUrl;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
    }

    public String getHaveDetail() {
        return haveDetail;
    }

    public void setHaveDetail(String haveDetail) {
        this.haveDetail = haveDetail;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
