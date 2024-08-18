package com.example.SpringSecurityDemo.config

import com.example.SpringSecurityDemo.filter.JWTAuthenticationTokenFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import responseWriter


//@EnableWebSecurity
@Configuration
@EnableMethodSecurity
class SecurityConfiguration {
    //    @Bean
//    fun userDetailsService(): UserDetailsService {
//        return ShopmeUserDetailsService();
//    }
    @Autowired
    lateinit var jwtAuthenticationTokenFilter: JWTAuthenticationTokenFilter

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration) = config.authenticationManager


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        关闭csrf
        http.csrf { it -> it.disable() }
        http.sessionManagement { it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//        http.sessionManagement { it->it.disable() }
        // 配置请求拦截方式
        http.authorizeHttpRequests {
            it.requestMatchers("/user/login").anonymous()
//                .requestMatchers("/hello").hasAnyAuthority("system:dept:list")
                .anyRequest().authenticated()
        }
        http.addFilterAt(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.exceptionHandling { it ->
            it.authenticationEntryPoint(AuthenticationEntryPointImp())
            it.accessDeniedHandler(AccessDeniedHandlerImp())
        }

        http.cors { it.configurationSource(corsConfigurationSource()) }

//                 http.authorizeRequests().antMatchers("/login").permitAll()
//                         .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
//                         .hasAnyAuthority("Admin", "Editor", "Salesperson")
//                         .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
//                         .anyRequest().authenticated()
//                         .and().formLogin()
//                         .loginPage("/login")
//                             .usernameParameter("email")
//                             .permitAll()
//                         .and()
//                         .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
//                         .and()
//                         .logout().permitAll();
//
//                 http.headers().frameOptions().sameOrigin();

        return http.build()
    }


    //    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}


//@Bean
//fun webSecurityCustomizer(): WebSecurityCustomizer {
//    return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//}


class AuthenticationEntryPointImp : AuthenticationEntryPoint {
    private val log = LoggerFactory.getLogger(this.javaClass)
    override fun commence(
        request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException
    ) {
        log.warn("出现认证异常:${authException.message}")
        responseWriter(request, response, HttpStatus.UNAUTHORIZED, "认证失败", authException.message ?: "")
//        val result = ResponseResult(HttpStatus.FORBIDDEN.value(),"认证失败", authException.message ?: "")
//        val jsonObject = jacksonObjectMapper()
//        val json = jsonObject.writeValueAsString(result )
//        response.contentType = "application/json"
//        response.characterEncoding = "UTF-8"
//        response.status = HttpStatus.FORBIDDEN.value()
//        response.writer.println(json)
    }
}


class AccessDeniedHandlerImp : AccessDeniedHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun handle(
        request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException
    ) {

        log.warn("出现权限异常:${accessDeniedException.message}")
        responseWriter(request, response, HttpStatus.FORBIDDEN, "没有访问权限", accessDeniedException.message ?: "")

//        val result = ResponseResult(HttpStatus.UNAUTHORIZED.value(), "没有访问权限", accessDeniedException.message ?: "")
//        val jsonObject = jacksonObjectMapper()
//        val json = jsonObject.writeValueAsString(result)
//        response.contentType = "application/json"
//        response.characterEncoding = "UTF-8"
//        response.status = HttpStatus.UNAUTHORIZED.value()
//        response.writer.println(json)

    }
}