#!/bin/bash

function displayHelp(){
	${JAVA_HOME}/bin/java -jar $(pwd)/lib/BuildWorkspace.jar --help
	SKIP=TRUE
}

function error(){
	echo "Failed to setup the workspace. Please refer the previous error messages for the detail."
	SKIP=TRUE
}
if [[ -z "${JAVA_HOME}" ]]; then
	echo "Set JAVA_HOME to JDK before starting setup script"
	SKIP=TRUE
fi

SKIP=FALSE

if [ "$#" -eq 0 ] || [ "$1" == "-help" ] || [ "$1" == "--help" ] || [ "$1" == "-h" ]; then
    displayHelp
fi

#Function to set default environment
function setDefaultEnv() {
	if [ "${INFA_MVN_REPO}" == "" ] ; then
		export INFA_MVN_REPO="http://infamvn:8081/nexus/content/groups/${RELEASE}OFFLIN64/"
	fi
	export MVN_REPO=$HOME/mvnreposit/${RELEASE}_${P4CLIENT}
	if [ "${PLATFORM_DIR}" == "" ] ; then
		export PLATFORM_DIR="$HOME/mvnreposit/${RELEASE}_${P4CLIENT}/.platform"
	fi
	
	export INFA_NATIVEDIR=$HOME/depends/${RELEASE}_${P4CLIENT}
	export INFA_RESOURCES=$INFA_NATIVEDIR/bin
	export IMF_CPP_RESOURCE_PATH=$INFA_NATIVEDIR/bin
	export LD_LIBRARY_PATH=$INFA_NATIVEDIR/bin:$LD_LIBRARY_PATH
	export SETTINGS_FILE=$(pwd)/conf/settings_lin.xml
	export PATH=$(pwd)/scripts:$JAVA_HOME/bin:$MVN_HOME/bin:$P4_HOME/bin:$INFA_NATIVEDIR/bin:$PATH
}

if [ "$SKIP" == "FALSE" ]; then
	export envFile=$(pwd)/scripts/envFile_temp.sh
	${JAVA_HOME}/bin/java $@ -Dp4MapFile=$(pwd)/conf/perforceMap.properties -Dp4SyncFileDirectory=$(pwd)/scripts -DenvFile=${envFile}  -jar $(pwd)/lib/BuildWorkspace.jar

	STATUS="${?}"

	if [ "${STATUS}" -ne "0" ]; then
		error
	fi

	chmod -R 777 $(pwd)/scripts/*
	export P4SYNC=NO
	source ${envFile}

	if [ "${P4SYNC}" == "YES" ]; then
		sh ${P4SYNC_SCRIPT}
	fi

	STATUS="${?}"

	if [ "${STATUS}" -ne "0" ]; then
		error
	fi

	rm ${P4SYNC_SCRIPT}

	#Create directory if not exists
	export scriptDir=$(pwd)
	mkdir -p $scriptDir
	setDefaultEnv
fi
