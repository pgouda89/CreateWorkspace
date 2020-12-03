package com.infa.idt.tools.build.utils;

public class SystemPropertyProvider {

	public static String getProperty(String systemPropertyName) {
		return getProperty(systemPropertyName, null, null);
	}

	public static String getProperty(String systemPropertyName, String systemEnvName) {
		return getProperty(systemPropertyName, systemEnvName, null);
	}

	public static String getProperty(String propertyName, String systemEnvName, String defaultPropertyValue) {
		String systemPropertyValue = System.getProperty(propertyName);
		if (systemPropertyValue == null && systemEnvName != null) {
			systemPropertyValue = System.getenv(systemEnvName);
		}

		if (systemPropertyValue == null)
			return defaultPropertyValue;
		return systemPropertyValue;
	}
}
