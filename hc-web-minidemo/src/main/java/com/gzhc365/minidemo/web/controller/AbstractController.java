package com.gzhc365.minidemo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gzhc365.common.constans.RedisConstans;
import com.gzhc365.minicommon.controller.BaseController;
import com.gzhc365.miniconfig.model.User;

import javax.servlet.http.HttpSession;

/**
 * @author chengang
 * @date 18-4-11
 * @time 上午10:13
 * @since JDK 1.8
 */
public abstract class AbstractController extends BaseController {

    protected User getUser(HttpSession session) {
        return JSONObject.parseObject(session.getAttribute(RedisConstans.REDIS_SESSION_USER).toString(), User.class);
    }
}
