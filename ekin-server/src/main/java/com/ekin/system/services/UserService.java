package com.ekin.system.services;

import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.SnowflakeIdWorker;
import com.ekin.system.constants.SystemCodeMessage;
import com.ekin.system.domains.User;
import com.ekin.system.dtos.UserDto;
import com.ekin.system.params.UserParam;
import com.ekin.system.params.UserSearchParam;
import com.ekin.system.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author colin
 */
@Service
public class UserService {
    private final UserRepository repository;

    private final SnowflakeIdWorker idWorker;

    @Autowired
    public UserService(UserRepository repository, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public UserDto getUser(Long id) {
        return UserDto.convertFrom(repository.findById(id).get());
    }

    public Optional<User> findByUserName(String username) {
        return repository.findByUsername(username);
    }

    public PageResult<UserDto> searchUsers(UserSearchParam searchParam, Integer currentPage, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);

        final Page<User> searchResult = repository.findAll(new UserSearchSpecification(searchParam), pageRequest);

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

    private class UserSearchSpecification implements Specification<User> {

        private final UserSearchParam searchParam;

        UserSearchSpecification(UserSearchParam searchParam) {
            this.searchParam = searchParam;
        }

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicateList = new ArrayList<>();

            if (StringUtils.isNotBlank(searchParam.getUsername())) {
                predicateList.add(criteriaBuilder.like(root.get("username"),
                        "%" + searchParam.getUsername() + "%"));
            }

            if (StringUtils.isNotBlank(searchParam.getEmail())) {
                predicateList.add(criteriaBuilder.like(root.get("email"),
                        "%" + searchParam.getEmail() + "%"));
            }

            if (StringUtils.isNotBlank(searchParam.getPhone())) {
                predicateList.add(criteriaBuilder.like(root.get("phone"),
                        "%" + searchParam.getPhone() + "%"));
            }

            if (searchParam.getSex() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("sex"), searchParam.getSex()));
            }

            if (searchParam.getStatus() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("status"), searchParam.getStatus()));
            }

            if (predicateList.size() > 0) {
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            } else {
                return null;
            }
        }
    }
}
