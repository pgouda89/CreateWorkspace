@echo on 
set IMVN_OPTS=-Dinfa.platform=WIN64 -Dinfa.build_package=DEVEL -Dinfa.nativeDir=%INFA_NATIVEDIR%  -Dinfa.build_config=RELEASE  -e -Dinfa.useIncredibuild=false -Dmaven.test.error.ignore=true -DbuildLocation=%MVN_REPO%\buildLocation -DeclipseInstall=%MVN_REPO%\eclipse -Dmaven.test.failure.ignore=true -Dfile.encoding=UTF-8 
"%MVN_HOME%\bin\mvn" -s "%SETTINGS_FILE%" %IMVN_OPTS% %* 
