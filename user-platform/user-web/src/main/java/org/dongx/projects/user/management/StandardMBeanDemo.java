package org.dongx.projects.user.management;

import org.dongx.projects.user.domain.User;

import javax.management.MBeanInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class StandardMBeanDemo {

	public static void main(String[] args) throws NotCompliantMBeanException {
		// 将静态的 MBean 接口转化成 DynamicBean
		StandardMBean standardMBean = new StandardMBean(new UserManager(new User()), UserManagerMBean.class);

		MBeanInfo mBeanInfo = standardMBean.getMBeanInfo();

		System.out.println(mBeanInfo);
	}
}
