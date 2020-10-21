package org.springframework.samples.rewardpoint.system;

import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Cache could be disabled in unit test.
 */
@org.springframework.context.annotation.Configuration
@EnableCaching
@Profile("production")
class CacheConfig {

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            Configuration<Object, Object> cacheConfiguration = createCacheConfiguration();
            cm.createCache("owners", cacheConfiguration);
        };
    }

    private Configuration<Object, Object> createCacheConfiguration() {
        return new MutableConfiguration<>().setStatisticsEnabled(true);
    }
}
