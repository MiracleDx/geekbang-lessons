package org.dongx.projects.user.service;

import org.dongx.projects.user.domain.User;

import java.util.List;

/**
 * 用户服务
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public interface UserService {

	/**
	 * 注册用户
	 *
	 * @param user {@link User}
	 * @return 成功返回 <code>true</code>
	 */
	boolean register(User user);

	/**
	 * 注销用户
	 *
	 * @param user {@link User}
	 * @return 成功返回 <code>true</code>
	 */
	boolean deregister(User user);

	/**
	 * 更新用户信息
	 *
	 * @param user {@link User}
	 * @return 成功返回 <code>true</code>
	 */
	boolean update(User user);

	/**
	 * 通过 id 查询用户信息
	 *
	 * @param id 用户 id
	 * @return {@link User}
	 */
	User queryUserById(Long id);

	/**
	 * 通过用户名密码查询用户
	 *
	 * @param name 用户名
	 * @param password 密码
	 * @return {@link User}
	 */
	User queryUserByNameAndPassword(String name, String password);

	/**
	 * 查询所有用户
	 * @return {@link List<User>}
	 */
	List<User> findAll();
}
