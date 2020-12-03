package com.infa.idt.tools.build.common;

public enum OsType {

	WINDOWS("Windoes"), LINUX("Linux");

	String type;

	OsType(String type) {
		this.type = type;
	}

	public static OsType getOsType(String type) {
		if (type.toLowerCase().startsWith("win"))
			return WINDOWS;
		else
			return LINUX;
	}

	public String toString() {
		return this.type;
	}
}
