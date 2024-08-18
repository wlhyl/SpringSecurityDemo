package com.example.SpringSecurityDemo.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

@TableName(value = "sys_menu")
data class Menu(
    @TableId
    val id: UInt,
    val menuName: String,// 菜单名
    val perms: String, // 权限标识
    val createTime: LocalDateTime, // 创建时间
    val updateTime: LocalDateTime?, // 更新时间
    val deleted: Boolean, // 删除标志（0代表未删除，1代表已删除
    val remark: String?// 备注
)
