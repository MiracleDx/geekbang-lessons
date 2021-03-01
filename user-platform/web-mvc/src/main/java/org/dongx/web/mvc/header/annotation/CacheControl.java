package org.dongx.web.mvc.header.annotation;

import java.lang.annotation.*;

/**
 * 缓存控制注解
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheControl {
}
