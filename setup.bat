@echo off  

IF "%JAVA_HOME%" == "" CALL :javaIsNotSet
SET HELP=NO
IF "%1" ==" " SET HELP=YES
IF "%1" =="" SET HELP=YES
IF "%1" =="-help" SET HELP=YES
IF "%1" =="--help" SET HELP=YES
IF "%1" =="-h" SET HELP=YES

IF "%HELP%" =="YES" (
	call :printSetupHelp
	goto :onExit
)
SET PATH=%JAVA_HOME%\bin;%PATH%

SET envFile=%~sdp0\scripts\envFile_temp.bat

java %* -Dp4MapFile=%~sdp0\conf\perforceMap.properties -Dp4SyncFileDirectory=%~sdp0\scripts -DenvFile=%envFile% -jar %~sdp0\lib\BuildWorkspace.jar 

IF NOT "%ERRORLEVEL%" == "0" CALL :errorWhileRunning

SET P4SYNC=NO
CALL %envFile%

IF "%P4SYNC%"=="YES" (
ECHO Executing P4 sync commands.
CALL %P4SYNC_SCRIPT% 
)
IF "%P4SYNC%"=="TRUE" CALL %P4SYNC_SCRIPT%

IF "%P4SYNC%"=="NO" CALL :printP4

CALL %~sdp0\scripts\envFile_default.bat

SET SCRIPT_LOCATION=%~sdp0\scripts

SET CONF_LOCATION=%~sdp0\conf

REM create desktop shortcut
CALL :createDesktopShortcut %RELEASE% %P4CLIENT%
IF EXIST "%USERPROFILE%\Desktop\%RELEASE%_%P4CLIENT%.bat" ECHO Successfully created the Desktop shortcut : %USERPROFILE%\Desktop\%RELEASE%_%P4CLIENT%.bat
REM Done

echo Use following helper scripts:
echo 	generatePom	: To perform component:generate-poms-promoted
echo 	install		: To perform mvn install
echo 	mce 		: Maven component eclipse
goto :onExit

:createDesktopShortcut
IF EXIST "%USERPROFILE%\Desktop\%~1_%~2.bat" del %USERPROFILE%\Desktop\%~1_%~2.bat

more %envFile% > %USERPROFILE%\Desktop\%~1_%~2.bat
echo CALL %~sdp0\scripts\envFile_default.bat >> %USERPROFILE%\Desktop\%~1_%~2.bat
echo cmd /k >> %USERPROFILE%\Desktop\%~1_%~2.bat

del %envFile% /s /f /q
del %P4SYNC_SCRIPT% /s /f /q
EXIT /B

:printP4
ECHO Skipping p4 sync commands. Please set -Dp4sync=TRUE to execute p4 sync commands.
EXIT /B

:javaIsNotSet
echo "Set JAVA_HOME in the system environment and have %%JAVA_HOME%%\bin in the PATH."
goto :onExit

:printSetupHelp
java -jar %~sdp0\lib\BuildWorkspace.jar --help
goto :onExit


:errorWhileRunning
echo "Failed to setup the workspace. Please refer the previous error messages for the detail."
goto :onExit

:onExit
EXIT /B 2