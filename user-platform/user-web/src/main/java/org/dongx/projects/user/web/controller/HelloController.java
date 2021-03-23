package org.dongx.projects.user.web.controller;

import org.dongx.configuration.microprofile.config.ServletConfigContextHolder;
import org.dongx.configuration.microprofile.config.ThreadLocalConfigHolder;
import org.dongx.context.annotation.Value;
import org.dongx.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 输出 "Hello World" Controller
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Path("/hello")
public class HelloController implements PageController {
	
	// 依赖注入获取
	@Value(propertyName = "java.version", defaultValue = "default")
	private String javaVersion;
	
	@GET
	@POST
	@Path("/world")
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 获取 session
		HttpSession session = request.getSession();
		// 通过 Servlet Context 获取
		String applicationName = ServletConfigContextHolder.getInstance().getConfig().getValue("application.name", String.class);
		// 通过 ThreadLocal 获取
		String javaHome = ThreadLocalConfigHolder.getConfig().getValue("JAVA_HOME", String.class);
		session.setAttribute("application_name", applicationName);
		session.setAttribute("java_version", javaVersion);
		session.setAttribute("java_home", javaHome);
		return "index.jsp";
	}
}
