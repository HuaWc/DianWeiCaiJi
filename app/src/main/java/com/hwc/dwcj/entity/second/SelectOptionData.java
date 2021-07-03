package com.hwc.dwcj.entity.second;

import com.hwc.dwcj.entity.DictInfo;

import java.util.List;

/**
 * Created by Christ on 2021/7/2.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class SelectOptionData {
    private String name;
    private List<DictInfo> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DictInfo> getOptions() {
        return options;
    }

    public void setOptions(List<DictInfo> options) {
        this.options = options;
    }
}
