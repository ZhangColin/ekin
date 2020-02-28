package com.ekin.system.user.application;

import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.ekin.constant.SystemCodeMessage;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.AssignService;
import com.ekin.system.user.domain.RegisterService;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.request.AssignOrganizationsCommand;
import com.ekin.system.user.request.AssignRolesCommand;
import com.ekin.system.user.request.CreateAccountCommand;
import com.ekin.system.user.request.UserQuery;
import com.ekin.system.user.response.UserConverter;
import com.ekin.system.user.response.UserDetailDto;
import com.ekin.system.user.response.UserDto;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;


/**
 * @author colin
 */
@Service
@Slf4j
public class UserAppService {
    private final RegisterService registerService;
    private final AssignService assignService;
    private final UserRepository repository;
    private final UserConverter userConverter;

    public UserAppService(RegisterService registerService,
                          AssignService assignService,
                          UserRepository repository,
                          UserConverter userConverter) {
        this.registerService = registerService;
        this.assignService = assignService;
        this.repository = repository;
        this.userConverter = userConverter;
    }

    public UserDetailDto getUser(Long id) {
        return UserDetailDto.convertFrom(repository.findById(id).get());
    }

    public PageResult<UserDto> searchUsers(@NonNull UserQuery userQuery, @NonNull Pageable pageable) {
        final Page<User> searchResult = repository.findAll(querySpecification(userQuery), pageable);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                userConverter.convert(searchResult.getContent()));
    }

    @Transactional(rollbackOn = Exception.class)
    public UserDetailDto createAccount(CreateAccountCommand command) {
        final User user = registerService.register(
                command.getUsername(), command.getPhone(), command.getEmail(), command.getRealName());

        assignService.assignRoles(user.getId(), command.getRoleIds());
        assignService.assignOrganizations(user.getId(), command.getOrganizationIds());

        return UserDetailDto.convertFrom(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignRoles(Long userId, AssignRolesCommand command) {
        assignService.assignRoles(userId, command.getRoleIds());
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignDepartments(Long userId, AssignOrganizationsCommand command) {
        assignService.assignOrganizations(userId, command.getOrganizationIds());
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
    public void changePassword(Long id, String password) {
        final Optional<User> userOptional = repository.findById(id);

        if (!userOptional.isPresent()) {
            throw new CartisanException(SystemCodeMessage.USER_NOT_EXIST);
        }

        final User user = userOptional.get();

        user.changePassword(password);

        repository.save(user);
    }
}
