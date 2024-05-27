package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.system.SysRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * ClassName: SysRoleUserMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/27 11:11
 * @Version 1.0
 */
@Mapper
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    /**
     * 根据用户id逻辑删除用户角色关系
     * @param userId
     * @return
     */
    @Update("update sys_role_user set is_deleted = 1 where user_id = #{userId}")
    int deleteByUserId(Long userId);
}
