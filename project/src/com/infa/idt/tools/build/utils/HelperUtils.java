package com.infa.idt.tools.build.utils;

import com.infa.idt.tools.build.Argument;
import com.infa.idt.tools.build.common.Constansts;
import com.infa.idt.tools.build.common.OsType;

public class HelperUtils {

	public static String getEnvPrefix(OsType osType) {

		switch (osType) {
		case WINDOWS:
			return "set ";
		case LINUX:
			return "export ";
		default:
			return "set ";
		}
	}

	public static String getFileSeparator(OsType osType) {

		switch (osType) {
		case WINDOWS:
			return Constansts.WINDOWS_FILE_SEPARATOR;
		case LINUX:
			return Constansts.LINUX_FILE_SEPARATOR;
		default:
			return Constansts.WINDOWS_FILE_SEPARATOR;
		}
	}

	public static String getScriptExtention(OsType osType) {

		switch (osType) {
		case WINDOWS:
			return "bat";
		case LINUX:
			return "sh";
		default:
			return "bat";
		}
	}

	public static String getScriptFileName(String prefix, String release, String p4Client, String buildNo,
			OsType osType) {

		return String.format("%s_%s_%s_%s.%s", prefix, release, p4Client, buildNo, getScriptExtention(osType));
	}

	public static boolean isEmptyOrNull(String str) {
		if (str == null)
			return true;
		else if (str.trim().length() == 0)
			return true;
		return false;
	}

	public static void printManPage(OsType osType) {

		String argDetails = Constansts.EMPTY;
		for (Argument arg : Argument.values()) {
			argDetails += "\n\t" + arg.getName();
			argDetails += "\n\t\t" + arg.getDescription();
		}
		String manual = "Usage:  " + Constansts.SETUP_SCRIPT_NAME + getScriptExtention(osType)
				+ " -Drelease=<ReleaseVersion> -D<optionalArg1>=<value1> -D<optionalArg2>=<value2> ...\n"
				+ "\nWhere optional arguments include:\n\t" + argDetails + "\n\nExample command: "
				+ Constansts.SETUP_SCRIPT_NAME + getScriptExtention(osType)
				+ " -Drelease=10.4.0 -DbuildNo=107 -Dcomponents=dtm.ldtm -Dp4User=<user> -Dp4Client=<Client name> -DjavaHome=<javaJdkHome> -DmvnHome=<mvnHomeLocation> -Dp4Sync=YES -DplatformDir=<Platform directory>";
		System.out.println(manual);
		System.exit(0);
	}
}
