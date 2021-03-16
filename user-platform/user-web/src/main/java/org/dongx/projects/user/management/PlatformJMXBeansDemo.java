package org.dongx.projects.user.management;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class PlatformJMXBeansDemo {

	public static void main(String[] args) {
		// 客户端获取 ClassLoadingMXBean 对象（代理对象）
		ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

		System.out.println(classLoadingMXBean.getLoadedClassCount());
	}
}
