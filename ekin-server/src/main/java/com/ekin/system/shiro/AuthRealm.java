//package com.ekin.system.shiro;
//
//import com.cartisan.utils.RedisUtil;
//import com.ekin.system.constant.LoginKey;
//import com.ekin.system.appservice.user.response.UserResponse;
//import com.ekin.system.queries.PermissionQueryMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//
///**
// * @author colin
// */
//@Component
//@Slf4j
//public class AuthRealm extends AuthorizingRealm {
//
//
//    private final RedisUtil redisUtil;
//
//    private final PermissionQueryMapper permissionQueryMapper;
//
//    @Autowired
//    public AuthRealm(RedisUtil redisUtil, PermissionQueryMapper permissionQueryMapper) {
//        this.redisUtil = redisUtil;
//        this.permissionQueryMapper = permissionQueryMapper;
//    }
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JwtToken;
//    }
//
//    /**
//     * 授权
//     * @param principals
//     * @return
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        UserResponse userResponse = null;
//
//        if (principals != null) {
//            userResponse = (UserResponse) principals.getPrimaryPrincipal();
//        }
//
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//
//        info.setRoles(new HashSet<>(userResponse.getRoleCodes()));
//        info.setStringPermissions(new HashSet<>(permissionQueryMapper.getPermissionCodesByRoleCodes(userResponse.getRoleCodes())));
//
//        return null;
//    }
//
//    /**
//     * 认证登录
//     * 获取身份验证信息 Shiro 中，默认使用此方法进行用户名正确与否验证
//     * @param authenticationToken 用户身份信息 token
//     * @return 返回封闭了用户信息的 AuthenticationInfo 实例
//     * @throws AuthenticationException
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        String token = (String)authenticationToken.getCredentials();
//
//        if (token == null) {
//            throw new AuthenticationException("token 为空");
//        }
//
//        final UserResponse user = redisUtil.get(LoginKey.token, token);
//
//        if (user == null) {
//            throw new AuthenticationException("登录过期，请重新登录");
//        }
//
//        redisUtil.renewal(LoginKey.token, token);
//
//        return new SimpleAuthenticationInfo(user, token, user.getUsername());
//
//
//    }
//}
