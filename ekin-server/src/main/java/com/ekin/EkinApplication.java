package com.ekin;

import com.cartisan.utils.SnowflakeIdWorker;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PreDestroy;

/**
 * @author colin
 */
@SpringBootApplication(scanBasePackages = {"com.cartisan", "com.ekin"})
@MapperScan("com.ekin.**.mapper")
@Slf4j
//@EnableHasor
//@EnableHasorWeb
public class EkinApplication {
    public static void main(String[] args) {
        /* 在指定的目录下生成应用pid，执行下面的命令关闭应用 :
            'cat /Users/colin/app.pid | xargs kill' */
//        SpringApplication application = new SpringApplication(EkinApplication.class);
//        application.addListeners(new ApplicationPidFileWriter("/Users/colin/ekin.pid"));
//        application.run(args);

        SpringApplication.run(EkinApplication.class, args);
    }

    @Bean
    public SnowflakeIdWorker idWorker() {
        return new SnowflakeIdWorker(1, 1);
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("Ekin application shutdown");
    }
}
