package org.dongx.projects.user.web.listener;

import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.management.UserManager;

import javax.management.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

/**
 * Jolokia 初始化器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class JolokiaInitializerListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			// 获取平台 MBean Server
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			// 为 UserMXBean 定义 ObjectName
			ObjectName objectName = new ObjectName("org.dongx.projects.user.management:type=User");
			// 创建 UserMBean 实例
			User user = new User();
			mBeanServer.registerMBean(new UserManager(user), objectName);
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
