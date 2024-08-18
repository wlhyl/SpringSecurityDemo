package com.example.SpringSecurityDemo.redis

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@SpringBootTest
class RedisTest {
    @Autowired
    lateinit var redisTemplate: RedisTemplate<Any, Any>

    @Test
    fun redisTest(){
     val redis =    redisTemplate.boundValueOps("test0")
        redis.set("test")
        val v = redis.get()
        println("=========")
        println(v)

    }

    @Test
    fun redisConnect(){
        println("=========")
        val connectionFactory = redisTemplate.connectionFactory as LettuceConnectionFactory
       println(connectionFactory.password)
        println(connectionFactory.hostName)
    }
}