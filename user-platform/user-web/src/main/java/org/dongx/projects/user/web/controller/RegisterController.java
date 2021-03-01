package org.dongx.projects.user.web.controller;

import org.dongx.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 注册前端控制器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Path("/register")
public class RegisterController implements PageController {

	@GET
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "register.jsp";
	}
}
