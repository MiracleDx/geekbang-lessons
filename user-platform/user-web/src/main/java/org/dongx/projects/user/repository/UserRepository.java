package org.dongx.projects.user.repository;

import org.dongx.projects.user.domain.User;

import java.util.Collection;

/**
 * 用户存储仓库
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
public interface UserRepository {

	/**
	 * 保存用户
	 *
	 * @param user {@link User}
	 * @return
	 */
	boolean save(User user);

	/**
	 * 通过 id 删除用户
	 *
	 * @param userId 主键
	 * @return
	 */
	boolean deleteById(Long userId);

	/**
	 * 更新用户
	 *
	 * @param user {@link User}
	 * @return
	 */
	boolean update(User user);

	/**
	 * 根据 id 获取用户
	 *
	 * @param userId 主键
	 * @return
	 */
	User getById(Long userId);

	/**
	 * 通过用户名和密码获取用户
	 *
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	User getByNameAndPassword(String userName, String password);

	/**
	 * 获取全部用户
	 *
	 * @return {@link Collection<User>}
	 */
	Collection<User> getAll();
}
