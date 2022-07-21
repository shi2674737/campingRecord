package com.example.campingrecord.interceptor;

import com.example.campingrecord.constant.Constants;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.exception.TokenException;
import com.example.campingrecord.service.UserService;
import com.example.campingrecord.utils.JwtUtil;
import com.example.campingrecord.vo.result.UResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 *
 * @author scy
 * @name JwtInterceptor
 * @date 2022-07-21
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        UResult uResult = UResult.ok();
        //获取请求头中的令牌
        String token = request.getHeader(Constants.AUTHORIZATION);
        // 设置当前请求中用户信息 && 校验token
        this.checkToken(request, token);
//        String json = new ObjectMapper().writeValueAsString(uResult);
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().println(json);
        return true;
    }

    private void checkToken(HttpServletRequest request, String token) {
        JwtUtil.verify(token);
        String userId = JwtUtil.getString(Constants.USER_ID, token);
        String account = JwtUtil.getString(Constants.ACCOUNT, token);
        String password = JwtUtil.getString(Constants.PASSWORD, token);
        // 校验账号密码
        User user = userService.getById(userId);
        if (!user.getAccount().equals(account)) {
            throw new TokenException("账号不正确");
        }
        if (!user.getPassword().equals(password)) {
            throw new TokenException("密码不正确");
        }
        // 过期时间
        if (JwtUtil.isExpire(token)) {
            throw new TokenException("登录过期");
        }

        request.setAttribute(Constants.CURRENT_USER, user);
    }
}
