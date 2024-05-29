package com.startzhao.spzx.model.entity.system;

import com.startzhao.spzx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class SysRoleMenu extends BaseEntity {

    private Long roleId;       // 角色id
    private Long menuId;       // 菜单id
    private Long isHalf;       // 是否为全选中

}
