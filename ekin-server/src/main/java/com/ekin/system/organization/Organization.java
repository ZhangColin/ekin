package com.ekin.system.organization;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_organizations")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Organization extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "enabled")
    private Boolean enabled;

    private Organization() {

    }

    public Organization(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = "";
        this.type = 0;
        this.description = "";
        this.sort = 0;
        this.enabled = true;
    }

    public void changeOrganization(Long parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }

    public void describe(String description, Integer sort) {
        this.description = description;
        this.sort = sort;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
