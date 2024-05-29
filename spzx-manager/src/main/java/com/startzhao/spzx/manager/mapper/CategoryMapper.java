package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: CategoryMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/29 19:53
 * @Version 1.0
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /** 查到parentId对应的下级分类
     *
     * @param parentId
     * @return
     */
    @Select("SELECT id,name,image_url,parent_id,status,order_num,create_time,update_time,is_deleted\n" +
            "FROM category\n" +
            "WHERE parent_id = #{parentId}\n" +
            "AND is_deleted = 0\n" +
            "ORDER BY id DESC")
    List<Category> selectByParentId(Long parentId);
}
