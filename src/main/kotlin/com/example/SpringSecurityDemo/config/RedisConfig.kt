package com.example.SpringSecurityDemo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer


/**
 * 参考https://www.jianshu.com/p/90618fd8bc83
 */
@Configuration
class RedisConfig {
    //还是使用springboot默认配置的RedisConnectionFactory
    @Autowired
    private lateinit var redisConnectionFactory: RedisConnectionFactory

    // 默认用的是用JdkSerializationRedisSerializer进行序列化的
    @Bean
    fun redisTemplate(): RedisTemplate<Any, Any> {
        val redisTemplate = RedisTemplate<Any, Any>()
        // 注入数据源
        redisTemplate.connectionFactory = redisConnectionFactory


        // 使用Jackson2JsonRedisSerialize 替换默认序列化
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        val stringRedisSerializer = StringRedisSerializer()
        // key-value结构序列化数据结构
        redisTemplate.keySerializer = stringRedisSerializer
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
//        redisTemplate.hashKeySerializer = stringRedisSerializer

        //        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 启用默认序列化方式
//        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

        /// redisTemplate.afterPropertiesSet(

        return redisTemplate

    }

}


///**
// * 参考https://www.jianshu.com/p/90618fd8bc83
// */
//@Configuration
//class RedisConfig {
//
//
//    // 默认用的是用JdkSerializationRedisSerializer进行序列化的
//    @Bean
//    fun redisTemplate(redisConnectionFactory: LettuceConnectionFactory): RedisTemplate<Any, Any> {
////        redisConnectionFactory.setPassword("p123456")
//        val redisTemplate = RedisTemplate<Any, Any>()
//        // 注入数据源
//        redisTemplate.connectionFactory = redisConnectionFactory
//
//
//        // 使用Jackson2JsonRedisSerialize 替换默认序列化
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        val stringRedisSerializer = StringRedisSerializer()
//        // key-value结构序列化数据结构
//        redisTemplate.keySerializer = stringRedisSerializer
////        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        // hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
////        redisTemplate.hashKeySerializer = stringRedisSerializer
//
//        //        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        // 启用默认序列化方式
////        redisTemplate.setEnableDefaultSerializer(true);
////        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
//
//        /// redisTemplate.afterPropertiesSet(
//
//        return redisTemplate
//
//    }
//
//}