package com.lut.service.impl;

import com.lut.pojo.entity.LoginUser;
import com.lut.pojo.entity.User;
import com.lut.pojo.vo.UserInfoVO;
import com.lut.pojo.vo.UserLoginVO;
import com.lut.result.Result;
import com.lut.service.LoginService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.JwtUtil;
import com.lut.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户登录
     *
     * @param user 传入的用户对象
     * @return
     */
    @Override
    @Transactional
    public Result login(User user) {
        //封装用户名和密码为 UsernamePasswordAuthenticationToken 对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //认证通过，获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        log.info("token:", token);
        log.info("loginUser", loginUser);
        //把用户信息存入redis (设置过期时间72小时)
        redisUtils.setCacheObject("user_" + userId, loginUser, 72, TimeUnit.HOURS);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVO userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        UserLoginVO vo = new UserLoginVO(token, userInfoVo);
        return Result.okResult(vo);
    }

    /**
     * 用户退出登录
     * @return
     */
    @Override
    public Result logout() {
        //获取token 解析出当前用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //删除 redis 当中的用户信息
        redisUtils.deleteObject("user_" + userId);
        return Result.okResult();
    }
}
