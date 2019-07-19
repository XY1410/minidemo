package com.gzhc365.minidemo.web.handlefilter;

import com.alibaba.dubbo.common.utils.IOUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengang
 * @date 18-5-23
 * @time 下午1:20
 * @since JDK 1.8
 */
public class MiniHttpServletRequest extends HttpServletRequestWrapper {
    private JSONObject jsonObject = null;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public MiniHttpServletRequest(HttpServletRequest request) {
        super(request);
        String body = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            body = IOUtils.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (body != null) {
            try {
                jsonObject = JSON.parseObject(body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getParameter(String key) {
        String value = super.getParameter(key);
        if (StringUtils.isBlank(value)) {
            if (jsonObject != null) {
                value = jsonObject.getString(key);
            }
            if (StringUtils.isBlank(value)) {
                value = getHeader(key);
            }
        }
        return value;
    }
}
