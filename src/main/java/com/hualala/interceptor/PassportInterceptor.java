package com.hualala.interceptor;

import com.alibaba.fastjson.JSON;
import com.hualala.bean.User;
import com.hualala.common.ResultCode;
import com.hualala.common.ResultUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author YuanChong
 * @create 2019-06-01 13:08
 * @desc 登陆拦截器 token认证
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if(request.getSession() != null && request.getSession().getAttribute("accessToken") != null) {
            User user = (User)request.getSession().getAttribute("accessToken");
            UserHolder.setUser(user);
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String error = JSON.toJSONString(ResultUtils.error(ResultCode.LOGIN_LOST));
        PrintWriter writer = response.getWriter();
        writer.append(error);
        writer.flush();
        writer.close();
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        UserHolder.clear();
    }
}
