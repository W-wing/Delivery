package com.kkb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@WebFilter("*.do")
public class CharSetFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
