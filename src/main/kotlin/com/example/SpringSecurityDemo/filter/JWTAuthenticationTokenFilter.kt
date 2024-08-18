package com.example.SpringSecurityDemo.filter

import com.example.SpringSecurityDemo.security.LoginUser
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import parseClaim


@Component
class JWTAuthenticationTokenFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<Any, Any>
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        //获取token
        val token: String? = request.getHeader("token")

        if (token == null) {
            // 没有token，直接放行，因为有些资源不需要认证即可访问
            filterChain.doFilter(request, response)
            return
        }

        // 解析token
        //此方法会有异常，必需捕捉，再以RequestRejectedException抛出，给security才会处理异常
        // userId有可能为null
        val userId = try {
            parseClaim(token).payload["user_id"]?.toString()
        } catch (e: Exception) {
            log.warn("token校验失败，${e.message}")
            // RequestRejectedException，此处必需使用此异常，其它异常，security不会处理
            throw RequestRejectedException("token校验失败")
        }

        if (userId == null) {
            log.warn("token认证失败，因为user id是 null")
            // RequestRejectedException，此处必需使用此异常，其它异常，security不会处理
            throw RequestRejectedException("用户user id是null")
        }


        //从redis获取用户信息
        //不用管userId=null的情况，redisKey的会变成login_user_id_null
        val redisKey = "login_user_id_$userId"


        // UserData loginUser = (UserData) redisTemplate.boundValueOps(redisKey).get();
        val loginUser = redisTemplate.boundValueOps(redisKey).get() as LoginUser?
        if (loginUser == null) {
            log.warn("token认证失败，因为redis中user id:$userId 的记录不存在，记录可能丢失或过期")
            // RequestRejectedException，此处必需使用此异常，其它异常，security不会处理
            throw RequestRejectedException("用户user id:$userId,在redis中的记录不存在")
        }


        //存入SecurityContextHolder
        val authentication = UsernamePasswordAuthenticationToken(
            loginUser, null,
            loginUser.authorities
        )
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}