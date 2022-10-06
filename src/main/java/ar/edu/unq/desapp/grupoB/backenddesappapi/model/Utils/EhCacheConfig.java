package ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;


@Configuration
public class EhCacheConfig {

    @Bean
    public CacheManager EhcacheManager() {

        CacheConfiguration<String, Cotization> cachecConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class,
                        Cotization.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB) //Memoria utilizada
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(60))) //Tiempo de caducidad que tiene la cache
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = (CacheManager) cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<String, Cotization> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cachecConfig);
        ((javax.cache.CacheManager) cacheManager).createCache("cryptoCotization", configuration);
        return cacheManager;
    }
}
