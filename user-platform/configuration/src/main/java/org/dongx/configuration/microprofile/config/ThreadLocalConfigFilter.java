package org.dongx.configuration.microprofile.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@WebFilter(urlPatterns = {"/**"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.ASYNC})
public class ThreadLocalConfigFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init ThreadLocalConfigFilter complete");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ThreadLocalConfigHolder.initial();
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		ThreadLocalConfigHolder.release();
	}
}
