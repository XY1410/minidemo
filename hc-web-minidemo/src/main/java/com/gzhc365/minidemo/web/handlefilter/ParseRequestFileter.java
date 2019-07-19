package com.gzhc365.minidemo.web.handlefilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chengang
 * @date 18-5-23
 * @time 下午1:35
 * @since JDK 1.8
 */
public class ParseRequestFileter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MiniHttpServletRequest miniHttpServletRequest = new MiniHttpServletRequest(httpServletRequest);
        chain.doFilter(miniHttpServletRequest, response);
    }

    @Override
    public void destroy() {

    }
}
