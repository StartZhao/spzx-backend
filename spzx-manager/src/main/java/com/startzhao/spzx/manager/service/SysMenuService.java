package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.vo.system.SysMenuVO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SysMenuService
 * Package: com.startzhao.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:58
 * @Version 1.0
 */
public interface SysMenuService extends IService<SysMenu> {


    /**
     * 保存菜单
     *
     * @param sysMenu
     */
    void saveSysMenu(SysMenu sysMenu);

    /**
     * 修改菜单信息
     *
     * @param sysMenu
     */
    void updateSysMenu(SysMenu sysMenu);

    /**
     * 根据id 删除菜单数据
     * @param id
     * @return
     */
    void deleteSysMenuById(Long id);


    /**
     * 菜单分页
     *
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 1.查询所有的菜单
     * 2.通过角色 id 查询该角色对应的菜单
     *
     * @param roleId
     * @return
     */
    Map<String, List> findSysRoleMenuByRoleId(Long roleId);

    /**
     * 根据当前用户动态的获取菜单栏
     * @return
     */
    List<SysMenuVO> findUserMenuList();
}
