package com.ekin.system.resource.controller;

import com.cartisan.dtos.PageResult;
import com.ekin.system.resource.application.ResourceAppService;
import com.ekin.system.resource.request.ResourceParam;
import com.ekin.system.resource.request.ResourceQuery;
import com.ekin.system.resource.response.ResourceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：资源")
@RestController
@RequestMapping("/system/resources")
public class ResourceController {
    private final ResourceAppService service;

    public ResourceController(ResourceAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索资源")
    @GetMapping("/search")
    public ResponseEntity<PageResult<ResourceDto>> searchUsers(
            @ApiParam(value = "查询参数") ResourceQuery resourceQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchResources(resourceQuery, pageable));
    }

    @ApiOperation(value = "获取所有资源")
    @GetMapping
    public ResponseEntity<List<ResourceDto>> getAllResources(){
        return success(service.getAllResources());
    }

    @ApiOperation(value = "添加资源")
    @PostMapping
    public ResponseEntity<ResourceDto> addResource(
            @ApiParam(value = "资源信息", required = true) @Validated @RequestBody ResourceParam resourceParam) {
        return success(service.addResource(resourceParam));
    }

    @ApiOperation(value = "编辑资源")
    @PutMapping("/{id}")
    public ResponseEntity<ResourceDto> editResource(
            @ApiParam(value = "资源Id", required = true) @PathVariable Long id,
            @ApiParam(value = "资源信息", required = true) @Validated @RequestBody ResourceParam resourceParam) {
        return success(service.editResource(id, resourceParam));
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeResource(
            @ApiParam(value = "资源Id", required = true) @PathVariable long id) {
        service.removeResource(id);
        return success();
    }
}
