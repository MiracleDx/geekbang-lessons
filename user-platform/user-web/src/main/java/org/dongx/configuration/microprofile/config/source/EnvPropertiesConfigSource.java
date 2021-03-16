package org.dongx.configuration.microprofile.config.source;

import java.util.Map;

/**
 * OS 环境变量属性
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class EnvPropertiesConfigSource extends AbstractPropertiesConfigSource {

	public EnvPropertiesConfigSource() {
		super();
	}

	@Override
	public Map initialProperties() {
		return System.getenv();
	}

	@Override
	public String getName() {
		return "Environment Properties";
	}
}
