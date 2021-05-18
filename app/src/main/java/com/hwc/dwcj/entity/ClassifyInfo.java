package com.hwc.dwcj.entity;

import java.util.List;

/**
 * @author Administrator
 *         日期 2018/8/17
 *         描述
 */

public class ClassifyInfo {


    /**
     * child : [{"id":"1175","name":"测试子分类","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png"},{"id":"1177","name":"测试分类22","thumb":"http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/dM5XZU7O215jJYMza2xn22450Vm2M7.png"}]
     * id : 0
     * name : 推荐分类
     */

    private int id;
    private String name;
    private List<ChildBean> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * id : 1175
         * name : 测试子分类
         * thumb : http://pd6b5xz3d.bkt.clouddn.com/images/1/2018/08/HBBS52525SOM55aA2dSE572S25Gm5h.png
         */

        private String id;
        private String name;
        private String thumb;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
