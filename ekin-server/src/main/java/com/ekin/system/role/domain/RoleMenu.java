package com.ekin.system.role.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author colin
 */
@Entity
@Table(name = "sys_role_menus")
@Getter
@EqualsAndHashCode
public class RoleMenu {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_id")
    private Long menuId;

    private RoleMenu() {
    }

    public RoleMenu(Long menuId) {
        this.menuId = menuId;
    }
}
