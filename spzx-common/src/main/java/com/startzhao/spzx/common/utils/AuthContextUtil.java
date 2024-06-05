package com.startzhao.spzx.common.utils;

import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.entity.user.UserInfo;

/**
 * ClassName: AuthContextUtil
 * Package: com.startzhao.spzx.common.utils
 * Description: ThreadLocal工具类
 *
 * @Author StartZhao
 * @Create 2024/4/21 18:30
 * @Version 1.0
 */
public class AuthContextUtil {

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>() ;

    // 定义存储数据的静态方法
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 定义获取数据的方法
    public static SysUser get() {
        return threadLocal.get() ;
    }

    // 删除数据的方法
    public static void remove() {
        threadLocal.remove();
    }

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }

}
