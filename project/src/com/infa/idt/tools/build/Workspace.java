package com.infa.idt.tools.build;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.infa.idt.tools.build.common.Constansts;
import com.infa.idt.tools.build.common.OsType;
import com.infa.idt.tools.build.exception.InvalidArgumentException;
import com.infa.idt.tools.build.utils.CondorUtils;
import com.infa.idt.tools.build.utils.HelperUtils;
import com.infa.idt.tools.build.utils.SystemPropertyProvider;

public class Workspace {

	private OsType osType;

	private final String release;

	private String javaHome;

	private String mvnHome;

	private String infaMvnRepo;

	private String buildNo;

	private String platformDir;

	private String p4Sync;

	private String p4User;

	private String p4Client;

	private Set<String> p4SyncCommands = new HashSet<String>();

	private Set<String> components = new HashSet<String>();

	private Map<String, String> environments = new HashMap<String, String>();

	public Workspace(OsType osType, String release, String[] arguments) throws InvalidArgumentException {

		this.osType = osType;

		this.release = release;

		this.javaHome = SystemPropertyProvider.getProperty(Argument.javaHome.getName(), Constansts.JAVA_HOME);
		if (this.javaHome == null) {
			throw new InvalidArgumentException("javaHome is missing in the argument line.");
		}

		this.mvnHome = SystemPropertyProvider.getProperty(Argument.mvnHome.getName(), Constansts.MVN_HOME);
		if (this.mvnHome == null) {
			throw new InvalidArgumentException("mvnHome is missing in the argument line.");
		}

		this.buildNo = SystemPropertyProvider.getProperty(Argument.buildNo.getName(), null, "LATEST");

		this.platformDir = SystemPropertyProvider.getProperty(Argument.platformDir.getName(), null, null);

		this.p4Sync = SystemPropertyProvider.getProperty(Argument.p4Sync.getName(), null, "NO");

		this.p4User = SystemPropertyProvider.getProperty(Argument.p4User.getName(), Constansts.P4USER, null);

		this.p4Client = SystemPropertyProvider.getProperty(Argument.p4Client.getName(), Constansts.P4CLIENT, null);

		this.infaMvnRepo = SystemPropertyProvider.getProperty(Argument.infaMvnRepo.getName(), null, null);

		String componentStr = SystemPropertyProvider.getProperty(Argument.components.getName(), null, null);
		if (componentStr != null) {
			String[] componentList = componentStr.split(Constansts.COMMA);
			for (String component : componentList) {
				System.out.println("Workspace component: " + component.trim());
				this.components.add(component.trim());
			}
		}

	}

	public void buildP4SyncCommands(String perforceDepotPath) {

		try {
			Set<String> syncCommands = CondorUtils.getP4SyncCommands(release, buildNo);

			Set<String> perforcePaths = new HashSet<String>();
			// main pom
			perforcePaths.add(perforceDepotPath + Constansts.POM);
			// available.properties
			perforcePaths.add(perforceDepotPath + Constansts.AVAILABLE_PROPERTIES);
			// promoted.properties
			perforcePaths.add(perforceDepotPath + Constansts.PROMOTED_PROPERTIES);
			for (String component : components) {
				perforcePaths.add(perforceDepotPath + Constansts.COMPONENTS_FOLDER + Constansts.SLASH
						+ component.replaceAll("\\.", Constansts.SLASH) + Constansts.SLASH);
			}

			for (String path : perforcePaths) {
				for (String command : syncCommands) {

					if (command.contains(path)) {
						System.out.println("Adding command: " + command);
						p4SyncCommands.add(command);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Exception accorded while getting p4 sync commands from condor.");
			System.out.println(e.getMessage());
			System.exit(2);
		}
	}

	public void writeP4SyncCommandToFile(String p4SyncFileDirectory) throws IOException {

		if (p4SyncCommands.size() > 0) {
			String fileContent = Constansts.EMPTY;
			for (String command : p4SyncCommands) {
				fileContent += command + Constansts.NEWLINE;
			}
			File p4SyncFileDirectoryFile = new File(p4SyncFileDirectory);
			if (!p4SyncFileDirectoryFile.exists()) {
				p4SyncFileDirectoryFile.mkdir();
			}
			String fileName = p4SyncFileDirectory + HelperUtils.getFileSeparator(osType)
					+ HelperUtils.getScriptFileName("p4Sync",
							release.replaceAll("\\.", Constansts.EMPTY).replaceAll(" ", Constansts.EMPTY), p4Client,
							buildNo, osType);
			File file = new File(fileName);
			if (file.exists())
				file.delete();
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(fileContent);
			out.close();
			System.out.println("Successfully created the p4 sync script file :" + fileName);
			environments.put(Constansts.P4SYNC_SCRIPT, fileName);
		} else {
			System.out.println("No perforce sync command to write to the file.");
		}
	}

	public void buildWorkspaceEnvironments() {

		// JAVA_HOME
		environments.put(Constansts.JAVA_HOME, javaHome);

		// MVN_HOME
		environments.put(Constansts.MVN_HOME, mvnHome);

		environments.put(Constansts.BUILD, buildNo);

		environments.put(Constansts.P4SYNC, p4Sync);

		// RELEASE
		environments.put(Constansts.RELEASE,
				release.replaceAll("\\.", Constansts.EMPTY).replaceAll(" ", Constansts.EMPTY));

		if (infaMvnRepo != null) {
			environments.put(Constansts.INFA_MVN_REPO, infaMvnRepo);
		}

		if (platformDir != null) {
			environments.put(Constansts.PLATFORM_DIR, platformDir);
		}

		if (p4User != null) {
			environments.put(Constansts.P4USER, p4User);
		}

		if (p4Client != null) {
			environments.put(Constansts.P4CLIENT, p4Client);
		}
	}

	public void writeWorkspaceEnvFile(String envFile) throws IOException {

		String fileContent = Constansts.EMPTY;
		String prefix = HelperUtils.getEnvPrefix(osType);
		for (String key : environments.keySet()) {
			fileContent += prefix + key + "=" + environments.get(key) + Constansts.NEWLINE;
		}
		File file = new File(envFile);
		if (file.exists())
			file.delete();
		BufferedWriter out = new BufferedWriter(new FileWriter(envFile));
		out.write(fileContent);
		out.close();
		System.out.println("Successfully created the env file :" + envFile);
	}
}
