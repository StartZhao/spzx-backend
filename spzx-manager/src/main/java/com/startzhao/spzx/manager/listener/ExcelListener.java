package com.startzhao.spzx.manager.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.startzhao.spzx.manager.mapper.CategoryMapper;
import com.startzhao.spzx.manager.service.CategoryService;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.vo.product.CategoryExcelVO;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ExcelLisenter
 * Package: com.startzhao.spzx.manager.lisenter
 * Description: Excel监听器
 * 在读取excel的时候，会回调com.alibaba.excel.read.listener.ReadListener#invoke的方法，
 * 而spring如果管理Listener会导致Listener 变成了单例，在有并发读取文件的情况下都会回调同一个Listener，
 * 就无法区分是哪个文件读取出来的了。
 * @Author StartZhao
 * @Create 2024/5/30 0:06
 * @Version 1.0
 */
public class ExcelListener<T> implements ReadListener<T> {


    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<CategoryExcelVO> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private CategoryService categoryService;

    public ExcelListener(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        cachedDataList.add((CategoryExcelVO) t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    private void saveData() {
        categoryService.saveBatch(cachedDataList);
    }
}
