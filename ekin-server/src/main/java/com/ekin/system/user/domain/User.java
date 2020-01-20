package com.ekin.system.user.domain;

import com.cartisan.domains.AggregateRoot;
import com.cartisan.domains.SoftDeleteEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_users")
@Getter
@EqualsAndHashCode(callSuper = true)
@Where(clause = "active=1 and deleted=0")
public class User extends SoftDeleteEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "real_name")
    private String realName;
    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "sex")
    private Integer sex;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<UserDepartment> departments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<UserRole> roles = new ArrayList<>();

    private User() {

    }

    public User(Long id, String username, String phone, String email, String password, String realName) {
        this.id = id;

        this.username = username;
        this.phone = Optional.ofNullable(phone).orElse("");
        this.email = Optional.ofNullable(email).orElse("");
        this.password = password;
        this.realName = realName;

        this.avatar = "";
        this.birthday = new Date();
        this.sex = 1;
        this.status = 1;
    }

    public void assignRoles(List<String> roleCodes) {
        this.roles.removeAll(this.roles.stream()
                .filter(role -> !roleCodes.contains(role.getRoleCode()))
                .collect(toList()));

        roleCodes.removeAll(this.roles.stream().map(UserRole::getRoleCode).collect(toList()));
        roleCodes.forEach(roleCode -> this.roles.add(new UserRole(roleCode)));
    }

    public void assignDepartments(List<Long> departmentIds) {
        this.departments.removeAll(this.departments.stream()
                .filter(department -> !departmentIds.contains(department.getDepartmentId()))
                .collect(toList()));

        departmentIds.removeAll(this.departments.stream().map(UserDepartment::getDepartmentId).collect(toList()));
        departmentIds.forEach(departmentId -> this.departments.add(new UserDepartment(departmentId)));
    }

    public void frozen() {
        this.status = 2;
    }

    public void unFrozen() {
        this.status = 1;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
