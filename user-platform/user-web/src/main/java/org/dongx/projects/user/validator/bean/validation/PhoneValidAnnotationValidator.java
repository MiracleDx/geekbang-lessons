package org.dongx.projects.user.validator.bean.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义手机号注解校验
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class PhoneValidAnnotationValidator implements ConstraintValidator<Phone, String> {
	
	private String message;
	
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$");
	
	@Override
	public void initialize(Phone annotation) {
		this.message = annotation.message();		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Matcher matcher = PHONE_PATTERN.matcher(value);
		return matcher.find();
	}
}
