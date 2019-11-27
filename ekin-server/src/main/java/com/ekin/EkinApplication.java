package com.ekin;

import com.cartisan.common.utils.SnowflakeIdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author colin
 */
@SpringBootApplication(scanBasePackages = {"com.cartisan.common", "com.ekin"})
@MapperScan("com.ekin.systemcontext.queries")
public class EkinApplication {
    public static void main(String[] args) {
        SpringApplication.run(EkinApplication.class);
    }

    @Bean
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(1, 1);
    }
}
