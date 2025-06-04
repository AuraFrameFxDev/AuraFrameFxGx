@echo off
setlocal enabledelayedexpansion

echo Searching for JDK...

:: Check common JDK locations
set "jdk_path="

:: Check Android Studio's embedded JDK
if exist "%LOCALAPPDATA%\Android\Sdk\jbr" (
    set "jdk_path=%LOCALAPPDATA%\Android\Sdk\jbr"
) else if exist "%ProgramFiles%\Android\Android Studio\jbr" (
    set "jdk_path=%ProgramFiles%\Android\Android Studio\jbr"
) else if exist "%ProgramFiles%\Android\Android Studio\jre" (
    set "jdk_path=%ProgramFiles%\Android\Android Studio\jre"
) else if exist "%ProgramFiles%\Android\Android Studio\jdk" (
    set "jdk_path=%ProgramFiles%\Android\Android Studio\jdk"
) else if exist "%ProgramFiles(x86)%\Android\Android Studio\jbr" (
    set "jdk_path=%ProgramFiles(x86)%\Android\Android Studio\jbr"
) else if exist "%ProgramFiles(x86)%\Android\Android Studio\jre" (
    set "jdk_path=%ProgramFiles(x86)%\Android\Android Studio\jre"
) else if exist "%ProgramFiles(x86)%\Android\Android Studio\jdk" (
    set "jdk_path=%ProgramFiles(x86)%\Android\Android Studio\jdk"
) else (
    echo JDK not found in standard locations.
    echo Please install Android Studio or set JAVA_HOME manually.
    pause
    exit /b 1
)

echo Found JDK at: %jdk_path%
set "JAVA_HOME=%jdk_path%"
set "PATH=%jdk_path%\bin;%PATH%"

echo JAVA_HOME set to: %JAVA_HOME%
echo.

echo Verifying Java installation...
java -version
if %ERRORLEVEL% neq 0 (
    echo Java verification failed. Please check your Java installation.
    pause
    exit /b 1
)

echo.
echo Building project...
call gradlew clean build --stacktrace

if %ERRORLEVEL% neq 0 (
    echo Build failed. Check the error messages above.
    pause
    exit /b 1
) else (
    echo Build completed successfully!
)

pause
