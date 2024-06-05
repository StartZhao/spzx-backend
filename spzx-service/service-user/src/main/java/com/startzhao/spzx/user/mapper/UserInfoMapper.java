package com.startzhao.spzx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserInfoMapper
 * Package: com.startzhao.spzx.user.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:41
 * @Version 1.0
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
