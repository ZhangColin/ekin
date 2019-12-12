package com.ekin.system.controllers;

import com.cartisan.dtos.PageResult;
import com.cartisan.responses.GenericResponse;
import com.ekin.system.services.dict.DictAppService;
import com.ekin.system.services.dict.condition.DictCondition;
import com.ekin.system.services.dict.dto.DictDTO;
import com.ekin.system.services.dict.dto.DictItemDTO;
import com.ekin.system.services.dict.param.DictItemParam;
import com.ekin.system.services.dict.param.DictParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.GenericResponse.success;

/**
 * @author colin
 */
@Api(tags = "DictController")
@RestController
@RequestMapping("/system/dicts")
public class DictController {
    private final DictAppService service;

    @Autowired
    public DictController(DictAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索字典")
    @GetMapping
    public GenericResponse<PageResult<DictDTO>> searchDicts(
            DictCondition condition,
            @PageableDefault Pageable pageable) {
        return success(service.searchDicts(condition, pageable));
    }

    @ApiOperation(value = "获取指定Code的字典项")
    @GetMapping("/{dictCode}/items")
    public GenericResponse<List<DictItemDTO>> getItems(@ApiParam(value = "字典Code", required = true) @PathVariable String dictCode){
        return success(service.getDictItems(dictCode));
    }

    @ApiOperation(value = "添加字典")
    @PostMapping
    public GenericResponse<?> addDict(
            @ApiParam(value = "字典信息", required = true) @Validated @RequestBody DictParam dictParam) {
        service.addDict(dictParam);

        return success();
    }

    @ApiOperation(value = "更新字典")
    @PutMapping("/{id}")
    public GenericResponse<?> editDict(
            @ApiParam(value = "字典Id", required = true) @PathVariable Long id,
            @ApiParam(value = "字典信息", required = true) @Validated @RequestBody DictParam dictParam) {
        service.updateDict(id, dictParam);

        return success();
    }

    @ApiOperation(value = "删除字典")
    @DeleteMapping("/{id}")
    public GenericResponse<?> removeDict(
            @ApiParam(value = "字典Id", required = true) @PathVariable long id) {
        service.removeDict(id);

        return success();
    }

    @ApiOperation(value = "提交字典项")
    @PutMapping("/{dictCode}/items")
    public GenericResponse<List<String>> submitDictItem(
            @ApiParam(value = "字典Code", required = true) @PathVariable String dictCode,
            @ApiParam(value = "字典项信息", required = true) @Validated @RequestBody DictItemParam dictItemParam){
        service.submitDictItem(dictCode, dictItemParam);
        return success();
    }

    @ApiOperation(value = "移除字典项")
    @DeleteMapping("/{dictCode}/items")
    public GenericResponse<?> removeDictItem(
            @ApiParam(value = "字典Code", required = true) @PathVariable String dictCode,
            @ApiParam(value = "字典项信息", required = true) @Validated @RequestBody DictItemParam dictItemParam) {
        service.removeDictItem(dictCode, dictItemParam);

        return success();
    }
}
