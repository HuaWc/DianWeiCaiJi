package com.hwc.dwcj.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzx on 2018/04/04/下午 5:37
 */

public class RegularCheckUtil {

    /**
     * 验证号码 手机号 固话均可
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "((^(13|14|15|16|17|18|19)[0-9]{9}$)|(^(([04]\\d{2,3}\\d{7,8})|(1[3584]\\d{9}))$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        if (id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * md5
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络

        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatTime(String time) {
        Long timer = -new Long(time) + System.currentTimeMillis() / 1000;
        if (timer / 60 <= 1) {//小于等于1分钟
            return "刚刚";
        } else if (timer / 60 > 1 && timer / (60 * 60) < 1) {//大于1分钟，小于一小时
            return (timer / 60) + "分钟前";
        } else if (timer / (60 * 60) >= 1 && timer / (24 * 60 * 60) < 1) {//大于等于1小时，小于一天
            return (timer / (60 * 60)) + "小时前";
        } else if (timer / (24 * 60 * 60) >= 1 && timer / (30 * 24 * 60 * 60) < 1) {
            return (timer / (24 * 60 * 60)) + "天前";
        } else {
            Long timet = new Long(time);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(timet * 1000);
        }
    }

    /**
     * editText 密码明文
     *
     * @param view
     * @param isPassword
     * @param image
     * @param imgMipmapClose
     * @param imgMipmapOpen
     */
    public static void editTextPlaintext(View view, boolean isPassword, View image, int imgMipmapClose, int imgMipmapOpen) {
        EditText editText = (EditText) view;
        ImageView imageView = (ImageView) image;
        if (isPassword) {
            //密码
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            if (imageView != null){
                imageView.setImageResource(imgMipmapClose);
            }
        } else {
            //明文
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            if (imageView != null){
                imageView.setImageResource(imgMipmapOpen);
            }
        }
        editText.setSelection(TextUtils.isEmpty(editText.getText().toString().trim()) ? 0 : editText.getText().toString().trim().length());
    }


    /**
     * editText 密码明文
     *
     * @param view
     * @param isPassword
     */
    public static void editTextPlaintextNoView(View view, boolean isPassword) {
        EditText editText = (EditText) view;
        if (isPassword) {
            //密码
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            //明文
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        editText.setSelection(TextUtils.isEmpty(editText.getText().toString().trim()) ? 0 : editText.getText().toString().trim().length());
    }


    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     */
    public static boolean isLegalName(String name) {
        if (name.contains("·") || name.contains("•")) {
            if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$")) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formtTimer(String time) {
        try {
            Long timer = new Long(time);
            if (timer <= 0) {
                return "";
            }
            Date date = new Date(timer);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            if (time.indexOf(".") != -1) {
                String[] mm = time.split(".");
                if (mm.length > 1 && "0".equals(mm[1])) {
                    formtTimer(mm[0]);
                }
            }
            Log.e("==============", e.getMessage());
        }
        return "";
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(Activity mContext, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }


    /**
     * 底部弹出动画
     *
     * @param type
     * @param view
     */
    public static void animation(final int type, View view, final Dialog dialog) {
        int height = 0;
        int height1 = 0;
        if (view instanceof LinearLayout) {
            LinearLayout llayout = (LinearLayout) view;
            llayout.measure(0, 0);
            if (type == 0) {
                height = llayout.getMeasuredHeight();
                height1 = 0;
            } else {
                height1 = llayout.getMeasuredHeight();
                height = 0;
            }
            Log.e("===========", height + "");
        }
        ObjectAnimator oA = ObjectAnimator.ofFloat(view, "translationY", height, height1);
        oA.setDuration(500);
        oA.start();

        oA.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (type == 1) {
                    dialog.dismiss();
                }
            }
        });
    }


    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 设置editText输入格式
     *
     * @param view
     */
    public static void setEditText(View view) {
        if (view instanceof EditText) {
            EditText et = (EditText) view;
            et.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }
    }

    /**
     * 判断区间
     *
     * @param text
     * @param num1
     * @param num2
     * @return
     */
    public static boolean IntgerString(String text, Long num1, Long num2) {
        if (!TextUtils.isEmpty(text)) {

            return Long.parseLong(text) < num1 || Long.parseLong(text) > num2;
        }
        return false;
    }


    /**
     * 关闭软键盘
     *
     * @param mActivity
     */
    public static void closeKeyboard(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && mActivity.getCurrentFocus() != null) {
            if (mActivity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;

    }

    /**
     * 返回app信息
     *
     * @param context
     * @return
     */
    public static String returnAppData(Context context) {
        return getLocalVersion(context) + "," + getLocalVersionName(context);
    }

}
