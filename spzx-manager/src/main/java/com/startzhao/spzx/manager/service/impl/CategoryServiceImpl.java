package com.startzhao.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.listener.ExcelListener;
import com.startzhao.spzx.manager.mapper.CategoryMapper;
import com.startzhao.spzx.manager.service.CategoryService;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.product.CategoryExcelVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CategoryServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 分类业务具体实现
 *
 * @Author StartZhao
 * @Create 2024/5/29 19:51
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据商品分类的parentId获取下级商品分类
     *
     * @param parentId
     */
    @Override
    public List<Category> findByParentId(Long parentId) {
        List<Category> categories = categoryMapper.selectByParentId(parentId);
        // 判断是否填充 hasChildren 属性
        if (CollectionUtils.isEmpty(categories)) return categories;
        categories.forEach(category -> {
            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.lambda().eq(Category::getParentId,category.getId()).eq(Category::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
            long count = count(categoryQueryWrapper);
            if (count > 0) category.setHasChildren(true);
            else category.setHasChildren(false);
        });
        return categories;
    }

    /**
     * 导出
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            // 设置响应头实现生成下载文件功能
            response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

            // 从数据库中获取需要导出的数据
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Category::getIsDeleted,DeletedStatusConstant.NOT_DELETED);
            List<Category> categoryList = categoryMapper.selectList(queryWrapper);
            List<CategoryExcelVO> categoryExcelVOList = new ArrayList<>();
            categoryList.forEach(category -> {
                CategoryExcelVO categoryExcelVO = new CategoryExcelVO();
                BeanUtils.copyProperties(category,categoryExcelVO);
                categoryExcelVOList.add(categoryExcelVO);
            });
            EasyExcel.write(response.getOutputStream(), CategoryExcelVO.class).sheet("分类数据").doWrite(categoryExcelVOList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StartZhaoException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     * 导入
     *
     * @param file
     */
    @Override
    public void importData(MultipartFile file) {
        try {
            // 创建监听器
            ExcelListener<CategoryExcelVO> excelListener = new ExcelListener<>(this);
            // 读取excel数据
            EasyExcel.read(file.getInputStream(),CategoryExcelVO.class,excelListener).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new StartZhaoException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     * 将excel导入数据保存到数据库
     *
     * @param list
     */
    @Override
    public void saveBatch(List<CategoryExcelVO> list) {
        List<Category> categoryList = new ArrayList<>();
        list.forEach(categoryExcelVO -> {
            Category category = new Category();
            BeanUtils.copyProperties(categoryExcelVO,category);
            categoryList.add(category);
        });
        saveBatch(categoryList);
    }

}
