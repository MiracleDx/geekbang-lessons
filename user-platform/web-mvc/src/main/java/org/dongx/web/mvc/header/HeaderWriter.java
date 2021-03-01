package org.dongx.web.mvc.header;

import java.util.List;
import java.util.Map;

/**
 * 请求头输出器接口
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since 1.0
 */
public interface HeaderWriter {

	/** 请求头输出器
	 * @param headers 请求头容器
	 * @param headerValues 请求头值集合
	 */
	void write(Map<String, List<String>> headers, String... headerValues);
}
