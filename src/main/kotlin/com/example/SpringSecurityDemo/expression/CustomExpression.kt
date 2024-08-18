package com.example.SpringSecurityDemo.expression

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component("exp")
class CustomExpression {
    fun hasAuthority(authority: String): Boolean {
        // 当前用户权限
        val authentication = SecurityContextHolder.getContext().authentication
        val authorities = authentication.authorities
//     val loginUser =    authentication.principal as LoginUser
        val permissions = authorities.map { it.authority }
        // 判断用户权限集合中是否存在authority
        return permissions.contains(authority)
    }
}