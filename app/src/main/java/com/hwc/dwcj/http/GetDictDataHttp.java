package com.hwc.dwcj.http;

import android.content.Context;

import com.hwc.dwcj.entity.DictInfo;
import com.zds.base.json.FastJsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Christ on 2021/7/3.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class GetDictDataHttp {

    private static List<DictInfo> mList;

    public static void  getDictData(Context mContext,String dataTypeCode,  GetDictDataHttp.GetDictDataResult getDictDataResult) {
        Map<String, Object> params = new HashMap<>();
        params.put("dataTypeCode", dataTypeCode);
        ApiClient.requestNetPost(mContext, AppConfig.getDataTypeList, "", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                mList = FastJsonUtil.getList(json, DictInfo.class);
                getDictDataResult.getData(mList);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    public interface GetDictDataResult {
        void getData(List<DictInfo> list);
    }
}
