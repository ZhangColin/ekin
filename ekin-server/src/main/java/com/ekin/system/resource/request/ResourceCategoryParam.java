package com.ekin.system.resource.request;

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
public class ResourceCategoryParam {
    @ApiModelProperty(value = "资源分类名称", required = true)
    @NotBlank(message = "资源分类名称不能为空")
    @Length(min = 2, max = 32, message = "资源分类名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "资源分类排序", required = true)
    @NotNull(message = "资源分类排序不能为空")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;
}
