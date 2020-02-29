package com.ekin.system.user.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_user_organizations")
@Getter
@EqualsAndHashCode
public class UserOrganization {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_id")
    private Long organizationId;

    private UserOrganization() {
    }

    public UserOrganization(Long organizationId) {
        this.organizationId = organizationId;
    }
}
