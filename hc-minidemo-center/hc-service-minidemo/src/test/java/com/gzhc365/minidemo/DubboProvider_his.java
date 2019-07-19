package com.gzhc365.minidemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboProvider_his {
    private static final Log log = LogFactory.getLog(DubboProvider_his.class);

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/spring-context.xml");
            context.start();
        } catch (Exception e) {
            log.error("== DubboProvider error:", e);
        }
        synchronized (DubboProvider_his.class) {
            while (true) {
                try {
                    DubboProvider_his.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:", e);
                }
            }
        }
    }
}
