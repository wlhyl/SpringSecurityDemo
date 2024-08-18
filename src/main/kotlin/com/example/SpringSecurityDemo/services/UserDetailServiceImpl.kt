package com.example.SpringSecurityDemo.services

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.example.SpringSecurityDemo.entity.User
import com.example.SpringSecurityDemo.mapper.MenuMapper
import com.example.SpringSecurityDemo.mapper.UserMapper
import com.example.SpringSecurityDemo.security.LoginUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl : UserDetailsService {
    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var menuMapper: MenuMapper
    override fun loadUserByUsername(username: String): UserDetails {
//        TODO("Not yet implemented")
//        val queryWrapper = LambdaQueryWrapper<User>()
//        queryWrapper.eq(User::name, username)
        val queryWrapper = QueryWrapper<User>()
        queryWrapper.eq("name", username)
        val user = userMapper.selectOne(queryWrapper) ?: throw UsernameNotFoundException("用户名不存在")
//        val premissions = listOf("test", "admin")
        val premissions = menuMapper.selectPermsByUserId(user.id)
        val authorities = premissions.map { SimpleGrantedAuthority(it) }
        // 将数据封闭成userDetails
        return LoginUser(user, authorities)
    }
}