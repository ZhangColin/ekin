package com.ekin.systemcontext.config;

import com.cartisan.common.repositories.CartisanRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author colin
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.ekin"},
        repositoryFactoryBeanClass = CartisanRepositoryFactoryBean.class)
//@EntityScan(basePackages = {"com.ekin"})
public class SystemConfig {
}
