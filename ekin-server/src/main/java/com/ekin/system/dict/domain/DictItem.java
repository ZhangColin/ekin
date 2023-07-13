package com.ekin.system.dict.domain;

import com.cartisan.domain.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_dict_items")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DictItem extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    @Setter
    private String label;

    @EqualsAndHashCode.Include
    @Column(name = "value")
    private String value;

    @Column(name = "sort")
    @Setter
    private Integer sort;

    protected DictItem() {

    }

    public DictItem(String label, String value) {
        this(label, value, 0);
    }

    public DictItem(@NonNull String label, @NonNull String value, @NonNull Integer sort) {
        this.label = label;
        this.value = value;
        this.sort = sort;
    }
}
