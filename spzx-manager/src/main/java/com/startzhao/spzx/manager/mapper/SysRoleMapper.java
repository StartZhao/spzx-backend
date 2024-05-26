package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * ClassName: SysRoleMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/23 17:50
 * @Version 1.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Insert("INSERT INTO sys_role(role_name,role_code,create_time,update_time) VALUES (#{roleName},#{roleCode},#{createTime},#{updateTime})")
    int insert(SysRole sysRole);

    @Update("update sys_role set is_deleted = 1 where id = #{roleId}")
    int deleteById(Long roleId);
}
