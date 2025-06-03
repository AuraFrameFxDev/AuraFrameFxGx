@echo off
setlocal

:: Set Java home to Android Studio's embedded JDK
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr
set PATH=%JAVA_HOME%\bin;%PATH%

echo Java Version:
java -version
echo.

:: Clean Gradle caches
echo Cleaning Gradle caches...
if exist .gradle (
    rmdir /s /q .gradle
)

:: Clean build directories
echo Cleaning build directories...
if exist build (
    rmdir /s /q build
)
if exist app\build (
    rmdir /s /q app\build
)

:: Stop any running Gradle daemons
echo Stopping Gradle daemons...
call gradlew --stop

:: Run the build with debug output
echo Starting build with debug output...
call gradlew clean assembleDebug --stacktrace --info --no-daemon

:: If build fails, show Gradle properties
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Build failed. Showing Gradle properties:
    call gradlew properties --no-daemon | findstr "version"
)

pause
