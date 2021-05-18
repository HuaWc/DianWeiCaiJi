package com.hwc.dwcj.base;


import com.hwc.dwcj.entity.UserInfo;
import com.hwc.dwcj.util.AESCipher;
import com.hwc.dwcj.util.SerializableUtil;
import com.zds.base.util.Preference;
import com.zds.base.util.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;


public class Storage {
    /**
     * 保存token
     *
     * @param token
     */
    public static void saveToken(String token) {
        if (token == null || token.equals("")) {
            return;
        }
        Preference.saveStringPreferences(Utils.getContext(), Constant.TOKEN, token);
    }

    /**
     * 获取token
     */
    public static String getToken() {
        return Preference.getStringPreferences(Utils.getContext(), Constant.TOKEN, "");
    }

    /**
     * 清除缓存用户信息数据
     */
    public static void ClearUserInfo() {
        saveToken("");
        Preference.saveStringPreferences(Utils.getContext(), Constant.SAVE_USER, "");
    }

    /**
     * 保存用户信息至缓存
     *
     * @param userInfo
     */
    public static void saveUsersInfo(UserInfo userInfo) {
        try {
            String aesUser = SerializableUtil.obj2Str(userInfo);
            try {
                aesUser = AESCipher.encrypt(aesUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Preference.saveStringPreferences(Utils.getContext(), Constant.SAVE_USER, aesUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取缓存数据
     *
     * @return
     */
    public static UserInfo GetUserInfo() {
        try {
            String userInfoStr = Preference.getStringPreferences(Utils.getContext(), Constant.SAVE_USER, "");
            if (userInfoStr.equals("")) {
                return null;
            }
            try {
                userInfoStr = AESCipher.decrypt(userInfoStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            UserInfo aesUser = (UserInfo) SerializableUtil.str2Obj(userInfoStr);
            return aesUser;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 保存登陆名
     *
     * @param userName
     */

    public static void saveLoginUser(String userName) {
        if (userName == null || userName.equals("")) {
            return;
        }
        Preference.saveStringPreferences(Utils.getContext(), Constant.LOGIN_NAME, userName);
    }

    /**
     * 获取登陆名
     */
    public static String getLoginUser() {
        return Preference.getStringPreferences(Utils.getContext(), Constant.LOGIN_NAME, "");
    }

}
