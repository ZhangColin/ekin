package com.ekin.system.role.domain;

import com.cartisan.converter.OnOffStatusConverter;
import com.cartisan.domain.AbstractEntity;
import com.cartisan.domain.AggregateRoot;
import com.cartisan.dp.OnOffStatus;
import com.cartisan.repository.StringListConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_roles")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name = "rules")
    @Convert(converter = StringListConverter.class)
    @Setter
    private List<String> rules;

    @Column(name = "status")
    @Convert(converter = OnOffStatusConverter.class)
    @Setter
    private OnOffStatus status;

    protected Role() {
    }

    public Role(Long parentId, String name) {
        this.parentId = parentId;
        this.name = name;
        this.rules = new ArrayList<>();
        this.status = OnOffStatus.Enabled;
    }
}
