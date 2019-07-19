package com.gzhc365.minidemo.facade.user;

import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.minicommon.constants.CenterCode;
import com.gzhc365.minidemo.AbstractTest;
import com.gzhc365.minidemo.facade.UserFacade;
import com.gzhc365.minicommon.exception.MiniCenterException;
import com.gzhc365.minicommon.util.CodeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 获取指定用户信息单测集合
 *
 * @author chengang
 * @date 18-7-24
 * @time 下午3:57
 * @since JDK 1.8
 */
public class UpdateTest extends AbstractTest {
    @Autowired
    private UserFacade userFacade;

    //******************************************************************************************************************

    /**
     * 当条件都符合时，是可以通过的
     */
    @Test
    public void validateSuccess() {
        commonValidate("", 1L);
    }

    //******************************************************************************************************************

    /**
     * 当用户id为空时，是可以通过的
     */
    @Test
    public void validateError() {
        commonValidate(CenterCode.PARAM_NULL, null);
    }
    //******************************************************************************************************************

    /**
     * 公共单测方法
     *
     * @param expectCode 期望的code码
     */
    private void commonValidate(String expectCode, Long userId) {
        String code = "";
        try {
            userFacade.getById(userId, new HcContext());
        } catch (MiniCenterException e) {
            code = e.getCode();
        }
        if (!"".equals(expectCode)) {
            expectCode = CodeUtil.getZH_Value("prefix") + expectCode;
        }
        Assert.assertEquals("equals", expectCode, code);
    }

    //******************************************************************************************************************
}
