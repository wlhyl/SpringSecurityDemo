package com.example.SpringSecurityDemo.services

import com.example.SpringSecurityDemo.requesst.UserRequest
import com.example.SpringSecurityDemo.responser.TokenResponser
import com.example.SpringSecurityDemo.security.LoginUser
import genAccessToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.BoundValueOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit


@Service
class LoginService {
    @Autowired
    private lateinit var authencationManager: AuthenticationManager

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<Any, Any>

    fun login(user: UserRequest): TokenResponser {
        val authentication = UsernamePasswordAuthenticationToken(user.name, user.password)
        val authenticate =
            authencationManager.authenticate(authentication) ?: throw BadCredentialsException("用户名或密码错误")

        // 取出userDetials，并重新构造取出userDetials，password为""
        val loginUser = (authenticate.principal as LoginUser).let { LoginUser(it.id, it.username, "", it.authorities) }

        // 生成jwt
        val jwt = genAccessToken(loginUser.id)

        val cache: BoundValueOperations<Any, Any> = redisTemplate.boundValueOps("login_user_id_${loginUser.id}")
        cache.set(loginUser)
        cache.expire(1, TimeUnit.DAYS)

        return TokenResponser(jwt)


    }

    fun logout(): HashMap<String, String> {
        val user = SecurityContextHolder.getContext().authentication.principal as LoginUser
        redisTemplate.delete("login_user_id_${user.id}")
        val map = HashMap<String, String>()
        map["message"] = "注销成功"
        return map
    }
}