package com.ekin;

import com.cartisan.utils.SnowflakeIdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author colin
 */
@SpringBootApplication(scanBasePackages = {"com.cartisan", "com.ekin"})
@MapperScan("com.ekin.**.mapper")
public class EkinApplication {
    public static void main(String[] args) {
        //'cat /Users/huangqingshi/app.pid | xargs kill' */
//        SpringApplication application = new SpringApplication(EkinApplication.class);
//        application.addListeners(new ApplicationPidFileWriter("/Users/huangqingshi/app.pid"));
//        application.run();

        SpringApplication.run(EkinApplication.class, args);
    }

    @Bean
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(1, 1);
    }
}
