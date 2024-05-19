package com.startzhao.spzx.model.vo.common;

/**
 * ClassName: PageResult
 * Package: com.startzhao.spzx.model.vo.common
 * Description: 封装分页查询结果
 *
 * @Author StartZhao
 * @Create 2024/5/19 16:04
 * @Version 1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private Long total; //总记录数

    private List<T> records; //当前页数据集合

}
