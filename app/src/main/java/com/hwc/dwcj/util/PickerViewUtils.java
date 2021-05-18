package com.hwc.dwcj.util;

import android.content.Context;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hwc.dwcj.interfaces.PickerViewSelectDateResult;
import com.hwc.dwcj.interfaces.PickerViewSelectDateTimeResult;
import com.hwc.dwcj.interfaces.PickerViewSelectOptionsResult;
import com.hwc.dwcj.interfaces.PickerViewSelectTimeResult;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Create by hwc on 2020/11/6
 * 弹窗选择工具类
 * 样式统一
 */
public class PickerViewUtils {

    public static float lineSpace = 2.5f;

    /**
     * 选择年 2000
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectYear(Context mContext, int startYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(startDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, false, false, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }


    /**
     * 选择年月 2000-12
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectYearAndMonth(Context mContext, int startYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(endDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, false, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }

    /**
     * 选择年月 2000-12
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectDateResult
     * @return Date 返回Date对象
     */
    public static void selectYearAndMonthForDate(Context mContext, int startYear, String title, PickerViewSelectDateResult pickerViewSelectDateResult) {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                pickerViewSelectDateResult.getDateResult(date);

            }
        }).setDate(startDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, false, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }


    /**
     * 选择日期 2000-01-01
     * 起始年份到今天
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectDateByStartYear(Context mContext, int startYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(startDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                 .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
               .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }

    /**
     * 选择日期 2000-01-01
     * 起始年份到今天
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectBirthdayByStartYear(Context mContext, int startYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();
        nowDate.add(Calendar.YEAR, -16);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(nowDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }

    /**
     * 选择日期 2000-01
     * 起始年份到今天
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectBirthdayMonthByStartYear(Context mContext, int startYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //注：（1）年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();
        Calendar nowDate = Calendar.getInstance();
        nowDate.add(Calendar.YEAR, -16);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(nowDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, false, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }


    /**
     * 选择日期 2000-01-01
     * 今天到结束年份
     *
     * @param mContext
     * @param endYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectDateByEndYear(Context mContext, int endYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //(1)年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, 11, 1);
        Calendar startDate = Calendar.getInstance();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(startDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }

    /**
     * 选择日期和时间 分别返回 2000-01-01 10:10
     * 今天到结束年份
     *
     * @param mContext
     * @param endYear
     * @param title
     * @param pickerViewSelectDateTimeResult
     */
    public static void selectTimeByEndYear(Context mContext, int endYear, String title, PickerViewSelectDateTimeResult pickerViewSelectDateTimeResult) {
        //(1)年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, 11, 1);
        Calendar startDate = Calendar.getInstance();

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String d = dateFormatter.format(date);//日期 String
                String t = timeFormatter.format(date);//时间 String
                pickerViewSelectDateTimeResult.getTimeResult(d, t);

            }
        }).setDate(startDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, true, true, true, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }

    /**
     * 选择日期 2000-01-01
     * 起始年份到终止年份
     *
     * @param mContext
     * @param startYear
     * @param title
     * @param pickerViewSelectTimeResult
     */
    public static void selectDateByStartYearAndEndYear(Context mContext, int startYear, int endYear, String title, PickerViewSelectTimeResult pickerViewSelectTimeResult) {
        //(1)年份可以随便设置 (2)月份是从0开始的（0代表1月 11月代表12月），即设置0代表起始时间从1月开始
        //(3)日期必须从1开始，因为2月没有30天，设置其他日期时，2月份会从设置日期开始显示导致出现问题
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(endYear, 11, 1);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            public void onTimeSelect(final Date date, View v) {
                String choiceTime = formatter.format(date);//日期 String
                pickerViewSelectTimeResult.getTimeResult(choiceTime);

            }
        }).setDate(startDate)//设置系统时间为当前时间
                .setRangDate(startDate, endDate)//设置控件日期范围 也可以不设置默认1900年到2100年
                .setType(new boolean[]{true, true, true, false, false, false})//设置年月日时分秒是否显示 true:显示 false:隐藏
                //.setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .isCyclic(false)//是否循环显示日期 例如滑动到31日自动转到1日 有个问题：不能实现日期和月份联动
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setLineSpacingMultiplier(lineSpace)
                .build();
        pvTime.show();
    }

    /**
     * 自定义选择 返回选项位置
     * 不联动选择
     *
     * @param mContext
     * @param title
     * @param listData1
     * @param listData2
     * @param listData3
     * @param pickerViewSelectOptionsResult
     */
    public static void selectOptions(Context mContext, String title, List<String> listData1, List<String> listData2, List<String> listData3, PickerViewSelectOptionsResult pickerViewSelectOptionsResult) {
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                pickerViewSelectOptionsResult.getOptionsResult(options1, option2, options3);
            }
        }).isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .setLineSpacingMultiplier(lineSpace)
                .build();//创建

        pvOptions.setNPicker(listData1, listData2, listData3);

        pvOptions.show();

    }


    /**
     * 自定义选择 返回选项位置
     * 联动选择
     *
     * @param mContext
     * @param title
     * @param listData1
     * @param listData2
     * @param listData3
     * @param pickerViewSelectOptionsResult
     */
    public static void selectOptionsDependent(Context mContext, String title, List<String> listData1, List<List<String>> listData2, List<List<List<String>>> listData3, PickerViewSelectOptionsResult pickerViewSelectOptionsResult) {
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                pickerViewSelectOptionsResult.getOptionsResult(options1, option2, options3);
            }
        }).isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0x1F191F25)//设置分割线颜色
                .setSubmitColor(0xFFF79D1F)//确定按钮文字颜色
                .setCancelColor(0xFFA3A5A8)//取消按钮文字颜色
                .setTitleText(title)//标题文字
                .setTitleColor(0xFF191F25)//标题文字颜色
                .setSelectOptions(0)//设置选择第一个
                .setOutSideCancelable(false)//点击背的地方不消失
                .setLineSpacingMultiplier(lineSpace)
                .build();//创建
        if (listData1 == null) {
            return;
        } else if (listData2 == null && listData3 == null) {
            pvOptions.setPicker(listData1);
        } else if (listData2 != null && listData3 == null) {
            pvOptions.setPicker(listData1, listData2);
        } else if (listData2 != null && listData3 != null) {
            pvOptions.setPicker(listData1, listData2, listData3);
        }
        pvOptions.show();

    }

}
