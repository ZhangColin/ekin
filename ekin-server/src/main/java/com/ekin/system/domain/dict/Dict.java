package com.ekin.system.domain.dict;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
    @Setter
    private String name;

    @Column(name = "code")
    @Setter
    private String code;

    @Column(name = "description")
    @Setter
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dict_id", nullable = false)
    private List<DictItem> dictItems = new ArrayList<>();

    protected Dict() {
    }

    public Dict(@NonNull String name, @NonNull String code) {
        this.name = name;
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
