package com.ekin.system.user.application;

import com.cartisan.dto.PageResult;
import com.ekin.security.LoginService;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.AssignService;
import com.ekin.system.user.domain.ChangePasswordService;
import com.ekin.system.user.domain.RegisterService;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.request.AssignOrganizationsCommand;
import com.ekin.system.user.request.AssignRolesCommand;
import com.ekin.system.user.request.CreateAccountCommand;
import com.ekin.system.user.request.UserQuery;
import com.ekin.system.user.response.UserConverter;
import com.ekin.system.user.response.UserDetailConverter;
import com.ekin.system.user.response.UserDetailDto;
import com.ekin.system.user.response.UserDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.repository.ConditionSpecifications.querySpecification;
import static com.cartisan.util.AssertionUtil.requirePresent;


/**
 * @author colin
 */
@Service
@Slf4j
public class UserAppService {
    private final RegisterService registerService;
    private final AssignService assignService;
    private final ChangePasswordService changePasswordService;
    private final UserRepository repository;
    private final UserConverter userConverter = UserConverter.CONVERTER;
    private final UserDetailConverter userDetailConverter = UserDetailConverter.CONVERTER;

    public UserAppService(RegisterService registerService,
                          AssignService assignService,
                          ChangePasswordService changePasswordService,
                          UserRepository repository) {
        this.registerService = registerService;
        this.assignService = assignService;
        this.changePasswordService = changePasswordService;
        this.repository = repository;
    }

    public PageResult<UserDto> searchUsers(@NonNull UserQuery userQuery, @NonNull Pageable pageable) {
        final Page<User> searchResult = repository.findAll(querySpecification(userQuery), pageable);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                userConverter.convert(searchResult.getContent()));
    }

    public List<UserDto> findAllNormalUsers() {
        final UserQuery userQuery = new UserQuery();
        userQuery.setStatus(1);

        return userConverter.convert(repository.findAll(querySpecification(userQuery)));
    }

    public UserDetailDto getUser(Long id) {
        return userDetailConverter.convert(requirePresent(repository.findById(id)));
    }

    public Optional<UserDto> getUserByRealName(String realName) {
        return repository.findByRealName(realName).map(userConverter::convert);
    }


    @Transactional(rollbackOn = Exception.class)
    public UserDetailDto createAccount(CreateAccountCommand command) {
        final User user = registerService.register(
                command.getUsername(), command.getPhone(), command.getEmail(), command.getRealName());

        assignService.assignRoles(user, command.getRoleIds());
        assignService.assignOrganization(user, command.getOrganizationIds());

        repository.save(user);
        return userDetailConverter.convert(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignRoles(Long userId, AssignRolesCommand command) {
        final User user = requirePresent(repository.findById(userId));
        assignService.assignRoles(user, command.getRoleIds());

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignOrganization(Long userId, AssignOrganizationsCommand command) {
        final User user = requirePresent(repository.findById(userId));
        assignService.assignOrganization(user, command.getOrganizationIds());

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void disable(Long userId) {
        final User user = requirePresent(repository.findById(userId));

        user.disable();

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void enable(Long userId) {
        final User user = requirePresent(repository.findById(userId));

        user.enable();

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void resetPassword(Long userId) {
        final User user = requirePresent(repository.findById(userId));

        changePasswordService.resetPassword(user);

//        loginService.logoutByUsername(user.getUsername());

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void setAvatar(Long userId, String avatar) {
        final User user = requirePresent(repository.findById(userId));

        user.setAvatar(avatar);

        repository.save(user);
    }
}
