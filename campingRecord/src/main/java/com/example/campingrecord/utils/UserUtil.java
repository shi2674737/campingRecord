package com.example.campingrecord.utils;

import com.example.campingrecord.constant.Constants;
import com.example.campingrecord.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户信息
 *
 * @author Administrator
 * @name UserRequest
 * @date 2021-08-04 12:57
 */
public class UserUtil {
    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User) request.getAttribute(Constants.CURRENT_USER);
        return user;
    }

}
