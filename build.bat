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

:: Clean Gradle cache
echo Cleaning Gradle cache...
call gradlew clean --no-daemon

:: Run Gradle with the system proxy settings
echo Starting Gradle build...
call gradlew build --stacktrace --no-daemon -Dorg.gradle.debug=true

:: If build fails, show the Gradle properties
echo Gradle properties:
call gradlew properties --no-daemon | findstr "version"

pause
