set INFA_NATIVEDIR=C:\depends\%RELEASE%_%P4CLIENT%
set IMF_CPP_RESOURCE_PATH=%INFA_NATIVEDIR%\bin
set INFA_RESOURCES=%INFA_NATIVEDIR%\bin
set IFCONTENTMASTER_HOME=%INFA_NATIVEDIR%\DT\current
set EFFECTIVE_PLATFORM=WIN64
set MAVEN_OPTS= -Dinfa.platform=WIN64 -Xmx2048m -Dinfa.build_package=DEVEL -server -Xms256M -XX:GCTimeRatio=19 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+HeapDumpOnOutOfMemoryError -XX:ParallelGCThreads=4 -XX:NewRatio=2 -XX:MaxMetaspaceSize=192m -d64
set INFA_JAVA_OPTS=-Xmx640M  -XX:GCTimeRatio=19 -XX:MaxMetaspaceSize=192m -XX:+HeapDumpOnOutOfMemoryError -d64

REM set LANG for i18N cases
set G_FILENAME_ENCODING=@locale,UTF-8,ISO-8859-15,CP1252
set LANG=en_US.UTF-8
set LC_ALL=en_US.UTF-8
set NLS_LANG=American_America.AL32UTF8
set TEMP=C:\p4temp
set MVN_REPO=C:\mvnrepro\%RELEASE%_%P4CLIENT%
set SETTINGS_FILE=%~dp0\..\conf\settings_win.xml

IF "%INFA_MVN_REPO%"=="" (
SET INFA_MVN_REPO=http://infamvn:8081/nexus/content/groups/%RELEASE%OFFWIN64/
)

IF "%PLATFORM_DIR%"=="" (
SET PLATFORM_DIR=%MVN_REPO%\.platform
)


set PATH=%JAVA_HOME%\bin;%MVN_HOME%\bin;%INFA_NATIVEDIR%\bin;%IFCONTENTMASTER_HOME%\bin;%~dp0;%PATH%