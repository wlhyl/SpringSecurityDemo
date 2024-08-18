package com.example.SpringSecurityDemo.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

@TableName(value = "sys_user")
data class User(
    @TableId
    val id: UInt,
    val name: String,
    val password: String,
    val salt: String,
    val disabled: Boolean,// '账号状态（0正常 1停用）',
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime?,
    val deleted: Boolean//（0代表未删除，1代表已删除）',
)