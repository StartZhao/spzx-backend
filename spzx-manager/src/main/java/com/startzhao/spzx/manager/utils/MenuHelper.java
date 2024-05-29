package com.startzhao.spzx.manager.utils;

import com.startzhao.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MenuHelper
 * Package: com.startzhao.spzx.manager.utils
 * Description: 菜单工具类
 *
 * @Author StartZhao
 * @Create 2024/5/28 23:57
 * @Version 1.0
 */
public class MenuHelper {

    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> tree = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            if (sysMenu.getParentId().longValue() == 0) tree.add(findChildren(sysMenu,sysMenuList));
        });
        return tree;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> tree) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        tree.forEach(node -> {
            if (sysMenu.getId().longValue() == node.getParentId().longValue()) {
                sysMenu.getChildren().add(findChildren(node,tree));
            }
        });
        return sysMenu;
    }
}
