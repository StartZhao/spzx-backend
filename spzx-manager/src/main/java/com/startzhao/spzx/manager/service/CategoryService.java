package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.vo.product.CategoryExcelVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName: CategoryService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/29 19:49
 * @Version 1.0
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据商品分类的parentId获取下级商品分类
     * @param parentId
     */
    List<Category> findByParentId(Long parentId);

    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);

    /**
     * 导入
     * @param file
     */
    void importData(MultipartFile file);

    /**
     * 将excel导入数据保存到数据库
     * @param list
     */
    void saveBatch(List<CategoryExcelVO> list);
}
