package com.gzhc365.minidemo.web.handlefilter;

import com.gzhc365.common.exceptions.BizException;
import com.gzhc365.minidemo.web.common.constants.WebCode;
import com.gzhc365.minicommon.exception.MiniCenterException;
import com.gzhc365.minicommon.util.CodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengang
 * @date 18-4-8
 * @time 上午10:10
 * @since JDK 1.8
 */
public abstract class BaseFilter implements Filter {
    protected static Logger logger = LoggerFactory.getLogger(BaseFilter.class);
    protected static final String PAY_BACK_URL = "/api/catering/order/payBack";

    /**
     * 统一处理异常
     *
     * @param chain
     * @param req
     * @param resp
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    protected void filter(FilterChain chain, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Throwable cause = null;
        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            cause = e;
            if (cause != null && cause.getCause() instanceof MiniCenterException) {
                MiniCenterException ex = (MiniCenterException) cause.getCause();
                logger.error("调用服务中心异常MiniCenterException,code={},msg={}", ex.getCode(), ex.getMsg());
                print(WebCode.BIZ_ERROR_CODE, ex.getCode(), ex.getMsg(), resp);
                return;
            } else if (cause.getCause() instanceof BizException) {
                BizException bex = (BizException) cause.getCause();
                logger.error("调用服务中心异常bizException,code={},msg={}", bex.getCode(), bex.getMsg());
                print(bex.getCode(), null, bex.getMsg(), resp);
                return;
            }
        }
    }

    /**
     * 输出结果
     *
     * @param code
     * @param bizCode
     * @param msg
     * @param resp
     */
    protected void print(Integer code, String bizCode, String msg, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        if (!StringUtils.isBlank(bizCode)) {
            result.put("bizCode", bizCode);
        }
        result.put("msg", msg);
        com.gzhc365.web.utils.HtmlUtil.writerJson(resp, result);
    }

    /**
     * 输出结果
     *
     * @param code
     * @param bizCode
     * @param resp
     */
    protected void print(Integer code, String bizCode, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        if (!StringUtils.isBlank(bizCode)) {
            result.put("bizCode", bizCode);
        }
        result.put("msg", CodeUtil.getZH_Value(code.toString()));
        com.gzhc365.web.utils.HtmlUtil.writerJson(resp, result);
    }
}
