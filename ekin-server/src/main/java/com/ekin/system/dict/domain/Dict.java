package com.ekin.system.dict.domain;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_dicts")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Dict extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dict_id", nullable = false)
    private List<DictItem> dictItems = new ArrayList<>();

    protected Dict() {
    }

    public Dict(@NonNull String code) {
        this.code = code;

        this.name = "";
        this.description = "";
    }

    public void describe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void changeCode(String code) {
        this.code = code;
    }


    public void submitItem(String label, String value, int sort) {
        final DictItem dictItem = new DictItem(label, value);
        dictItem.setLabel(label);
        dictItem.setSort(sort);

        dictItems.remove(dictItem);
        dictItems.add(dictItem);
    }

    public void removeItem(String value) {
        dictItems.remove(new DictItem("", value));
    }
}
