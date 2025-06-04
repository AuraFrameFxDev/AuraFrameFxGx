@echo off
setlocal enabledelayedexpansion

set "old_package=com.example.app"
set "new_package=com.genesis.ai.app"
set "old_path=app\src\main\java\com\example\app"
set "new_path=app\src\main\java\com\genesis\ai\app"

REM Create new directory structure
if not exist "%new_path%" mkdir "%new_path%"

REM Copy files to new location
xcopy /E /I /Y "%old_path%" "%new_path%"

echo Files copied to new package structure.
echo.
echo Updating package declarations...

REM Update package declarations in all Kotlin and Java files
for /r "%new_path%" %%f in (*.kt *.java) do (
    powershell -Command "(Get-Content '%%f') -replace 'package com\.example\.app', 'package com.genesis.ai.app' | Set-Content '%%f' -Encoding UTF8"
    echo Updated: %%~nxf
)

echo.
echo Package update complete!
echo Please sync your project with Gradle files.

pause
