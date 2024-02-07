package com.lut.filter;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.pojo.entity.LoginUser;
import com.lut.result.Result;
import com.lut.utils.JwtUtil;
import com.lut.utils.RedisUtils;
import com.lut.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头拿到 token
        String token = request.getHeader("token");
        if(StringUtils.isNotBlank(token)) {
            //解析获取userId
            Claims claims = null;
            try {
                claims = JwtUtil.parseJWT(token);
            } catch (Exception e) {
                e.printStackTrace();
                //token 异常情况
                Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
            }
            String userId = claims.getSubject();

            //从redis中查用户的信息
            LoginUser loginUser = redisUtils.getCacheObject("bloglogin:" + userId);
            //如果获取不到
            if(Objects.isNull(loginUser)){
                //说明登录过期  提示重新登录
                Result result = Result.errorResult(AppHttpCodeEnum.NEED_LOGIN);
                WebUtils.renderString(response, JSON.toJSONString(result));
                return;
            }
            //存入SecurityContextHolder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }
        


    }
}
