package org.dongx.projects.user.web.controller;

import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.repository.DatabaseUserRepository;
import org.dongx.projects.user.sql.DBConnectionManager;
import org.dongx.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Collection;

/**
 * 用户视图控制器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Path("/user")
public class UserController implements PageController {
	
	@POST
	@Path("/register")
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 获取 session
		HttpSession session = request.getSession();
		
		User user = new User();
		user.setName(request.getParameter("name"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setPhoneNumber(request.getParameter("phoneNumber"));
		
		String forwardPath;
		try {
			DatabaseUserRepository databaseUserRepository = new DatabaseUserRepository(new DBConnectionManager());
			// 存储用户
			databaseUserRepository.save(user);
			// 查询所有用户
			Collection<User> users = databaseUserRepository.getAll();
			session.setAttribute("users", users);
			forwardPath = "register_success.jsp";
		} catch (Exception e) {
			forwardPath = "register_failure.jsp";
		}
		session.setAttribute("user", user);
		return forwardPath;
	}
}
