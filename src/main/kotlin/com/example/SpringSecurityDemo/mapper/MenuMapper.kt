package com.example.SpringSecurityDemo.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.example.SpringSecurityDemo.entity.Menu
import org.apache.ibatis.annotations.Select

interface MenuMapper: BaseMapper<Menu> {
//     SELECT DISTINCT sys_menu.* from sys_user_role  LEFT JOIN sys_role ON sys_user_role.role_id = sys_role.id LEFT JOIN sys_role_menu on sys_role.id=sys_role_menu.role_id LEFT JOIN sys_menu on sys_role_menu.menu_id=sys_menu.id WHERE sys_user_role.user_id=1 and sys_role.deleted=0 and sys_menu.deleted=0
  @Select( "SELECT DISTINCT sys_menu.perms " +
          "from sys_user_role " +
          "LEFT JOIN sys_role ON sys_user_role.role_id = sys_role.id "
          + "LEFT JOIN sys_role_menu on sys_role.id=sys_role_menu.role_id "+
          "LEFT JOIN sys_menu on sys_role_menu.menu_id=sys_menu.id " +
    "WHERE sys_user_role.user_id=#{user_id} and sys_role.deleted=0 and sys_menu.deleted=0")
    fun selectPermsByUserId(userId: UInt):List<String>
}