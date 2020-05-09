package com.ekin.system.common;

import com.cartisan.utils.RedisDistributedLock;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@RestController
@RequestMapping("/distributed")
@Api(tags = "分布式锁")
@Slf4j
public class DistributedLockController {
    private final RedisDistributedLock distributedLock;
    private long stocks = 10;
//    private int timeout = 30 * 1000;
    private int timeout = 500;
    private String key = "lockKey";

    public DistributedLockController(RedisDistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }

    @GetMapping("/lockDemo")
    public ResponseEntity<?> lockDemo() {
        final ArrayList<String> users = new ArrayList<>(100000);
        final ArrayList<String> shopUsers = new ArrayList<>(10);

        IntStream.range(0, 100000).parallel().forEach(index -> users.add("User-" + index));

        stocks = 10;

        users.parallelStream().forEach(user -> {
            final String result = qiang(user);
            if (!StringUtils.isEmpty(result)) {
                shopUsers.add(result);
            }
        });

        return success(shopUsers);
    }

    private String qiang(String user) {
        final long startTime = System.currentTimeMillis();

        while ((startTime + timeout) >= System.currentTimeMillis()) {
            if (stocks <= 0) {
                break;
            }

            if (distributedLock.tryGet(key, user, 1000)) {
                log.info("用户[{}]拿到锁...", user);
                try {
                    if (stocks <= 0) {
                        break;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    stocks -= 1;

                    log.info("用户[{}]抢单成功，剩余库存[{}]", user, stocks);
                    return user + "抢单成功，剩余库存" + stocks;
                } finally {
                    log.info("用户[{}]释放锁...", user);
                    distributedLock.release(key, user);
                }
            }
        }

        return "";
    }
}
