package com.gzhc365.minidemo.web.handlefilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzhc365.component.utils.entity.HcContext;
import com.gzhc365.gateway.facade.GateWayFacade;
import com.gzhc365.miniconfig.model.User;
import com.gzhc365.minidemo.web.common.constants.WebCode;
import com.gzhc365.miniconfig.cache.ConfigCache;
import com.gzhc365.miniconfig.common.constants.ConfigSystemEnum;
import com.gzhc365.miniconfig.model.Config;
import com.gzhc365.web.controller.BaseController;
import com.gzhc365.web.log.MultiReadHttpRequestWarp;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: 微信小程序授权<br/>
 *
 * @author liuzhongqi
 * @version 1.0
 * @date: 2016年11月11日 下午4:40:50
 * @since JDK 1.7
 */
public class WeChatAppFilter extends BaseFilter {
    /**
     * 微信授权获取openid,unionid和sessionkey
     */
    private static final String AUTHOR_OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    private static final String LOGIN_REQUEST_URL = "getToken";
    private GateWayFacade gateWayFacade;
    private ConfigCache configCache;

    public HcContext getHcContext(HttpServletRequest request) {
        return (HcContext) request.getAttribute(BaseController.HC_CONTEXT);
    }

    /**
     * 微信小程序获取openid、unionid和sessionkey
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        logger.debug("进入微信小程序授权http请求");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
//        String requestURI = req.getRequestURI();
//        if (requestURI.indexOf(PAY_BACK_URL) > -1
//                || requestURI.indexOf("swagger") > -1
//                || requestURI.indexOf("api-docs.do") > -1) {
//            filter(chain, req, resp);
//            return;
//        }
//        MultiReadHttpRequestWarp requestWarp = new MultiReadHttpRequestWarp(req);
//        HcContext context = getHcContext(requestWarp);
//        logger.debug("sequence:{}, 请求的url地址为：{}", context.getMsgSeq(), req.getRequestURL() + "?" + req.getQueryString());
//        HttpSession session = req.getSession();
//        String code = request.getParameter("code");
//        Object obj = session.getAttribute("redis_session_user");
//        logger.debug("客户端上传的code和token为,code:{},token:{}", code, req.getParameter("login_access_token"));
//        if (obj != null) {
//            filter(chain, req, resp);
//            return;
//        }
//        if (StringUtils.isBlank(code)) {//如果code为空
//            logger.debug("sequence:{}, session不存在", context.getMsgSeq());
//            print(WebCode.TOKEN_CODE_ERROR, null, resp);
//            return;
//        }
//        if (requestURI.indexOf(LOGIN_REQUEST_URL) <= -1) {//访问授权链接错误
//            logger.debug("sequence:{}, 授权登录地址错误", context.getMsgSeq());
//            print(WebCode.TOKEN_URL_ERROR, null, resp);
//            return;
//        }
//        Config config = getConfigFromCenter(resp, context);
//        if (config == null) {
//            logger.error("sequence:{}, 小程序登陆授权获取不到对应的平台配置信息, system:{}", context.getMsgSeq(), ConfigSystemEnum.BUTTERFLY.getName());
//            print(WebCode.CONFIG_CODE_ERROR, null, resp);
//            return;
//        }
//        String str = getOpenIdFromWX(resp, config, code, context);
//        if (StringUtils.isBlank(str)) {
//            recordOpenIdError(context, resp);
//            return;
//        }
//        logger.debug("sequence:{}，微信app授权获取到的数据：{}", context.getMsgSeq(), str);
//        JSONObject result = JSONObject.parseObject(str);
//        if (StringUtils.isNotBlank(result.getString("errcode"))) {
//            recordOpenIdError(context, resp);
//            return;
//        }
//        String openId = result.getString("openid");
//        String sessionKey = result.getString("session_key");
//        String unionid = result.getString("unionid");
//        logger.debug("sequence:{}, 获取到的openId:{},unionId:{}", context.getMsgSeq(), openId, unionid);
//        User user = generatorUser(context, unionid, openId, sessionKey);
//        session.setAttribute("redis_session_user", JSONObject.toJSONString(user));
        filter(chain, req, resp);
    }

    /**
     * 从微信远程获取用户的openId
     *
     * @param resp
     * @param config
     * @param code
     * @param context
     * @return
     * @throws IOException
     */
    private String getOpenIdFromWX(HttpServletResponse resp, Config config, String code, HcContext context) throws IOException {
        String str = null;
        try {
            str = this.gateWayFacade.get(context,
                    AUTHOR_OPENID_URL.replace("APPID", config.getAppId()).replace("SECRET", config.getAppSec())
                            .replace("JSCODE", code), "utf-8");
        } catch (Exception e) {
            recordOpenIdError(context, resp);
            return null;
        }
        return str;
    }

    /**
     * 获取全局配置信息
     *
     * @param resp
     * @param context
     * @return
     */
    private Config getConfigFromCenter(HttpServletResponse resp, HcContext context) {
        Config config = null;
        try {
            config = configCache.get(ConfigSystemEnum.BUTTERFLY.getName());
        } catch (Exception e) {
            logger.error("sequence:{}, 获取全局配置信息异常,exception:{}", context.getMsgSeq(), e);
            print(WebCode.CONFIG_CODE_ERROR, null, resp);
            return null;
        }
        return config;
    }

    /**
     * 处理微信返回的错误消息
     *
     * @param context
     * @param resp
     * @throws java.io.IOException
     */
    private void recordOpenIdError(HcContext context, HttpServletResponse resp) throws IOException {
        logger.error("sequence:{}， 获取不到微信的授权信息", context.getMsgSeq());
        print(WebCode.WX_SYSTEM_ERROR, null, resp);
    }

    /**
     * 获取用户信息存储在session里
     *
     * @param context
     * @param openId
     * @param sessionKey
     * @return
     */
    private User generatorUser(HcContext context, String unionId, String openId, String sessionKey) {
        User user = new User();
        //todo  根据需要生成用户信息
        logger.debug("授权获取的用户信息", JSON.toJSON(user));
        return user;
    }


    public void setGateWayFacade(GateWayFacade gateWayFacade) {
        this.gateWayFacade = gateWayFacade;
    }

    /**
     * 返回 #{bare_field_comment}
     */
    public GateWayFacade getGateWayFacade() {
        return gateWayFacade;
    }

    public ConfigCache getConfigCache() {
        return configCache;
    }

    public void setConfigCache(ConfigCache configCache) {
        this.configCache = configCache;
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}