package com.gzhc365.minidemo.facade;

import com.gzhc365.minidemo.model.User;
import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.minicommon.exception.MiniCenterException;

/**
 * 用户服务
 *
 * @author chengang
 * @date 18-7-24
 * @time 下午3:52
 * @since JDK 1.8
 */
public interface UserFacade {
    /**
     * 获取指定用户信息
     *
     * @param userId
     * @param context
     * @return
     * @throws MiniCenterException
     */
    public User getById(Long userId, HcContext context) throws MiniCenterException;

    /**
     * 根据用户名获取制定用户
     * @param name
     * @param context
     * @return
     * @throws MiniCenterException
     */
    public User getByName(String name, HcContext context) throws MiniCenterException;

}
