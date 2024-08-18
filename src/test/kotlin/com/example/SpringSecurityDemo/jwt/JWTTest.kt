package com.example.SpringSecurityDemo.jwt

import genAccessToken
import org.junit.jupiter.api.Test
import parseClaim


class JWTTest {
    @Test
    fun jwtTest(){
        val token = genAccessToken(10U)
        println("==========")
        println(token)
      val payload =   parseClaim(token).payload
        val userId = payload["user"]
        println(userId)


    }
}