package com.ekin.system.controllers;

import com.ekin.system.dtos.DepartmentDto;
import com.ekin.system.dtos.TreeNode;
import com.ekin.system.params.DepartmentParam;
import com.ekin.system.queries.DepartmentQueryMapper;
import com.ekin.system.services.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "DepartmentController")
@RestController
@RequestMapping("/system/departments")
public class DepartmentController {
    private final DepartmentService service;

    private final DepartmentQueryMapper mapper;

    @Autowired
    public DepartmentController(DepartmentService service, DepartmentQueryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @ApiOperation(value = "获取部门列表")
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartmentList() {
        return success(service.getDepartmentList());
    }

    @ApiOperation(value = "获取部门树")
    @GetMapping("/tree")
    public ResponseEntity<List<TreeNode>> getDepartmentTree() {
        return success(TreeNode.buildTree(mapper.getDepartmentTreeNodes()));
    }

    @ApiOperation(value = "添加部门")
    @PostMapping
    public ResponseEntity addDepartment(
            @ApiParam(value = "部门信息", required = true) @Validated @RequestBody DepartmentParam departmentParam) {
        service.addDepartment(departmentParam);

        return success();
    }

    @ApiOperation(value = "更新部门")
    @PutMapping("/{id}")
    public ResponseEntity editDepartment(
            @ApiParam(value = "部门Id", required = true) @PathVariable Long id,
            @ApiParam(value = "部门信息", required = true) @Validated @RequestBody DepartmentParam departmentParam) {
        service.editDepartment(id, departmentParam);

        return success();
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public ResponseEntity removeDepartment(
            @ApiParam(value = "部门Id", required = true) @PathVariable long id) {
        service.removeDepartment(id);

        return success();
    }
}
