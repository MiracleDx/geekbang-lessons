package org.dongx.projects.user.sql;

import java.lang.annotation.*;

/**
 * 本地事务
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Documented
// 仅支持方法级别
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalTransactional {
	
	int PROPAGATION_REQUIRED = 0;
	
	int PROPAGATION_REQUIRED_NEW = 3;
	
	int PROPAGATION_NESTED  = 6;

	/**
	 * 事务传播
	 * @return
	 */
	int propagation() default PROPAGATION_REQUIRED;

	/**
	 * 事务隔离级别
	 * @return
	 * @see Connection#TRANSACTION_READ_COMMITTED
	 */
	//int isolation() default TRANSACTION_READ_COMMITTED;
}
