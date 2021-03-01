package org.dongx.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面控制器，负责服务端页面渲染
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
public interface PageController extends Controller {

	/**
	 * 处理方法
	 * @param request HTTP 请求
	 * @param response HTTP 响应
	 * @return 视图地址路径
	 * @throws Throwable 异常
	 */
	String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
