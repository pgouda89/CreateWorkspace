package com.infa.idt.tools.build;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.infa.idt.tools.build.common.OsType;
import com.infa.idt.tools.build.utils.HelperUtils;
import com.infa.idt.tools.build.utils.SystemPropertyProvider;

public class BuildWorkspace {

	public static Workspace workspace;

	public static Properties perforceReleaseBranchMap = new Properties();

	public static void main(String[] arguments) {

		String OS = SystemPropertyProvider.getProperty("os.name", null, "Linux");
		OsType osType = OsType.getOsType(OS);
		String release = SystemPropertyProvider.getProperty(Argument.release.getName());
		String components = SystemPropertyProvider.getProperty(Argument.components.getName());

		if (arguments.length > 0 && arguments[0].equalsIgnoreCase("--help")) {
			HelperUtils.printManPage(osType);
		} else if (HelperUtils.isEmptyOrNull(release) || HelperUtils.isEmptyOrNull(components)) {
			System.out.println(
					"Invaild arguments. -Drelease=<ReleaseVersion> and -Dcomponents=<components> are mandatory the arguments.");
			HelperUtils.printManPage(osType);
		}
		try {
			workspace = new Workspace(osType, release, arguments);
			String p4MapFile = SystemPropertyProvider.getProperty("p4MapFile");
			if (!HelperUtils.isEmptyOrNull(p4MapFile)) {
				perforceReleaseBranchMap = getProperties(p4MapFile);
			}
			if (perforceReleaseBranchMap.getProperty(release) == null) {
				System.out.println("Not able to locate perforce depot path for the release [" + release + "]");
				HelperUtils.printManPage(osType);
			} else
				workspace.buildP4SyncCommands(perforceReleaseBranchMap.getProperty(release));

			String p4SyncFileDirectory = SystemPropertyProvider.getProperty("p4SyncFileDirectory");
			if (!HelperUtils.isEmptyOrNull(p4SyncFileDirectory)) {
				workspace.writeP4SyncCommandToFile(p4SyncFileDirectory);
			}

			workspace.buildWorkspaceEnvironments();
			String envFile = SystemPropertyProvider.getProperty("envFile");
			workspace.writeWorkspaceEnvFile(envFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(2);
		}

	}

	private static Properties getProperties(String p4MapFile) {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(p4MapFile)) {
			prop = new Properties();
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
	}
}
