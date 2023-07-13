package com.ekin.security;

import com.cartisan.constant.CodeMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cartisan.response.ResponseUtil.fail;

/**
 * 自定义返回结果：没有权限访问时
 * @author colin
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public RestfulAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 当用户在没有授权的情况下访问受保护的 REST 资源时，将调用此方法发送 403 响应
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
//        response.sendError(CodeMessage.FORBIDDEN.getCode(), CodeMessage.FORBIDDEN.getMessage());
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(CodeMessage.FORBIDDEN.getCode());
        response.getWriter().println(objectMapper.writeValueAsString(fail(CodeMessage.FORBIDDEN).getBody()));
        response.getWriter().flush();
    }
}
