package org.dongx.projects.user.validator.bean.validation;

import org.dongx.projects.user.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义用户注解校验
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class UserValidAnnotationValidator implements ConstraintValidator<UserValid, User> {
	
	private int idRange;
	
	@Override
	public void initialize(UserValid annotation) {
		this.idRange = annotation.idRange();
	}

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		// 获取模版信息
		String messageTemplate = context.getDefaultConstraintMessageTemplate();

		Long id = value.getId();
		return id != null && id > idRange && Math.round(id) == id;
	}
}
