package org.dongx.configuration.microprofile.config.source;

import java.util.Map;

/**
 * 动态配置源
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DynamicConfigSource extends MapBasedConfigSource {
	
	private Map configData;

	public DynamicConfigSource(String name, int ordinal) {
		super("DynamicConfigSource", 500);
	}

	@Override
	protected void prepareConfigData(Map configData) {
		this.configData = configData;
	}
	
	public void onUpdate(String data) {
		// 更新（异步）
	}
}
