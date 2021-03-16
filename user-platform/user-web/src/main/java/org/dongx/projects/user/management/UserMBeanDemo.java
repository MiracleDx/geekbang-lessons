package org.dongx.projects.user.management;

import org.dongx.projects.user.domain.User;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class UserMBeanDemo {

	public static void main(String[] args) throws Exception {
		// 获取平台 MBean Server
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// 为 UserMXBean 定义 ObjectName
		ObjectName objectName = new ObjectName("org.dongx.projects.user.management:type=User");
		// 创建 UserMBean 实例
		User user = new User();
		mBeanServer.registerMBean(createUserBean(user), objectName);
		while (true) {
			Thread.sleep(2000);
			System.out.println(user);
		}
	}

	private static Object createUserBean(User user) {
		return new UserManager(user);
	}


}
