package com.huilianyi.middleware.vo;

import com.huilianyi.middleware.common.CommonPo;
import com.huilianyi.middleware.po.SystemUser;
import lombok.Getter;
import lombok.Setter;

/**
 * SystemUserVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午5:41
 */
@Getter
@Setter
public class SystemUserVo extends CommonPo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 将po对象转换为vo对象
     *
     * @param po po
     * @return vo
     */
    public SystemUserVo changePoToVo(SystemUser po) {
        this.setObjId(po.getObjId());
        this.setName(po.getName());
        this.setUsername(po.getUsername());
        this.setCreateBy(po.getCreateBy());
        this.setIsEnable(po.getIsEnable());
        this.setRemark(po.getRemark());
        this.setCreateTime(po.getCreateTime());
        this.setUpdateTime(po.getUpdateTime());
        return this;
    }
}
