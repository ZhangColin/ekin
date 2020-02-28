package com.ekin.system.role.domain;

import com.cartisan.domains.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_role_resources")
@Getter
@EqualsAndHashCode(callSuper = true)
public class RoleResource extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    private RoleResource() {
    }

    public RoleResource(Long resourceId) {
        this.resourceId = resourceId;
    }
}
