package com.ekin.systemcontext.domains;

import com.cartisan.common.domains.AggregateRoot;
import com.cartisan.common.domains.SoftDeleteEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Column(name = "real_name")
    @Setter
    private String realName;
    @Column(name = "password")
    private String password;
    @Column(name = "slat")
    private String slat;
    @Column(name = "avatar")
    @Setter
    private String avatar;
    @Column(name = "birthday")
    @Setter
    private Date birthday;
    @Column(name = "sex")
    @Setter
    private Integer sex;
    @Column(name = "email")
    @Setter
    private String email;
    @Column(name = "phone")
    @Setter
    private String phone;
    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<UserDepartment> departments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<UserRole> roles = new ArrayList<>();

    private User() {

    }

    public User(Long userId, String username, String password) {
        this.id = userId;
        this.username = username;
        generateEncryptPassword(password);
        this.realName = "";
        this.avatar = "";
        this.birthday = new Date();
        this.sex = 1;
        this.email = "";
        this.phone = "";
        this.status = 1;
    }

    public void assignRoles(List<String> roleCodes) {
        this.roles.removeAll(this.roles.stream()
                .filter(role->!roleCodes.contains(role.getRoleCode()))
                .collect(toList()));

        roleCodes.removeAll(this.roles.stream().map(UserRole::getRoleCode).collect(toList()));
        roleCodes.forEach(roleCode->this.roles.add(new UserRole(roleCode)));
    }

    public void assignDepartments(List<Long> departmentIds){
        this.departments.removeAll(this.departments.stream()
                .filter(department->!departmentIds.contains(department.getDepartmentId()))
                .collect(toList()));

        departmentIds.removeAll(this.departments.stream().map(UserDepartment::getDepartmentId).collect(toList()));
        departmentIds.forEach(departmentId->this.departments.add(new UserDepartment(departmentId)));
    }

    public void frozen() {
        this.status = 2;
    }

    public void unFrozen() {
        this.status = 1;
    }

    public void changePassword(String password) {
        generateEncryptPassword(password);
    }

    public boolean valid(String password) {
        final String validEncryptPassword = this.encryptPassword(password, this.slat);

        return StringUtils.equals(validEncryptPassword, this.password);
    }

    private void generateEncryptPassword(String password) {
        this.slat = RandomStringUtils.randomAlphabetic(8);
        this.password = this.encryptPassword(password, this.slat);
    }

    private String encryptPassword(String password, String slat) {
        return DigestUtils.md5Hex(password + slat);
    }
}
