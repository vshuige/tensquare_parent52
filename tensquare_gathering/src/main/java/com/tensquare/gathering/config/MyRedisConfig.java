package com.tensquare.gathering.config;


import com.tensquare.gathering.pojo.Gathering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;

@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<Object, Gathering> empRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Gathering> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer<Gathering>(Gathering.class);
        template.setDefaultSerializer(serializer);
        return template;
    }



    //自己根据RedisCacheConfiguration类的cacheManager方法写的  有更改，测试过该修改的生成CacheManager生效
    //CacheManagerCustomizers可以来定制缓存的一些规则
    @Primary  //将某个缓存管理器作为默认的(不加的话多个RedisCacheManager时会报错)
    @Bean
    public RedisCacheManager employeeCacheManager(RedisConnectionFactory redisConnectionFactory,
                                                  ResourceLoader resourceLoader){

        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new Jackson2JsonRedisSerializer<Gathering>(Gathering.class)));
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(config);
        return builder.build();
    }



//    private org.springframework.data.redis.cache.RedisCacheConfiguration determineConfiguration(
//            ClassLoader classLoader) {
//        if (this.redisCacheConfiguration != null) {
//            return this.redisCacheConfiguration;
//        }
//        Redis redisProperties = this.cacheProperties.getRedis();
//        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
//                .defaultCacheConfig();
//        config = config.serializeValuesWith(SerializationPair
//                .fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
//        if (redisProperties.getTimeToLive() != null) {
//            config = config.entryTtl(redisProperties.getTimeToLive());
//        }
//        if (redisProperties.getKeyPrefix() != null) {
//            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
//        }
//        if (!redisProperties.isCacheNullValues()) {
//            config = config.disableCachingNullValues();
//        }
//        if (!redisProperties.isUseKeyPrefix()) {
//            config = config.disableKeyPrefix();
//        }
//        return config;
//    }

}
