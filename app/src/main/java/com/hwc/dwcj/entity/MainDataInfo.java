package com.hwc.dwcj.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hwc.dwcj.http.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 日期 2018/8/16
 * 描述
 */

public class MainDataInfo {

    /**
     * advs : [{"advname":"轮播","id":"9","link":"","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png"},{"advname":"轮播","id":"8","link":"","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png"},{"advname":"轮播","id":"7","link":"","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png"},{"advname":"轮播","id":"6","link":"","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png"}]
     * goods : [{"groupid":"10","groupsprice":"8.00","id":"205","ispresell":"0","marketprice":"90.00","minprice":"10.00","presellprice":"0.00","productprice":"90.00","singleprice":"90.00","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png","title":"总平台拼团","total":"121","type":"1"},{"groupid":"8","groupsprice":"9.00","id":"203","ispresell":"0","marketprice":"10.00","minprice":"10.00","presellprice":"0.00","productprice":"9.00","singleprice":"10.00","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/merch/97/kVtHWW9wAvN4HWiaFb9eZ93BweF48e.png","title":"测试","total":"20","type":"1"}]
     * navs : [{"icon":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/PS0wQMPSCwcXWJpcmsGS5Szq4s29CP.png","id":"14","navname":"酒水饮料","url":""}]
     * url : http://192.168.1.177/./
     */

    private String url;
    private String home_img;
    private List<AdvsBean> advs;
    private List<GoodsBean> goods;
    private List<NavsBean> navs;

    public String getUrl() {
        return url;
    }

    public String getHome_img() {
        return home_img;
    }

    public void setHome_img(String home_img) {
        this.home_img = home_img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AdvsBean> getAdvs() {
        return advs;
    }

    public void setAdvs(List<AdvsBean> advs) {
        this.advs = advs;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public List<NavsBean> getNavs() {
        return navs;
    }

    public void setNavs(List<NavsBean> navs) {
        this.navs = navs;
    }

    public static class AdvsBean {
        /**
         * advname : 轮播
         * id : 9
         * link :
         * thumb : http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png
         */

        private String advname;
        private String id;
        private String link;
        private String thumb;

        public String getAdvname() {
            return advname;
        }

        public void setAdvname(String advname) {
            this.advname = advname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class GoodsBean implements MultiItemEntity {
        /**
         * groupid : 10
         * groupsprice : 8.00
         * id : 205
         * ispresell : 0
         * marketprice : 90.00
         * minprice : 10.00
         * presellprice : 0.00
         * productprice : 90.00
         * singleprice : 90.00
         * thumb : http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png
         * title : 总平台拼团
         * total : 121
         * type : 1
         */

        private String groupid;
        private String groupsprice;
        private String id;
        private String ispresell;
        private String marketprice;
        private String minprice;
        private String presellprice;
        private String productprice;
        private String singleprice;
        private String deduct_price;
        private int is_dispatch;
        private String thumb;
        private String title;
        private String total;
        private String type;
        private int groupnum;
        private int itemType=0;
        private List<GoodsBean> goodslist=new ArrayList<>();

        public List<GoodsBean> getGoodslist() {
            return goodslist;
        }

        public void setGoodslist(List<GoodsBean> goodslist) {
            this.goodslist = goodslist;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getDeduct_price() {
            return deduct_price;
        }

        public void setDeduct_price(String deduct_price) {
            this.deduct_price = deduct_price;
        }

        public int getIs_dispatch() {
            return is_dispatch;
        }

        public void setIs_dispatch(int is_dispatch) {
            this.is_dispatch = is_dispatch;
        }

        public int getGroupnum() {
            return groupnum;
        }

        public void setGroupnum(int groupnum) {
            this.groupnum = groupnum;
        }

        public String getGroupid() {
            return groupid;
        }

        public void setGroupid(String groupid) {
            this.groupid = groupid;
        }

        public String getGroupsprice() {
            return groupsprice;
        }

        public void setGroupsprice(String groupsprice) {
            this.groupsprice = groupsprice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIspresell() {
            return ispresell;
        }

        public void setIspresell(String ispresell) {
            this.ispresell = ispresell;
        }

        public String getMarketprice() {
            return marketprice;
        }

        public void setMarketprice(String marketprice) {
            this.marketprice = marketprice;
        }

        public String getMinprice() {
            return minprice;
        }

        public void setMinprice(String minprice) {
            this.minprice = minprice;
        }

        public String getPresellprice() {
            return presellprice;
        }

        public void setPresellprice(String presellprice) {
            this.presellprice = presellprice;
        }

        public String getProductprice() {
            return productprice;
        }

        public void setProductprice(String productprice) {
            this.productprice = productprice;
        }

        public String getSingleprice() {
            return singleprice;
        }

        public void setSingleprice(String singleprice) {
            this.singleprice = singleprice;
        }

        public String getThumb() {
            return thumb + AppConfig.qiyImage;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class NavsBean {
        /**
         * icon : http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/PS0wQMPSCwcXWJpcmsGS5Szq4s29CP.png
         * id : 14
         * navname : 酒水饮料
         * url :
         */

        private String icon;
        private String id;
        private String navname;
        private String url;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNavname() {
            return navname;
        }

        public void setNavname(String navname) {
            this.navname = navname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
