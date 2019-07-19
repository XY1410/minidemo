package com.gzhc365.minidemo.activemq;

import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.minidemo.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class QueueListener implements MessageListener{
    @Autowired
    private UserFacade userFacade;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println(text);
            System.out.println(userFacade.getById(Long.parseLong(text), new HcContext()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
