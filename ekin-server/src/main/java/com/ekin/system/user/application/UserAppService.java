package com.ekin.system.user.application;

import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.ekin.constant.SystemCodeMessage;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.AssignService;
import com.ekin.system.user.domain.RegisterService;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.request.AssignDepartmentsCommand;
import com.ekin.system.user.request.AssignRolesCommand;
import com.ekin.system.user.response.UserDto;
import com.ekin.system.user.request.CreateAccountCommand;
import com.ekin.system.user.request.SearchUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;


/**
 * @author colin
 */
@Service
public class UserAppService {
    private final RegisterService registerService;
    private final AssignService assignService;
    private final UserRepository repository;

    @Autowired
    public UserAppService(RegisterService registerService,
                          AssignService assignService,
                          UserRepository repository) {
        this.registerService = registerService;
        this.assignService = assignService;
        this.repository = repository;
    }

    public UserDto getUser(Long id) {
        return UserDto.convertFrom(repository.findById(id).get());
    }

    public PageResult<UserDto> searchUsers(SearchUser searchParam, Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);

        final Page<User> searchResult = repository.findAll(querySpecification(searchParam), pageRequest);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                searchResult.map(UserDto::convertFrom).getContent());
    }

    @Transactional(rollbackOn = Exception.class)
    public void createAccount(CreateAccountCommand command) {
        final User user = registerService.register(
                command.getUsername(), command.getPhone(), command.getEmail(), command.getRealName());

        assignService.assignRoles(user.getId(), command.getRoleCodes());
        assignService.assignDepartments(user.getId(), command.getDepartmentIds());
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignRoles(Long userId, AssignRolesCommand command) {
        assignService.assignRoles(userId, command.getRoleCodes());
    }

    @Transactional(rollbackOn = Exception.class)
    public void assignDepartments(Long userId, AssignDepartmentsCommand command) {
        assignService.assignDepartments(userId, command.getDepartmentIds());
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
