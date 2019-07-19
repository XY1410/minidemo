package com.gzhc365.minidemo.web.common.constants;

/**
 * 用于存储服务中心的异常码
 *
 * @author chengang
 * @date 18-3-15
 * @time 下午2:22
 * @since JDK 1.8
 */
public class WebCode {
    /**
     * 业务异常
     */
    public static final Integer BIZ_ERROR_CODE = 10001;

    /**
     * 授权码不能为空
     */
    public static final Integer TOKEN_CODE_ERROR = 10003;

    /**
     * 获取配置信息异常
     */
    public static final Integer CONFIG_CODE_ERROR = 10004;

    /**
     * 获取微信信息异常
     */
    public static final Integer WX_SYSTEM_ERROR = 10005;

    /**
     * 授权地址错误
     */
    public static final Integer TOKEN_URL_ERROR = 10006;

    /**
     * 数据错误，请重新授权
     */
    public static final Integer TOKEN_DATA_ERROR=10011;
}
