package com.gzhc365.minidemo.impl;


import com.gzhc365.component.utils.cache.CacheClient;
import com.gzhc365.minidemo.dao.UserDao;
import com.gzhc365.minidemo.model.User;
import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.minidemo.facade.UserFacade;
import com.gzhc365.minidemo.validator.UserValidator;
import com.gzhc365.minicommon.exception.MiniCenterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * 用户服务实现
 *
 * @author chengang
 * @date 18-7-24
 * @time 下午3:53
 * @since JDK 1.8
 */
@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private Jedis cacheClient;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDao userDao;

    /**
     * 获取指定用户信息
     *
     * @param userId
     * @param context
     * @return
     * @throws com.gzhc365.minicommon.exception.MiniCenterException
     */
    public User getById(Long userId, HcContext context) throws MiniCenterException {
        userValidator.validateGetById(userId, context);
        cacheClient.hs
        return userDao.getById(userId);
    }

    @Override
    public User getByName(String name, HcContext context) throws MiniCenterException {
        userValidator.validateGetByName(name,context );
        return userDao.getByName(name);
    }

    /**
     * 组装用户信息
     *
     * @param userId
     * @return
     */
    private User assembleUser(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("香山红叶");
        return user;
    }
}
