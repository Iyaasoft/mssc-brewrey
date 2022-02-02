package guru.springframework.msscbrewrey.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
     //   JCacheCacheManager jcacheManager = new JCacheCacheManager();
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<Cache>();
        caches.add(new ConcurrentMapCache("beerCache"));
        caches.add(new ConcurrentMapCache("beerListCache"));
        caches.add(new ConcurrentMapCache("customerCache"));
        cacheManager.setCaches( caches);
        return cacheManager;
    }
}
