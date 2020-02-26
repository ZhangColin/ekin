package com.ekin.system.user.application;

import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.ekin.security.CurrentUserInfo;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author colin
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = requireUserPresent(repository.findByUsername(username));

//        UmsAdmin admin = adminService.getAdminByUsername(username);
//        if (admin != null) {
//            List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
//            return new AdminUserDetails(admin,permissionList);
//        }
//        throw new UsernameNotFoundException("用户名或密码错误");

//        private UmsAdmin umsAdmin;
//        private List<UmsPermission> permissionList;
//    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
//            this.umsAdmin = umsAdmin;
//            this.permissionList = permissionList;
//        }
//
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            //返回当前用户的权限
//            return permissionList.stream()
//                    .filter(permission -> permission.getValue()!=null)
//                    .map(permission ->new SimpleGrantedAuthority(permission.getValue()))
//                    .collect(Collectors.toList());
//        }

        return buildUserDetails(user);
    }

    private CurrentUserInfo buildUserDetails(User user) {
        return new CurrentUserInfo(user.getId(), user.getUsername(), user.getPassword());
    }

    private User requireUserPresent(Optional<User> userOptional) {
        return userOptional
                .orElseThrow(() -> new CartisanException(CodeMessage.FAIL.fillArgs("用户名或密码不正确")));
    }


}
