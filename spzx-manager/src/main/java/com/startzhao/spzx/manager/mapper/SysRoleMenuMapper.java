package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.entity.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * ClassName: SysRoleMenuMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/28 19:58
 * @Version 1.0
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色id逻辑删除角色菜单关系
     * @param roleId
     * @return
     */
    @Update("update sys_role_menu set is_deleted = 1 where role_id = #{roleId}")
    int deleteByRoleId(Long roleId);
}
