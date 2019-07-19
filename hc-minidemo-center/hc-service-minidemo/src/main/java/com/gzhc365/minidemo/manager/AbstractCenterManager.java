package com.gzhc365.minidemo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 数据库事物处理
 *
 * @author chengang
 * @date 18-3-22
 * @time 下午3:24
 * @since JDK 1.8
 */
@Repository
public abstract class AbstractCenterManager {
    @Autowired
    private PlatformTransactionManager transactionManager;

    protected TransactionTemplate getCenterTransactionManager() {
        return new TransactionTemplate(transactionManager);
    }
}
