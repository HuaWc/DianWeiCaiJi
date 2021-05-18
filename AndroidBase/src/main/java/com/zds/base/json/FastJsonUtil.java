package com.zds.base.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zds.base.log.XLog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作   者：赵大帅
 * 描   述: json 解析
 * 日   期: 2017/9/6 14:41
 * 更新日期: 2017/9/6
 */

public class FastJsonUtil {

    /**
     * @param
     * @param type
     * @param <T>
     * @return
     */
    public static final <T> T getObject(String jsontext, Type type) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, type);
        } catch (Exception e) {
            XLog.error("json字符串转换失败" + jsontext, e);
        }
        return t;
    }

    /**
     * 解析
     *
     * @param jsontext
     * @param clazz
     * @param <T>
     * @return
     */
    // 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, clazz);
        } catch (Exception e) {
            XLog.error("json字符串转换失败" + jsontext, e);
        }
        return t;
    }

    /**
     * 解析实体类
     *
     * @param jsontext
     * @param obj_str
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getObject(String jsontext, String obj_str,
                                        Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonobj.getObject(obj_str, clazz);
        } else {
            if (obj instanceof JSONArray) {
                return jsonobj.getObject(obj_str, clazz);
            } else {
                XLog.e(jsontext);
            }
        }

        return null;
    }

    /**
     * 解析实体类
     *
     * @param jsontext
     * @param obj_str
     * @return
     */
    public static final String getString(String jsontext, String obj_str) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * @param jsontext
     * @param obj_str
     * @return
     */
    public static final int getInt(String jsontext, String obj_str) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return 0;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return 0;
        }
        try {
            return Integer.valueOf(obj.toString());
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 解析集合
     *
     * @param jsontext
     * @param list_str
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> List<T> getList(String jsontext, String list_str,
                                            Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(list_str);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JSONArray) {
            JSONArray jsonarr = (JSONArray) obj;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < jsonarr.size(); i++) {
                list.add(jsonarr.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }


    public static final String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    public static final String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * @param jsonStr json字符串
     * @param clazz   class名称
     * @return
     * @Description： json字符串转成为List
     * @author: GuXiYang
     * @date: 2017/05/08 10:25:00
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            XLog.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     * @param jsonString json字符串
     * @return
     * @Description： json字符串转换成list<Map>
     * @author: GuXiYang
     * @date: 2017/05/08 10:27:16
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            XLog.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * @param jsonStr json字符串
     * @return
     * @Description： json字符串转换为Map
     * @author: GuXiYang
     * @date: 2017/05/08 10:32:33
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            XLog.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }
}
