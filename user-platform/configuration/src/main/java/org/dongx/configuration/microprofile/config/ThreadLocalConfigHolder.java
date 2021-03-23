package org.dongx.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class ThreadLocalConfigHolder {
	
	private static ConcurrentHashMap<String, ThreadLocal<Config>> threadLocalMaps = new ConcurrentHashMap<>();
	
	public static void initial() {
		threadLocalMaps.put(Thread.currentThread().getName(), 
				ThreadLocal.withInitial(() -> ServletConfigContextHolder.getInstance().getConfig()));
	}
	
	public static Config getConfig() {
		return threadLocalMaps.get(Thread.currentThread().getName()).get();
	}
	
	public static void release() {
		threadLocalMaps.get(Thread.currentThread().getName()).remove();
	}
}
