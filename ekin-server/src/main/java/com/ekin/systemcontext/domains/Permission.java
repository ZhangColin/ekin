package com.ekin.systemcontext.domains;

import com.cartisan.common.domains.AbstractEntity;
import com.cartisan.common.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_permissions")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Permission extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    @Setter
    private Long parentId;
    @Column(name = "name")
    @Setter
    private String name;
    @Column(name = "code")
    @Setter
    private String code;
    @Column(name = "description")
    @Setter
    private String description;
    @Column(name = "sort")
    @Setter
    private Integer sort;

    private Permission() {
    }

    public Permission(Long id, Long parentId, String name, String code) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
        this.description = "";
        this.sort = 0;
    }
}
