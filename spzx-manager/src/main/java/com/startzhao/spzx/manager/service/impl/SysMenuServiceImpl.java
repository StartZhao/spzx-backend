package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.manager.mapper.SysMenuMapper;
import com.startzhao.spzx.manager.mapper.SysRoleMenuMapper;
import com.startzhao.spzx.manager.service.SysMenuService;
import com.startzhao.spzx.manager.utils.MenuHelper;
import com.startzhao.spzx.model.entity.system.*;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.system.SysMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SysMenuServiceImpl
 * Package: com.startzhao.service.impl
 * Description: 首页服务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:58
 * @Version 1.0
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    /**
     * 保存菜单
     *
     * @param sysMenu
     */
    @Override
    public void saveSysMenu(SysMenu sysMenu) {
        String component = sysMenu.getComponent();
        String title = sysMenu.getTitle();
        Integer sortValue = sysMenu.getSortValue();
        Integer status = sysMenu.getStatus();
        if (StringUtils.isEmpty(component) || StringUtils.isEmpty(title) || sortValue == null || status == null)
            throw new StartZhaoException(500, "字段填写不完整，请填写完整");
        if (sortValue < 1) throw new StartZhaoException(500, "排序值填写有无，请重新填写");

        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title).eq("component", component);
        SysMenu menu = sysMenuMapper.selectOne(queryWrapper);
        if (menu != null) throw new StartZhaoException(500, "存在相同标题或相同组件，请勿重复添加");
        sysMenu.setCreateTime(DateTime.now());
        sysMenu.setUpdateTime(DateTime.now());
        sysMenuMapper.insert(sysMenu);
    }

    /**
     * 修改菜单信息
     *
     * @param sysMenu
     */
    @Override
    public void updateSysMenu(SysMenu sysMenu) {
        sysMenu.setUpdateTime(DateTime.now());
        sysMenuMapper.updateById(sysMenu);
    }

    /**
     * 根据id 删除菜单数据
     * 由于存在菜单表被角色表关联的情况
     * 故要在角色与用户没有关联时才可删除
     *
     * @param menuId
     * @return
     */
    @Override
    public void deleteSysMenuById(Long menuId) {
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.lambda().eq(SysMenu::getParentId,menuId).eq(SysMenu::getIsDeleted,DeletedStatusConstant.NOT_DELETED);
        long count = count(sysMenuQueryWrapper);
        if (count > 0) throw new StartZhaoException(ResultCodeEnum.NODE_ERROR);
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", menuId).eq("is_deleted", DeletedStatusConstant.NOT_DELETED);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(queryWrapper);
        if (sysRoleMenus.size() > 0) throw new StartZhaoException(500, "该菜单被角色关联，若要删除该菜单，请先取消菜单与角色关联关系");
        sysMenuMapper.deleteById(menuId);
    }


    /**
     * 菜单分页
     *
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", DeletedStatusConstant.NOT_DELETED).orderByAsc("sort_value");
        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
        if (sysMenus == null) return null;
        sysMenus = MenuHelper.buildTree(sysMenus);
        return sysMenus;
    }

    /**
     * 1.查询所有的菜单
     * 2.通过角色 id 查询该角色对应的菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public Map<String, List> findSysRoleMenuByRoleId(Long roleId) {
        Map<String, List> map = new HashMap<>();
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", DeletedStatusConstant.NOT_DELETED);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
        map.put("sysMenuList", sysMenus);

        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId).eq("is_deleted", DeletedStatusConstant.NOT_DELETED);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(wrapper);
        List<Long> roleMenuIds = new ArrayList<>();
        sysRoleMenus.forEach(sysRoleMenu -> {
            roleMenuIds.add(sysRoleMenu.getMenuId());
        });
        map.put("roleMenuIds", roleMenuIds);
        return map;
    }

    /**
     * 根据当前用户动态的获取菜单栏
     *
     * @return
     */
    @Override
    public List<SysMenuVO> findUserMenuList() {
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();
        List<SysMenu> sysMenus = sysMenuMapper.selectListByUserId(userId);
        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenus);
        return this.buildMenus(sysMenuTreeList);
    }

    private List<SysMenuVO> buildMenus(List<SysMenu> sysMenuTreeList) {
        List<SysMenuVO> sysMenuVOList = new ArrayList<>();
        sysMenuTreeList.forEach(sysMenu -> {
            SysMenuVO sysMenuVO = new SysMenuVO();
            sysMenuVO.setName(sysMenu.getComponent());
            sysMenuVO.setTitle(sysMenu.getTitle());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVO.setChildren(buildMenus(children));
            }
            sysMenuVOList.add(sysMenuVO);
        });
        return sysMenuVOList;
    }


}
