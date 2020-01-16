package com.ekin.system.user.domain;

import com.cartisan.domains.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_user_departments")
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserDepartment extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id")
    private Long departmentId;

    private UserDepartment() {
    }

    public UserDepartment(Long departmentId) {
        this.departmentId = departmentId;
    }
}
