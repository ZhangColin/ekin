package com.ekin.system.common;

import com.cartisan.aop.limit.Limit;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/expiration")
@Api(tags = "系统：redis键失效通知")
@Slf4j
public class RedisKeyExpirationController {
    @Autowired
    private ValueOperations<String, String> valueOperations;

    @GetMapping
    public String testLimit() {
        valueOperations.set("expiration", "colin", 1, TimeUnit.MINUTES);

        return "success";
    }
}
