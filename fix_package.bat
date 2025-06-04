@echo off
echo Updating package structure...

set "old_package=com.example.app"
set "new_package=com.genesis.ai.app"
set "old_path=app\src\main\java\com\example\app"
set "new_path=app\src\main\java\com\genesis\ai\app"

REM Create new directory structure
if not exist "%new_path%" mkdir "%new_path%"

REM Copy all files to new location (excluding the new path)
robocopy "%old_path%" "%new_path%" /E /MOVE /XD "%new_path%"

echo Package structure updated.
echo Updating package declarations in source files...

REM Update package declarations in all Kotlin files
for /r "%new_path%" %%f in (*.kt) do (
    powershell -Command "(Get-Content '%%f') -replace 'package com\.example\.app', 'package com.genesis.ai.app' | Set-Content '%%f' -Encoding UTF8"
    echo Updated: %%~nxf
)

echo Package update complete!
pause
