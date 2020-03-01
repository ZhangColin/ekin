package com.ekin.system.user.domain;

import com.cartisan.domains.AggregateRoot;
import com.cartisan.domains.SoftDeleteEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @Setter
    private String avatar;

    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "sex")
    private Integer sex;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id")
    private List<UserOrganization> organizations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id")
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
        this.birthday = null;
        this.sex = 1;
        this.status = 1;
    }

    public void profile(LocalDate birthday, Integer sex) {
        this.birthday = birthday;
        this.sex = sex;
    }

    public void assignRoles(List<Long> roleIds) {
        this.roles.removeAll(this.roles.stream()
                .filter(role -> !roleIds.contains(role.getRoleId()))
                .collect(toList()));

        roleIds.removeAll(this.roles.stream().map(UserRole::getRoleId).collect(toList()));
        roleIds.forEach(roleId -> this.roles.add(new UserRole(roleId)));
    }

    public void assignOrganizations(List<Long> organizationIds) {
        this.organizations.removeAll(this.organizations.stream()
                .filter(userOrganization -> !organizationIds.contains(userOrganization.getOrganizationId()))
                .collect(toList()));

        organizationIds.removeAll(this.organizations.stream().map(UserOrganization::getOrganizationId).collect(toList()));
        organizationIds.forEach(organizationId -> this.organizations.add(new UserOrganization(organizationId)));
    }

    public void disable() {
        this.status = 2;
    }

    public void enable() {
        this.status = 1;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
