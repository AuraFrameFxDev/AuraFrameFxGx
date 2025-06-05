@echo off
REM Check for JDK in common locations
if exist "C:\Program Files\Java\jdk-17" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-17"
    goto found
)

if exist "C:\Program Files\Java\jdk-21" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-21"
    goto found
)

if exist "C:\Program Files\Android\Android Studio\jbr" (
    set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
    goto found
)

if exist "C:\Program Files\Android\Android Studio\jre" (
    set "JAVA_HOME=C:\Program Files\Android\Android Studio\jre"
    goto found
)

echo JDK not found in common locations. You'll need to set JAVA_HOME manually to the location of your JDK.
goto end

:found
echo Setting JAVA_HOME to %JAVA_HOME%
echo JAVA_HOME has been set for this session.
echo You can now run './gradlew clean build' to build your project.

:end
