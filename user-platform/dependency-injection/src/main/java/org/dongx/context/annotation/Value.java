package org.dongx.context.annotation;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
	
	String propertyName() default "";
	
	String defaultValue() default "";
}
