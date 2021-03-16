package org.dongx.configuration.microprofile.config.source;

import java.util.Map;

/**
 * Java 系统属性
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class JavaSystemPropertiesConfigSource extends AbstractPropertiesConfigSource {

	/**
	 * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
	 * -Dapplication.name=user-web
	 */
	
	public JavaSystemPropertiesConfigSource() {
		super();
	}

	@Override
	public Map initialProperties() {
		return System.getProperties();
	}

	@Override
	public String getName() {
		return "Java System Properties";
	}
}
