package com.ekin.system.dtos;

import com.ekin.system.domains.Department;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@Getter
@AllArgsConstructor
public class DepartmentDto {
    private String id;

    private String name;
    private String parentId;
    private String description;
    private Integer sort;


    @Setter
    @JsonProperty("children")
    private List<DepartmentDto> childDepartments;

    public static List<DepartmentDto> buildDepartmentTreeList(List<Department> departments) {
        Multimap<Long, Department> departmentMap = ArrayListMultimap.create();
        departments.forEach(department -> departmentMap.put(department.getParentId(), department));

        return buildDepartmentTreeList(0L, departmentMap);
    }

    private static List<DepartmentDto> buildDepartmentTreeList(Long parentId, Multimap<Long, Department> departmentMap) {
        return departmentMap.get(parentId).stream().map(department -> new DepartmentDto(
                department.getId().toString(), department.getName(), department.getParentId().toString(),
                department.getDescription(), department.getSort(),
                buildDepartmentTreeList(department.getId(), departmentMap))).collect(Collectors.toList());
    }
}
