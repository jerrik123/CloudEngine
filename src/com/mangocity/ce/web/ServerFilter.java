package com.mangocity.ce.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class ServerFilter
 */

public class ServerFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ServerFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse resp = (HttpServletResponse) response;  
		//System.out.println(((HttpServletRequest)request).getRequestURI());
		// pass the request along the filter chain
		//验证来源合法性
		System.out.println(req.getHeader("referer"));
		String referer=((req).getHeader("Referer"));
		if(referer==null||!referer.startsWith("http://localhost")){
			
			String token =UUID.randomUUID().toString().replace("-", "");
			req.getSession().setAttribute("token", token);
			resp.setHeader("token", token);
			chain.doFilter(req, resp);
		}else{
			chain.doFilter(req, resp);
		}
		
		//chain.doFilter(req, resp);
		//验证token 有效性
		if(true){
			
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}



}
