package com.ekin.system.user.domain;

import com.cartisan.domains.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_user_roles")
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserRole extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_code")
    private String roleCode;

    private UserRole() {

    }

    public UserRole(String roleCode) {
        this.roleCode = roleCode;
    }
}
