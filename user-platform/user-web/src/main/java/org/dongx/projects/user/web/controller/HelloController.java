package org.dongx.projects.user.web.controller;

import org.dongx.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	
	@GET
	@POST
	@Path("/world")
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "index.jsp";
	}
}
