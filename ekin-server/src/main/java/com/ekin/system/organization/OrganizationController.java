package com.ekin.system.organization;

import com.cartisan.dtos.TreeNode;
import com.ekin.system.organization.OrganizationDto;
import com.ekin.system.organization.OrganizationParam;
import com.ekin.system.organization.mapper.OrganizationQueryMapper;
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
@Api(tags = "系统管理：组织")
@RestController
@RequestMapping("/system/organizations")
public class OrganizationController {
    private final OrganizationAppService service;

    public OrganizationController(OrganizationAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "获取所有组织")
    @GetMapping
    public ResponseEntity<List<OrganizationDto>> getOrganizationTreeList() {
        return success(service.getOrganizationTreeList());
    }

    @ApiOperation(value = "添加组织")
    @PostMapping
    public ResponseEntity<OrganizationDto> addOrganization(
            @ApiParam(value = "组织信息", required = true) @Validated @RequestBody OrganizationParam organizationParam) {
        return success(service.addOrganization(organizationParam));
    }

    @ApiOperation(value = "编辑组织")
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDto> editOrganization(
            @ApiParam(value = "组织Id", required = true) @PathVariable Long id,
            @ApiParam(value = "组织信息", required = true) @Validated @RequestBody OrganizationParam organizationParam) {
        return success(service.editOrganization(id, organizationParam));
    }

    @ApiOperation(value = "删除组织")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOrganization(
            @ApiParam(value = "组织Id", required = true) @PathVariable long id) {
        service.removeOrganization(id);
        return success();
    }
}
