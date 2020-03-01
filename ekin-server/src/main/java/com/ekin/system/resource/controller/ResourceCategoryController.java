package com.ekin.system.resource.controller;

import com.ekin.system.resource.application.ResourceCategoryAppService;
import com.ekin.system.resource.request.ResourceCategoryParam;
import com.ekin.system.resource.response.ResourceCategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：资源分类")
@RestController
@RequestMapping("/system/resources/categories")
public class ResourceCategoryController {
    private final ResourceCategoryAppService service;

    public ResourceCategoryController(ResourceCategoryAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "获取所有资源分类")
    @GetMapping
    public ResponseEntity<List<ResourceCategoryDto>> getAllResourceCategories(){
        return success(service.getAllResourceCategories());
    }

    @ApiOperation(value = "添加资源分类")
    @PostMapping
    public ResponseEntity<ResourceCategoryDto> addResourceCategory(
            @ApiParam(value = "资源分类信息", required = true) @Validated @RequestBody ResourceCategoryParam resourceCategoryParam) {
        return success(service.addResourceCategory(resourceCategoryParam));
    }

    @ApiOperation(value = "编辑资源分类")
    @PutMapping("/{id}")
    public ResponseEntity<ResourceCategoryDto> editResourceCategory(
            @ApiParam(value = "资源分类Id", required = true) @PathVariable Long id,
            @ApiParam(value = "资源分类信息", required = true) @Validated @RequestBody ResourceCategoryParam resourceCategoryParam) {
        return success(service.editResourceCategory(id, resourceCategoryParam));
    }

    @ApiOperation(value = "删除资源分类")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeResourceCategory(
            @ApiParam(value = "资源分类Id", required = true) @PathVariable long id) {
        service.removeResourceCategory(id);
        return success();
    }
}
