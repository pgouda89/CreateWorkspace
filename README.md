### CreateWorkspace
Utility to create Informatica DEI workspace.

#### Pre requisites
##### JAVA
You need to install/copy java JDK and set JAVA_HOME as an environmental variable.

Windows:

    set JAVA_HOME=<JAVA Location on windows machine>
    set PATH=%JAVA_HOME%/bin;%PATH%

Linux:

    export JAVA_HOME=<JAVA location on Linux machine>
    export PATH=$JAVA_HOME/bin:$PATH
  
Run the following command in Cli after setting the required environmental variables to check your java version:

    java -version

##### Apache Maven
Required to install Apache maven 3.0 or above version.
Setting environmental variables to handle Cli:

Windows:

    set MVN_HOME=<Maven Location on windows machine>
    set PATH=%MVN_HOME%/bin;%PATH%

Linux:

    export MVN_HOME=<Maven location on Linux machine>
    export PATH=$MVN_HOME/bin:$PATH

Run the following command in Cli after setting the required environmental variables to check your maven version:

    mvn -version

##### Perforce
CreateWorkspace utility uses the perforce command line interface. P4v needs to be configured and you should have valid credentails.

Run the following command in Cli to verify perforce command availability:

    p4 help
    
#### Update before creating workspace


#### Working with Windows
Download the project from github.


#### Working with Linux
