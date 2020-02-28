package com.ekin.system.resource.request;

import com.ekin.system.resource.validator.ResourceCategoryExists;
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
public class ResourceParam {
    @ApiModelProperty(value = "资源名称", required = true)
    @NotBlank(message = "资源名称不能为空")
    @Length(min = 2, max = 32, message = "资源名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "资源分类", required = true)
    @ResourceCategoryExists
    private Long categoryId;

    @ApiModelProperty(value = "权限编码", required = true)
    @NotBlank(message = "权限编码不能为空")
    private String code;

    @ApiModelProperty(value = "Url")
    private String url;

    @ApiModelProperty(value = "资源描述")
    private String description;

    @ApiModelProperty(value = "资源排序")
    @NotNull(message = "资源排序不能为空")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;
}
