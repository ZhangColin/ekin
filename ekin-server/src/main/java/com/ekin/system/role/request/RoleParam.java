package com.ekin.system.role.request;

import com.cartisan.dp.OnOffStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author colin
 */
@Data
public class RoleParam {
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 32, message = "角色名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "上级角色")
    private Long parentId;

    @ApiModelProperty(value = "权限规则")
    private List<String> rules = new ArrayList<>();

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private OnOffStatus status;
}
