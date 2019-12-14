package com.ekin.system.domain.role;

import com.cartisan.domains.AbstractEntity;
import com.cartisan.domains.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "role_id")
    private List<RolePermission> permissions = new ArrayList<>();

    private Role() {
    }

    public Role(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = "";
    }

    public void assignPermissions(List<Long> permissionIds) {
        this.permissions.removeAll(this.permissions.stream()
                .filter(role->!permissionIds.contains(role.getPermissionId()))
                .collect(toList()));

        permissionIds.removeAll(this.permissions.stream().map(RolePermission::getPermissionId).collect(toList()));
        permissionIds.forEach(permissionId->this.permissions.add(new RolePermission(permissionId)));
    }
}
