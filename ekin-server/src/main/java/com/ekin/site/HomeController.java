package com.ekin.site;


import com.cartisan.response.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.cartisan.response.ResponseUtil.success;

@Api(tags = "站点基础信息")
@RestController
@Slf4j
public class HomeController {

    private ObjectMapper objectMapper;

    public HomeController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "获取当前用户站点相关配置")
    @GetMapping("/config")
    public GenericResponse<Map<String, Object>> findAllNormalUsers() throws JsonProcessingException {
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> siteConfig = new HashMap<>();

        siteConfig.put("siteName", "Cartisan");
        siteConfig.put("version", "1.0.0");

        data.put("menus", objectMapper.readTree("[{\"id\":1,\"pid\":0,\"type\":\"menu\",\"title\":\"控制台\",\"name\":\"dashboard/dashboard\",\"path\":\"dashboard\",\"icon\":\"fa fa-dashboard\",\"menu_type\":\"tab\",\"url\":\"\",\"component\":\"/src/views/dashboard.vue\",\"keepalive\":\"dashboard/dashboard\",\"extend\":\"none\"},{\"id\":2,\"pid\":0,\"type\":\"menu_dir\",\"title\":\"系统管理\",\"name\":\"system\",\"path\":\"system\",\"icon\":\"fa fa-group\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\",\"children\":[{\"id\":3,\"pid\":2,\"type\":\"menu\",\"title\":\"角色管理\",\"name\":\"system/role\",\"path\":\"system/role\",\"icon\":\"fa fa-group\",\"menu_type\":\"tab\",\"url\":\"\",\"component\":\"/src/views/system/role/index.vue\",\"keepalive\":\"system/role\",\"extend\":\"none\",\"children\":[{\"id\":4,\"pid\":3,\"type\":\"button\",\"title\":\"查看\",\"name\":\"system/role/index\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":5,\"pid\":3,\"type\":\"button\",\"title\":\"添加\",\"name\":\"system/role/add\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":6,\"pid\":3,\"type\":\"button\",\"title\":\"编辑\",\"name\":\"system/role/edit\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":7,\"pid\":3,\"type\":\"button\",\"title\":\"删除\",\"name\":\"system/role/del\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"}]},{\"id\":8,\"pid\":2,\"type\":\"menu\",\"title\":\"用户管理\",\"name\":\"system/user\",\"path\":\"system/user\",\"icon\":\"el-icon-UserFilled\",\"menu_type\":\"tab\",\"url\":\"\",\"component\":\"/src/views/system/user/index.vue\",\"keepalive\":\"system/user\",\"extend\":\"none\",\"children\":[{\"id\":9,\"pid\":8,\"type\":\"button\",\"title\":\"查看\",\"name\":\"system/user/index\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":10,\"pid\":8,\"type\":\"button\",\"title\":\"添加\",\"name\":\"system/user/add\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":11,\"pid\":8,\"type\":\"button\",\"title\":\"编辑\",\"name\":\"system/user/edit\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":12,\"pid\":8,\"type\":\"button\",\"title\":\"删除\",\"name\":\"system/user/del\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"}]},{\"id\":13,\"pid\":2,\"type\":\"menu\",\"title\":\"菜单管理\",\"name\":\"system/menu\",\"path\":\"system/menu\",\"icon\":\"el-icon-Grid\",\"menu_type\":\"tab\",\"url\":\"\",\"component\":\"/src/views/system/menu/index.vue\",\"keepalive\":\"system/menu\",\"extend\":\"none\",\"children\":[{\"id\":14,\"pid\":13,\"type\":\"button\",\"title\":\"查看\",\"name\":\"system/menu/index\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":15,\"pid\":13,\"type\":\"button\",\"title\":\"添加\",\"name\":\"system/menu/add\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":16,\"pid\":13,\"type\":\"button\",\"title\":\"编辑\",\"name\":\"system/menu/edit\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":17,\"pid\":13,\"type\":\"button\",\"title\":\"删除\",\"name\":\"system/menu/del\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"},{\"id\":18,\"pid\":13,\"type\":\"button\",\"title\":\"快速排序\",\"name\":\"system/menu/sortable\",\"path\":\"\",\"icon\":\"\",\"menu_type\":null,\"url\":\"\",\"component\":\"\",\"keepalive\":0,\"extend\":\"none\"}]}]}]"));
        data.put("siteConfig", siteConfig);

        return success(data);
    }
}
