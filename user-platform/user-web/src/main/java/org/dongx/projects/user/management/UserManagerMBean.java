package org.dongx.projects.user.management;

import org.dongx.projects.user.domain.User;

/**
 * {@link User} MBean 接口描述
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public interface UserManagerMBean {
	
	// MBeanAttributeInfo 列表
	Long getId();
	
	void setId(Long id);
	
	String getName();
	
	void setName(String name);
	
	String getPassword();
	
	void setPassword(String password);
	
	String getEmail();
	
	void setEmail(String email);
	
	String getPhoneNumber();
	
	void setPhoneNumber(String phoneNumber);
	
	// MBeanOpeationInfo
	@Override
	String toString();
	
	User getUser();
	
}
