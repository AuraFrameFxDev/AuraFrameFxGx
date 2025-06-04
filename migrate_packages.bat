@echo off
setlocal enabledelayedexpansion

REM Set source and target directories
set "SOURCE_DIR=app\src\main\java\com\example\app"
set "TARGET_DIR=app\src\main\java\com\genesis\ai\app"
set "OLD_PKG=com.example.app"
set "NEW_PKG=com.genesis.ai.app"

echo Creating target directory structure...
if not exist "%TARGET_DIR%" mkdir "%TARGET_DIR%"

REM Create subdirectories
for /d /r "%SOURCE_DIR%" %%d in (*) do (
    set "relpath=%%~d"
    set "relpath=!relpath:%SOURCE_DIR%=!"
    if not "!relpath!"=="" (
        if not exist "%TARGET_DIR%!relpath!" mkdir "%TARGET_DIR%!relpath!"
    )
)

echo Copying and updating source files...
for /r "%SOURCE_DIR%" %%f in (*.kt *.java) do (
    set "src=%%f"
    set "dest=!src:%SOURCE_DIR%=%TARGET_DIR%!"
    
    echo Copying: %%f
    copy /y "%%f" "!dest!" >nul
    
    echo Updating package in: !dest!
    powershell -Command "(Get-Content '!dest!' -Raw) -replace 'package %OLD_PKG%', 'package %NEW_PKG%' | Set-Content '!dest!' -NoNewline"
    powershell -Command "(Get-Content '!dest!' -Raw) -replace 'import %OLD_PKG%', 'import %NEW_PKG%' | Set-Content '!dest!' -NoNewline"
)

echo Updating AndroidManifest.xml...
if exist "app\src\main\AndroidManifest.xml" (
    powershell -Command "(Get-Content 'app\src\main\AndroidManifest.xml' -Raw) -replace 'package="[^"]*"', 'package="%NEW_PKG%"' | Set-Content 'app\src\main\AndroidManifest.xml' -NoNewline"
    powershell -Command "(Get-Content 'app\src\main\AndroidManifest.xml' -Raw) -replace 'android:name="\.', 'android:name="%NEW_PKG%.' | Set-Content 'app\src\main\AndroidManifest.xml' -NoNewline"
)

echo Updating build.gradle.kts...
if exist "app\build.gradle.kts" (
    powershell -Command "(Get-Content 'app\build.gradle.kts' -Raw) -replace 'namespace\s*=\s*\"[^\"]*\"', 'namespace = \"%NEW_PKG%\"' | Set-Content 'app\build.gradle.kts' -NoNewline"
    powershell -Command "(Get-Content 'app\build.gradle.kts' -Raw) -replace 'applicationId\s*=\s*\"[^\"]*\"', 'applicationId = \"%NEW_PKG%\"' | Set-Content 'app\build.gradle.kts' -NoNewline"
)

echo.
echo Migration complete!
echo Please review the changes and test the application.
echo Once verified, you can safely delete the old package directory: %SOURCE_DIR%

pause
