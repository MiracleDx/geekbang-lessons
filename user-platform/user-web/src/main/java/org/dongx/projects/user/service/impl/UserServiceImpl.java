package org.dongx.projects.user.service.impl;

import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.service.UserService;

/**
 * {@link org.dongx.projects.user.service.UserService} 实现类
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class UserServiceImpl implements UserService {
	
	@Override
	public boolean register(User user) {
		return false;
	}

	@Override
	public boolean deregister(User user) {
		return false;
	}

	@Override
	public boolean update(User user) {
		return false;
	}

	@Override
	public User queryUserById(Long id) {
		return null;
	}

	@Override
	public User queryUserByNameAndPassword(String name, String password) {
		return null;
	}
}
