//package com.ekin.system.common;
//
//import com.cartisan.controllers.ApiVersion;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.cartisan.responses.ResponseUtil.success;
//
//@RestController
//@ApiVersion
//@RequestMapping("/{version}/versions")
//@Api(tags = "版本：测试版本")
//@Slf4j
//public class VersionController {
//
//    @GetMapping
//    public ResponseEntity<?> version1() {
//        Map<String, String> result = new HashMap<>();
//        result.put("data", "测试版本1");
//        return success(result);
//    }
//
//    @GetMapping
//    @ApiVersion(2)
//    public ResponseEntity<?> version2() {
//        Map<String, String> result = new HashMap<>();
//        result.put("data", "测试版本2");
//        return success(result);
//    }
//}
//
