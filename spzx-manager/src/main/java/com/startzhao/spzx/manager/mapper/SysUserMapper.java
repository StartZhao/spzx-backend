package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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

    @Insert("INSERT INTO \n" +
            "sys_user(username, `password`,`name`, phone,avatar,description,create_time,update_time,`status`)\n" +
            "VALUES(#{userName},#{password},#{name},#{phone},#{avatar},#{description},#{createTime},#{updateTime},#{status})")
    int insert(SysUser sysUser);
}
