package com.gzhc365.minidemo.web.service;

import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.minidemo.facade.UserFacade;
import com.gzhc365.minidemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 用户相关业务逻辑实现
 *
 * @author chengang
 * @date 19-4-15
 * @time 下午5:57
 * @since JDK 1.8
 */
@Service
public class UserService {
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private Destination queueDestination;

    @Autowired
    private JmsTemplate jmsTemplate;
    /**
     * 获取指定用户信息
     *
     * @param userId
     * @param context
     * @return
     * @throws com.gzhc365.minicommon.exception.MiniCenterException
     */
    public User getById(Long userId, HcContext context) {
        jmsTemplate.send(queueDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(userId+"");
            }
        });
        return userFacade.getById(userId, context);
    }


    /**
     *
     * @param name
     * @param context
     * @return
     */
    public User getByName(String name, HcContext context) {
        return userFacade.getByName(name, context);
    }

}
