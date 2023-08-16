package com.ekin.system.menu;

import com.cartisan.converter.OnOffStatusConverter;
import com.cartisan.domain.AbstractEntity;
import com.cartisan.domain.AggregateRoot;
import com.cartisan.dp.OnOffStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_menu_rules")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Menu extends AbstractEntity implements AggregateRoot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "component")
    private String component;

    @Column(name = "icon")
    private String icon;

    @Column(name = "menu_type")
    private String menuType;

    @Column(name = "url")
    private String url;

    @Column(name = "keepalive")
    private OnOffStatus keepalive;

    @Column(name = "remark")
    private String remark;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "status")
    @Convert(converter = OnOffStatusConverter.class)
    private OnOffStatus status;

    protected Menu() {
    }
}
