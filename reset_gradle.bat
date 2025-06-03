@echo off
setlocal

echo Stopping Gradle daemons...
call gradlew --stop

echo Cleaning Gradle caches...
if exist .gradle (
    rmdir /s /q .gradle
)

if exist build (
    rmdir /s /q build
)

if exist app\build (
    rmdir /s /q app\build
)

echo Recreating Gradle wrapper...
if exist gradlew (
    del /f /q gradlew
)
if exist gradlew.bat (
    del /f /q gradlew.bat
)
if exist gradle (
    rmdir /s /q gradle
)

:: Recreate gradle wrapper
call gradle wrapper --gradle-version 8.5 --distribution-type bin

echo.
echo Please close all instances of Android Studio and any terminals.
echo Then run: .\gradlew clean build

pause
