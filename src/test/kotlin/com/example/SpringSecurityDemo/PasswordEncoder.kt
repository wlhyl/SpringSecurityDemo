package com.example.SpringSecurityDemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class PasswordEncoder {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    @Test
    fun testPasswordEncoder(){
        val encode = passwordEncoder.encode("123456")
        println("=============")
        println(encode)
//        $2a$10$0Rv3YECY4Ts6O7BSfGDLZ.WtzbSvFp5Ohrh1ec9acv..Junz3M51G
        val passwd="\$2a\$10\$0Rv3YECY4Ts6O7BSfGDLZ.WtzbSvFp5Ohrh1ec9acv..Junz3M51G"
        val isMatch = passwordEncoder.matches("123456", passwd)
        println(isMatch)

    }
}