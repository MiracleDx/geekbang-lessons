package org.dongx.web.mvc;

import org.dongx.web.mvc.controller.Controller;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 处理方法信息类
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
public class HandlerMethodInfo {

	/**
	 * 请求路径
	 */
	private final String requestPath;
	
	private final Controller handlerController;

	/**
	 * 处理方法
	 */
	private final Method handlerMethod;

	/**
	 * 支持的 Http 方法
	 */
	private final Set<String> supportedHttpMethods;

	public HandlerMethodInfo(String requestPath, Controller handlerController, Method handlerMethod, Set<String> supportedHttpMethods) {
		this.requestPath = requestPath;
		this.handlerController = handlerController;
		this.handlerMethod = handlerMethod;
		this.supportedHttpMethods = supportedHttpMethods;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public Controller getHandlerController() {
		return handlerController;
	}
	
	public Method getHandlerMethod() {
		return handlerMethod;
	}

	public Set<String> getSupportedHttpMethods() {
		return supportedHttpMethods;
	}
}
