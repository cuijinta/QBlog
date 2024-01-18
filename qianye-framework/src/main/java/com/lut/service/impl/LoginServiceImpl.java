package com.lut.service.impl;

import com.lut.pojo.entity.User;
import com.lut.result.Result;
import com.lut.service.LoginService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.JwtUtil;
import com.lut.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户登录
     * @param user 传入的用户对象
     * @return
     */
    @Override
    public Result login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
//        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//        String userId = loginUser.getUser().getId().toString();
//        String jwt = JwtUtil.createJWT(userId);
//        //把用户信息存入redis
//        redisUtils.setCacheObject("bloglogin:"+userId,loginUser);
//
//        //把token和userinfo封装 返回
//        //把User转换成UserInfoVo
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
//        return Result.okResult(vo);
        return null;
    }
}
