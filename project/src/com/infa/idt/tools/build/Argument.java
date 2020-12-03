package com.infa.idt.tools.build;

public enum Argument {

	buildNo("buildNo", "Build number that you want to build. For example: -DbuildNo=100 or -DbuildNo=latest"),

	release("release", "Product release version. For example: -Drelease=10.4.0 or -Drelease=10.2.201 for \"10.2.2 HF1\""),

	javaHome("javaHome",
			"Java JDK installation location. Execution will use JAVA_HOME environmental variable if -DjavaHome not passed in the argument line. For Example: -DjavaHome=C:\\java"),

	mvnHome("mvnHome",
			"Maven installation location. Execution will honor MVN_HOME environmental variable if -DmvnHome not passed in the argument line. For Example: -DmvnHome=C\\apache-maven"),

	platformDir("platformDir",
			"Component eclipse will use platformDir to set -Ddirectory. For example -DplatformDir=C\\repro\\.platform"),

	components("components",
			"List of components you want to build on your workspace. For example: -Dcomponents=dtm.ldtm,datapreview,tools.core"),
	p4Sync("p4Sync", "Whether to sync files from perforce. NO by default"),

	p4User("p4User", "Perforce username. P4USER environment variable will be used by default."),

	p4Client("p4Client", "Perforce workspace name. P4CLIENT environment variable will be used by default."),
	
	infaMvnRepo("infaMvnRepo",
			"Maven repo location for the release. For example: -DinfaMvnRepo=http://infamvn:8081/nexus/content/groups/1040OFFLIN64/");

	private String name;

	private String description;

	Argument(String argName, String description) {
		this.name = argName;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
