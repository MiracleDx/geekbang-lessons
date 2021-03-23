package org.dongx.configuration.microprofile.config.source;

import java.util.Map;

/**
 * 操作系统环境变量 ConfigSource
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class OperationSystemEnvironmentVariablesConfigSource extends MapBasedConfigSource {

	protected OperationSystemEnvironmentVariablesConfigSource() {
		super("Operation System Environment Variables", 300);
	}

	@Override
	protected void prepareConfigData(Map configData) throws Throwable {
		configData.putAll(System.getenv());
	}
}
