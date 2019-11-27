package com.ekin.systemcontext.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author colin
 */
@Data
public class DepartmentParam {
    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank(message = "部门名称不能为空")
    @Length(min = 2, max = 32, message = "部门名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "上级部门")
    private Long parentId;

    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度需要在 255 字以内")
    private String description;

    @ApiModelProperty(value = "排序字段")
    @NotNull(message = "展示顺序不能为空")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;
}
