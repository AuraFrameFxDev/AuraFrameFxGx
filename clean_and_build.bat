@echo off
setlocal

:: Set JAVA_HOME to Android Studio's JetBrains JDK
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr

:: Add JDK bin to PATH
set PATH=%JAVA_HOME%\bin;%PATH%

:: Verify Java version
echo Java Version:
java -version
echo.

:: Stop any running Gradle daemons
echo Stopping Gradle daemons...
call gradlew --stop

:: Clean Gradle cache
echo Cleaning Gradle cache...
if exist .gradle (
    rmdir /s /q .gradle
)
if exist .idea\libraries (
    rmdir /s /q .idea\libraries
)
if exist .idea\workspace.xml (
    del /f /q .idea\workspace.xml
)

:: Run Gradle with the system proxy settings
echo Starting Gradle build...
call gradlew clean build --stacktrace --no-daemon --warning-mode all

:: If build fails, show the Gradle properties
echo Gradle properties:
call gradlew properties --no-daemon | findstr "version"

pause
