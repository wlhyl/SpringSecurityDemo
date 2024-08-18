package com.example.SpringSecurityDemo.controller

import com.example.SpringSecurityDemo.requesst.UserRequest
import com.example.SpringSecurityDemo.services.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var loginService: LoginService

    @PostMapping("/login")
    fun login(@RequestBody user: UserRequest) = loginService.login(user)

    @GetMapping("/logout")
    fun logout() = loginService.logout()


}