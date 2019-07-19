package com.gzhc365.minidemo.validator;

import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.minicommon.constants.CenterCode;
import com.gzhc365.minicommon.validator.BaseValidator;
import com.gzhc365.minidemo.model.LimitConstants;
import org.springframework.stereotype.Component;

/**
 * 用户验证器
 *
 * @author chengang
 * @date 18-7-24
 * @time 下午3:54
 * @since JDK 1.8
 */
@Component
public class UserValidator extends BaseValidator {

    /**
     * 获取“获取指定用户信息”入参是否正确
     *
     * @param userId
     * @param context
     */
    public void validateGetById(Long userId, HcContext context) {
        validateLongTypeIsNull(userId, CenterCode.PARAM_NULL, "userId");
    }


    public void validateGetByName(String name, HcContext context) {
        validateObjectIsNull(name, CenterCode.PARAM_NULL, "name");
        validateStrTypeOverLimit(name, LimitConstants.THIRTY, CenterCode.OVERLIMIT, "name" );
    }
}
