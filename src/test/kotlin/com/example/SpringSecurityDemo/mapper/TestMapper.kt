package com.example.SpringSecurityDemo.mapper

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestMapper {
    @Autowired
    private lateinit var menuMapper: MenuMapper

    @Test
    fun testMenuMapper(){
        val perms = menuMapper.selectPermsByUserId(1U)
       println("========")
        perms.forEach { println(it) }
    }
}