package com.gzhc365.minidemo;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author chengang
 * @date 18-3-14
 * @time 下午5:22
 * @since JDK 1.8
 */
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public abstract class AbstractTest extends AbstractJUnit4SpringContextTests {
    protected String getOverLimitStr(int limit){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<limit;i++){
            builder.append(1);
        }
        return builder.toString();
    }
}
