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

:: Remove build directories
echo Cleaning build directories...
if exist build (
    rmdir /s /q build
)
if exist app\build (
    rmdir /s /q app\build
)

:: Run the build
echo Starting clean build...
call gradlew clean build --stacktrace --no-daemon --warning-mode all

:: If build fails, show the Gradle properties
echo Gradle properties:
call gradlew properties --no-daemon | findstr "version"

pause
