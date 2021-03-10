package org.dongx.projects.user.validator.bean.validation;

import org.dongx.projects.user.domain.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class BeanValidationDemo {

	public static void main(String[] args) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		User user = new User();
		user.setPassword("***");

		Set<ConstraintViolation<User>> validate = validator.validate(user);
		
		validate.forEach(e -> {
			System.out.println(e.getMessage());
		});
	}
}
