package com.ekin.system.resource.controller;

import com.cartisan.dto.PageResult;
import com.cartisan.response.GenericResponse;
import com.ekin.system.resource.application.ResourceAppService;
import com.ekin.system.resource.request.ResourceParam;
import com.ekin.system.resource.request.ResourceQuery;
import com.ekin.system.resource.response.ResourceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.response.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：资源")
@RestController
@RequestMapping("/system/resources")
@Validated
@Slf4j
public class ResourceController {
    private final ResourceAppService service;

    public ResourceController(ResourceAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索资源")
    @GetMapping("/search")
    public GenericResponse<PageResult<ResourceDto>> searchUsers(
            @ApiParam(value = "查询参数") ResourceQuery resourceQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchResources(resourceQuery, pageable));
    }

    @ApiOperation(value = "获取所有资源")
    @GetMapping
    public GenericResponse<List<ResourceDto>> getAllResources(){
        return success(service.getAllResources());
    }

    @ApiOperation(value = "添加资源")
    @PostMapping
    public GenericResponse<ResourceDto> addResource(
            @ApiParam(value = "资源信息", required = true) @Validated @RequestBody ResourceParam resourceParam) {
        return success(service.addResource(resourceParam));
    }

    @ApiOperation(value = "编辑资源")
    @PutMapping("/{id}")
    public GenericResponse<ResourceDto> editResource(
            @ApiParam(value = "资源Id", required = true) @PathVariable Long id,
            @ApiParam(value = "资源信息", required = true) @Validated @RequestBody ResourceParam resourceParam) {
        return success(service.editResource(id, resourceParam));
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("/{id}")
    public GenericResponse<?> removeResource(
            @ApiParam(value = "资源Id", required = true) @PathVariable long id) {
        service.removeResource(id);
        return success();
    }
}
