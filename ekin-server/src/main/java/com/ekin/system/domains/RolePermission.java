package com.ekin.system.domains;

import com.cartisan.domains.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_role_permissions")
@Getter
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_id")
    private Long permissionId;

    private RolePermission() {
    }

    public RolePermission(Long permissionId) {
        this.permissionId = permissionId;
    }
}
