package org.dongx.web.mvc.header;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 缓存请求头输出器
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
public class CacheControlHeaderWriter implements HeaderWriter {
	
	@Override
	public void write(Map<String, List<String>> headers, String... headerValues) {
		headers.put("cache-control", Arrays.asList(headerValues));
	}
}
