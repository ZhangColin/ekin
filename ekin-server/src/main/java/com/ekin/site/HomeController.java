package com.ekin.site;


import com.cartisan.response.GenericResponse;
import com.ekin.system.menurule.MenuRuleAppService;
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

    private final MenuRuleAppService menuRuleAppService;
    private ObjectMapper objectMapper;

    public HomeController(MenuRuleAppService menuRuleAppService) {
        this.menuRuleAppService = menuRuleAppService;
    }

    @ApiOperation(value = "获取当前用户站点相关配置")
    @GetMapping("/config")
    public GenericResponse<Map<String, Object>> findAllNormalUsers() throws JsonProcessingException {
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> siteConfig = new HashMap<>();

        siteConfig.put("siteName", "Cartisan");
        siteConfig.put("version", "1.0.0");

        data.put("menus", menuRuleAppService.getMenuRuleTreeList());
        data.put("siteConfig", siteConfig);

        return success(data);
    }
}
