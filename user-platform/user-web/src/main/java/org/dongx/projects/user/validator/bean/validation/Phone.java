package org.dongx.projects.user.validator.bean.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 手机号长度校验注解
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidAnnotationValidator.class)
public @interface Phone {
	
	String message() default "手机号校验错误, 请以 1 开头, 并 11 位长度";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
