package com.example.SpringSecurityDemo

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@MapperScan("com.example.SpringSecurityDemo.mapper")
@SpringBootApplication
class SpringSecurityDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringSecurityDemoApplication>(*args)
//    显示spring security 的filter
//    val run = runApplication<SpringSecurityDemoApplication>(*args)
//    val beans = run.getBean(DefaultSecurityFilterChain::class.java)
//    val filters = beans.filters
//
//	println("=======================")
//	println("spring security filter:")
//    filters.forEach { it -> println(it) }

}
