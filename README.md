### CreateWorkspace
Utility to create Informatica Data Engineering developement workspace.
What to expect from the Utility:
   1. Create new workspace
   2. Update an existing workspace to a specified build
   3. Creates a script file to configure console environmental variables
   4. Configurs shortcuts to perform
      a. Pom generation
      b. Maven install
      c. Maven component eclipse
   5. Works for both Windows and Linux machine.
  

**Table of Contents**  

- [Pre requisites](#pre-requisites)
  - [JAVA](#java)
  - [Apache Maven](#apache-maven)
  - [Perforce](#perforce)
- [Getting started](#getting-started)
  - [Before setup script](#before-setup-script)
  - [Execute setup script](#execute-setup-script)
  - [Post Setup script](#post-setup-script)

#### Pre requisites
##### JAVA
You need to install/copy java JDK and set JAVA_HOME as an environmental variable.

Windows:

    set JAVA_HOME=<JAVA Location on windows machine>
    set PATH=%JAVA_HOME%/bin;%PATH%

Linux:

    export JAVA_HOME=<JAVA location on Linux machine>
    export PATH=$JAVA_HOME/bin:$PATH
  
Run the following command in command line interface after setting the required environmental variables to check the java version:

    java -version

##### Apache Maven
Required to install Apache maven 3.0 or above version.
Set the following environmental variables to handle command line interface:

Windows:

    set MVN_HOME=<Maven Location on windows machine>
    set PATH=%MVN_HOME%/bin;%PATH%

Linux:

    export MVN_HOME=<Maven location on Linux machine>
    export PATH=$MVN_HOME/bin:$PATH

Run the following command in command line interface after setting the required environmental variables to check the maven version:

    mvn -version

##### Perforce
CreateWorkspace utility uses the perforce command line interface. P4v needs to be configured and you should have valid credentails.

Run the following command in command line interface to verify perforce command availability:

    p4 help
    
#### Getting started
Download/clone the project from github. 

##### Before setup script
a. Make sure conf/perforceMap.properties has an entry for the Informatica product version you want to build.
b. 

##### Execute setup script
Execute the following command to list all command line arguments that the setup script supports:

Windows:

       setup.bat --help

Linux:
    
      source setup.sh --help

| Argument| Type| Description | 
|----------|---|---------------|
|release|Required|Product release version. You can fetch version details from http://condor/?tab=Dashboard. For example: -Drelease=10.4.0 or -Drelease=10.2.201 for "10.2.2 HF1"|
|components|Required|List of components you want to build on your workspace. For example: -Dcomponents=dtm.ldtm,datapreview,tools.core|
|buildNo| Optional|Product build number. By default the utility will use latest build number. For example: -DbuildNo=100 or -DbuildNo=latest      |
|javaHome|Optional|Java JDK installation location. It will use JAVA_HOME environmental variable if -DjavaHome not passed in the argument line. For Example: -DjavaHome=C:\java  |
|mvnHome|Optional|Maven installation location. Execution will honor MVN_HOME environmental variable if -DmvnHome not passed in the argument line. For Example: -DmvnHome=C\apache-maven|
|platformDir|Optional|Component eclipse will use platformDir to set -Ddirectory. For example -DplatformDir=C\repro\.platform|
|p4Sync|Optional|Whether to sync files from perforce. NO by default. For Example: -Dp4Sync=YES|
|p4User|Optional|Perforce username. P4USER environment variable will be used by default.|
|p4Client|Optional|Perforce workspace name. P4CLIENT environment variable will be used by default.|
|infaMvnRepo|Optional|Maven repo location for the release. For example: -DinfaMvnRepo=http://infamvn:8081/nexus/content/groups/1040OFFLIN64/|

Example Command:

    setup.bat -Drelease=10.4.0 -DbuildNo=107 -Dcomponents=dtm.ldtm -Dp4User=<user> -Dp4Client=<Client name> -DjavaHome=<javaJdkHome> -DmvnHome=<mvnHomeLocation> -Dp4Sync=YES -DplatformDir=<Platform directory>

##### Post Setup script
After the successful setup script execution
a. Windows - A batch script under %USERPROFILE%\Desktop\<release>\.
b. Linux - A shell script under $HOME directory.

The following shortcut scripts are already configured:

| Script| Description | 
|----------|---------------|
|generatePom|Shortcut to execute component:generate-poms-promoted|
|infamvn|Shortcut to execute mvn with Informatica related arguments|
|install|Shortcut to execute infamvn clean install with -Dmaven.test.skip=true|
|mce|Shortcut to execute infamvn component:eclipse|


