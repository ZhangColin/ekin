package com.ekin.system.appservice.user;

import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.ekin.system.constant.SystemCodeMessage;
import com.ekin.system.domain.user.User;
import com.ekin.system.appservice.user.response.UserDto;
import com.ekin.system.appservice.user.request.UserParam;
import com.ekin.system.appservice.user.request.SearchUser;
import com.ekin.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;


/**
 * @author colin
 */
@Service
public class UserAppService {
    private final UserRepository repository;

    private final SnowflakeIdWorker idWorker;

    @Autowired
    public UserAppService(UserRepository repository, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public UserDto getUser(Long id) {
        return UserDto.convertFrom(repository.findById(id).get());
    }

    public Optional<User> findByUserName(String username) {
        return repository.findByUsername(username);
    }

    public PageResult<UserDto> searchUsers(SearchUser searchParam, Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);

        final Page<User> searchResult = repository.findAll(querySpecification(searchParam), pageRequest);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                searchResult.map(UserDto::convertFrom).getContent());
    }

    @Transactional(rollbackOn = Exception.class)
    public void addUser(UserParam userParam) {
        if (repository.existsByEmail(userParam.getEmail())) {
            throw new CartisanException(SystemCodeMessage.EMAIL_EXIST);
        }

        if (repository.existsByPhone(userParam.getPhone())) {
            throw new CartisanException(SystemCodeMessage.PHONE_EXIST);
        }

        final User user = new User(idWorker.nextId(), userParam.getUsername(), userParam.getPassword());
        fillUserInfo(userParam, user);

        user.assignRoles(userParam.getRoleCodes());
        user.assignDepartments(userParam.getDepartmentIds());

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editUser(Long id, UserParam userParam) {
        if (repository.existsByEmailAndIdNot(userParam.getEmail(), id)) {
            throw new CartisanException(SystemCodeMessage.EMAIL_EXIST);
        }

        if (repository.existsByPhoneAndIdNot(userParam.getPhone(), id)) {
            throw new CartisanException(SystemCodeMessage.PHONE_EXIST);
        }

        final User user = repository.findById(id)
                .orElseThrow(()-> new CartisanException(SystemCodeMessage.USER_NOT_EXIST));

        fillUserInfo(userParam, user);

        user.assignRoles(userParam.getRoleCodes());
        user.assignDepartments(userParam.getDepartmentIds());

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeUser(long id) {
        repository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public void frozen(Long id) {
        final Optional<User> userOptional = repository.findById(id);

        if (!userOptional.isPresent()) {
            throw new CartisanException(SystemCodeMessage.USER_NOT_EXIST);
        }

        final User user = userOptional.get();

        user.frozen();

        repository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public void unFrozen(Long id) {
        final Optional<User> userOptional = repository.findById(id);

        if (!userOptional.isPresent()) {
            throw new CartisanException(SystemCodeMessage.USER_NOT_EXIST);
        }

        final User user = userOptional.get();

        user.unFrozen();

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


    private void fillUserInfo(UserParam userParam, User user) {
        user.setRealName(userParam.getRealName());
        user.setAvatar(userParam.getAvatar());
        user.setBirthday(userParam.getBirthday());
        user.setSex(userParam.getSex());
        user.setEmail(userParam.getEmail());
        user.setPhone(userParam.getPhone());
    }

}
