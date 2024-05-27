package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * ClassName: SysUserMapper
 * Package: com.startzhao.spzx.manager.mapper;
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/19 17:01
 * @Version 1.0
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 根据用户id逻辑删除用户
     * @param id
     * @return
     */
    @Update("update sys_user set is_deleted = 1 where id = #{id}")
    int deleteById(Long id);

}
