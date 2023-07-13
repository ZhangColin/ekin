package com.ekin.system.dict;

import com.cartisan.dto.PageResult;
import com.ekin.system.dict.request.DictItemParam;
import com.ekin.system.dict.request.DictParam;
import com.ekin.system.dict.request.DictQuery;
import com.ekin.system.dict.response.DictDto;
import com.ekin.system.dict.response.DictItemDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.response.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：数据字典")
@RestController
@RequestMapping("/system/dicts")
@Validated
@Slf4j
public class DictController {
    private final DictAppService service;

    public DictController(DictAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索字典")
    @GetMapping("/search")
    public ResponseEntity<PageResult<DictDto>> searchDicts(
            DictQuery dictQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchDicts(dictQuery, pageable));
    }

    @ApiOperation(value = "获取指定Code的字典项")
    @GetMapping("/{dictCode}/items")
    public ResponseEntity<List<DictItemDto>> getItems(@ApiParam(value = "字典Code", required = true) @PathVariable String dictCode){
        return success(service.getDictItems(dictCode));
    }

    @ApiOperation(value = "添加字典")
    @PostMapping
    public ResponseEntity<?> addDict(
            @ApiParam(value = "字典信息", required = true) @RequestBody DictParam dictParam) {
        service.addDict(dictParam);

        return success();
    }

    @ApiOperation(value = "更新字典")
    @PutMapping("/{id}")
    public ResponseEntity<?> editDict(
            @ApiParam(value = "字典Id", required = true) @PathVariable Long id,
            @ApiParam(value = "字典信息", required = true) @RequestBody DictParam dictParam) {
        service.editDict(id, dictParam);

        return success();
    }

    @ApiOperation(value = "删除字典")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDict(
            @ApiParam(value = "字典Id", required = true) @PathVariable long id) {
        service.removeDict(id);

        return success();
    }

    @ApiOperation(value = "提交字典项")
    @PutMapping("/{dictCode}/items")
    public ResponseEntity<?> submitDictItem(
            @ApiParam(value = "字典Code", required = true) @PathVariable String dictCode,
            @ApiParam(value = "字典项信息", required = true) @RequestBody DictItemParam dictItemParam){
        service.submitDictItem(dictCode, dictItemParam);
        return success();
    }

    @ApiOperation(value = "移除字典项")
    @DeleteMapping("/{dictCode}/items")
    public ResponseEntity<?> removeDictItem(
            @ApiParam(value = "字典Code", required = true) @PathVariable String dictCode,
            @ApiParam(value = "字典项信息", required = true) @RequestBody DictItemParam dictItemParam) {
        service.removeDictItem(dictCode, dictItemParam);

        return success();
    }
}
