package com.ekin.system.role.domain;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Column(name = "name")
    private String name;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "role_id")
    private List<RoleMenu> menus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "role_id")
    private List<RoleResource> resources = new ArrayList<>();

    private Role() {
    }

    public Role(String name, String description, Integer sort) {
        this.name = name;
        this.description = description;
        this.sort = sort;
        this.status = 1;
    }

    public void change(String name, String description, Integer sort){
        this.name = name;
        this.description = description;
        this.sort = sort;
    }

    public void assignMenus(List<Long> menuIds) {
        this.menus.removeAll(this.menus.stream()
                .filter(role->!menuIds.contains(role.getMenuId()))
                .collect(toList()));

        menuIds.removeAll(this.menus.stream().map(RoleMenu::getMenuId).collect(toList()));
        menuIds.forEach(menuId->this.menus.add(new RoleMenu(menuId)));
    }

    public void assignResources(List<Long> resourceIds) {
        this.resources.removeAll(this.resources.stream()
                .filter(role->!resourceIds.contains(role.getResourceId()))
                .collect(toList()));

        resourceIds.removeAll(this.resources.stream().map(RoleResource::getResourceId).collect(toList()));
        resourceIds.forEach(resourceId->this.resources.add(new RoleResource(resourceId)));
    }

    public void enable() {
        this.status = 1;
    }

    public void disable() {
        this.status = 0;
    }
}
