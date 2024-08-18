package com.example.SpringSecurityDemo.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

//    @PreAuthorize("hasAuthority('system:dept:list')")
@PreAuthorize("@exp.hasAuthority('system:dept:list')")
    @GetMapping("/hello")
    fun hello()="hello"


}