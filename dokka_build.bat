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

:: Run Dokka
echo Running Dokka documentation generation...
call gradlew :app:dokkaHtml --info

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Dokka generation failed. See the output above for details.
    echo.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Dokka documentation has been generated at: %cd%\app\build\dokka
echo.

:: Open the documentation in default browser
start "" "%cd%\app\build\dokka\index.html"

pause
