package org.dongx.projects.user.service.impl;

import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.orm.jpa.DelegatingEntityManager;
import org.dongx.projects.user.service.UserService;

import javax.annotation.Resource;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link org.dongx.projects.user.service.UserService} 实现类
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class UserServiceImpl implements UserService {
	
	@Resource(name = "bean/EntityManager")
	private DelegatingEntityManager entityManager;
	
	@Override
	public boolean register(User user) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		try {
			entityManager.persist(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		} finally {
			transaction.rollback();
		}
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

	@Override
	public List<User> findAll() {
		Query query = entityManager.createQuery("select id, name, password, email, phoneNumber from User");
		List rows = query.getResultList();
		List<User> users = new ArrayList<>();
		for (Object row : rows) {
			Object[] cells = (Object[]) row;
			User user = new User();
			user.setId((Long) cells[0]);
			user.setName(cells[1].toString());
			user.setPassword(cells[2].toString());
			user.setEmail(cells[3].toString());
			user.setPhoneNumber(cells[4].toString());
			users.add(user);
		}
		return users;
	}
}
