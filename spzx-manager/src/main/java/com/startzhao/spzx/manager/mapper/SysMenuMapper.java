package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClassName: SysMenuMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/27 23:50
 * @Version 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    @Update("UPDATE sys_menu SET is_deleted = 1 WHERE id = #{menuId}")
    public int deleteById(Long menuId);

    @Select("SELECT DISTINCT m.*\n" +
            "FROM sys_menu m\n" +
            "JOIN sys_role_menu rm \n" +
            "ON rm.`menu_id` = m.`id`\n" +
            "JOIN sys_role_user ru\n" +
            "ON rm.`role_id` = ru.`role_id`\n" +
            "WHERE user_id = 12")
    List<SysMenu> selectListByUserId(Long userId);
}
