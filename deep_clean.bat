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

:: Clean Gradle and IDE files
echo Cleaning project...

:: Remove Gradle cache
if exist .gradle (
    echo Removing .gradle directory...
    rmdir /s /q .gradle
)

:: Remove build directories
if exist build (
    echo Removing build directory...
    rmdir /s /q build
)
if exist app\build (
    echo Removing app/build directory...
    rmdir /s /q app\build
)

:: Remove IDE files
if exist .idea (
    echo Removing .idea directory...
    rmdir /s /q .idea
)

:: Remove local.properties if exists
if exist local.properties (
    echo Removing local.properties...
    del /f /q local.properties
)

:: Remove Gradle wrapper and reinitialize
echo Reinitializing Gradle wrapper...
if exist gradlew (
    del /f /q gradlew
)
if exist gradlew.bat (
    del /f /q gradlew.bat
)
if exist gradle (
    rmdir /s /q gradle
)

:: Reinitialize Gradle wrapper
echo Recreating Gradle wrapper...
call gradle wrapper --gradle-version 8.5 --distribution-type bin

:: Run the build
echo Starting clean build...
call gradlew clean build --stacktrace --no-daemon --warning-mode all

:: Show Gradle properties
echo Gradle properties:
call gradlew properties --no-daemon | findstr "version"

pause
