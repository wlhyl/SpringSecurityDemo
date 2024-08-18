package com.example.SpringSecurityDemo.security

import com.example.SpringSecurityDemo.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder

class LoginUser(
    val id: UInt,
    private val username: String,
    private val password: String,
    private val authorities: List<GrantedAuthority>
) : UserDetails {


    override fun getAuthorities(): List<GrantedAuthority> = authorities


    constructor(user: User, authorities: List<GrantedAuthority>) : this(user.id, user.name, user.password, authorities) {}

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }
}