package org.dongx.configuration.microprofile.config.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 本地配置文件属性
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class LocalPropertiesConfigSource extends AbstractPropertiesConfigSource {
	
	public LocalPropertiesConfigSource() {
		super();
	}
	
	@Override
	public Map initialProperties() {
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/logging.properties");
		Properties properties = new Properties();
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
