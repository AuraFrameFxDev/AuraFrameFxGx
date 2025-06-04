@echo off
setlocal enabledelayedexpansion

echo Creating target directory structure...
mkdir "app\src\main\java\com\genesis\ai\app" 2>nul

xcopy /E /I /Y "app\src\main\java\com\example\app\*" "app\src\main\java\com\genesis\ai\app\"

echo Updating package declarations in Kotlin/Java files...
for /r "app\src\main\java\com\genesis\ai\app" %%f in (*.kt) do (
    echo Updating: %%~nxf
    powershell -Command "(Get-Content '%%f' -Raw) -replace 'package com\\.example\\.app', 'package com.genesis.ai.app' | Set-Content '%%f' -NoNewline"
    powershell -Command "(Get-Content '%%f' -Raw) -replace 'import com\\.example\\.app', 'import com.genesis.ai.app' | Set-Content '%%f' -NoNewline"
)

echo.
echo Migration complete!
echo Please review the changes and test the application.
echo Once verified, you can safely delete the old package directory: app\src\main\java\com\example\app
echo.
pause
