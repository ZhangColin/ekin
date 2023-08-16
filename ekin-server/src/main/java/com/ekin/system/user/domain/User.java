package com.ekin.system.user.domain;

import com.cartisan.converter.OnOffStatusConverter;
import com.cartisan.domain.AggregateRoot;
import com.cartisan.domain.SoftDeleteEntity;
import com.cartisan.dp.OnOffStatus;
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
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "password")
    private String password;
    @Column(name = "avatar")
    @Setter
    private String avatar;
    @Column(name = "motto")
    @Setter
    private String motto;
    @Column(name = "status")
    @Convert(converter = OnOffStatusConverter.class)
    private OnOffStatus status;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinColumn(name = "user_id")
//    private List<UserOrganization> organizations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "user_id")
    private List<UserRole> roles = new ArrayList<>();

    protected User() {

    }

    public User(Long id, String username, String phone, String email, String password, String nickname) {
        this.id = id;

        this.username = username;
        this.phone = Optional.ofNullable(phone).orElse("");
        this.email = Optional.ofNullable(email).orElse("");
        this.password = password;
        this.nickname = nickname;

        this.avatar = "";
        this.motto = "";
        this.status = OnOffStatus.Enabled;
    }

    public void assignRoles(List<Long> roleIds) {
        this.roles.removeAll(this.roles.stream()
                .filter(role -> !roleIds.contains(role.getRoleId()))
                .collect(toList()));

        roleIds.removeAll(this.roles.stream().map(UserRole::getRoleId).collect(toList()));
        roleIds.forEach(roleId -> this.roles.add(new UserRole(roleId)));
    }

//    public void assignOrganizations(List<Long> organizationIds) {
//        this.organizations.removeAll(this.organizations.stream()
//                .filter(userOrganization -> !organizationIds.contains(userOrganization.getOrganizationId()))
//                .collect(toList()));
//
//        organizationIds.removeAll(this.organizations.stream().map(UserOrganization::getOrganizationId).collect(toList()));
//        organizationIds.forEach(organizationId -> this.organizations.add(new UserOrganization(organizationId)));
//    }

    public void disable() {
        this.status = OnOffStatus.Disabled;
    }

    public void enable() {
        this.status = OnOffStatus.Enabled;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
