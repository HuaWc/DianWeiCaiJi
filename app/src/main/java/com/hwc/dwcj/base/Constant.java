/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hwc.dwcj.base;


public class Constant {


    // 网络请求
    /**
     * 成功
     */
    public static final int CODESUCCESS = 200;
    /**
     * 请求失败 错误
     */
    public static final int CODEERROR = 0;
    /**
     * 需要登陆
     */
    public static final int NEEDLOGIN = 401;
    /**
     * token  Preference 字典
     */
    public static final String TOKEN = "login_name";
    /**
     * 保存用户信息  Preference 字典
     */
    public static final String SAVE_USER = "save_user";
    /**
     * 缓存用户手机号  Preference 字典
     */
    public static final String LOGIN_NAME = "login_name";

    /**
     * 生成二维码的前缀的域名
     */
    public static final String URL_PREFIX = "http://192.168.1.109:8080/?positionCode=";

}
