//package com.ekin.system.organization;
//
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import org.hibernate.validator.constraints.Length;
//
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotBlank;
//
///**
// * @author colin
// */
//@Data
//public class OrganizationParam {
//    @ApiModelProperty(value = "组织名称", required = true)
//    @NotBlank(message = "组织名称不能为空")
//    @Length(min = 2, max = 32, message = "组织名称必须在 2 至 32 之间")
//    private String name;
//
//    @ApiModelProperty(value = "上级组织")
//    private Long parentId;
//
//    @ApiModelProperty(value = "描述")
//    @Length(max = 255, message = "描述长度需要在 255 字以内")
//    private String description;
//
//    @ApiModelProperty(value = "组织排序")
//    @Min(value = 0, message = "排序最小为0")
//    private Integer sort;
//}
