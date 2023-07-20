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
//import static com.cartisan.response.ResponseUtil.success;
//
//@RestController
//@ApiVersion(3)
//@RequestMapping("/{version}/versions")
//@Api(tags = "版本：测试版本")
//@Slf4j
//public class VersionExtController {
//
//    @GetMapping
//    public GenericResponse<?> version3() {
//        Map<String, String> result = new HashMap<>();
//        result.put("data", "测试版本3");
//        return success(result);
//    }
//}
//
