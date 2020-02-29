package com.ekin.system.menu;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_menus")
@Getter
@EqualsAndHashCode(callSuper = true)
public class Menu extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "level")
    private Integer level;

    @Column(name = "sort")
    private Integer sort;

    private Menu() {
    }

    public Menu(Long parentId, String title, String name, String icon, Boolean hidden, Integer level, Integer sort) {
        this.parentId = parentId;
        this.title = title;
        this.name = name;
        this.icon = icon;
        this.hidden = hidden;
        this.level = level;
        this.sort = sort;
    }

    public void change(Long parentId, String title, String name, String icon, Boolean hidden, Integer level, Integer sort) {
        this.parentId = parentId;
        this.title = title;
        this.name = name;
        this.icon = icon;
        this.hidden = hidden;
        this.level = level;
        this.sort = sort;
    }
}
