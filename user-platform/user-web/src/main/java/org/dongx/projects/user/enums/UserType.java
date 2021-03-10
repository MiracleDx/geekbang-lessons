package org.dongx.projects.user.enums;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public enum UserType {
	
	// 底层实际 public final class UserType extends java.lang.Enum
	
	NORMAL,
	VIP;
	
	
	UserType() {
		// 枚举中构造器是 private	
	}
	
	public static void main(String[] args) {
		UserType.VIP.ordinal();
	}
}
