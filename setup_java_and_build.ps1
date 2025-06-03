# Find Java installations
$javaHomes = @()

# Check common Java installation paths
$possibleJavaPaths = @(
    "${env:ProgramFiles}\Android\Android Studio\jbr",
    "${env:ProgramFiles}\Android\Android Studio\jre",
    "${env:ProgramFiles}\Android\Android Studio\jbr17",
    "${env:ProgramFiles}\Android\Android Studio\jre17",
    "${env:ProgramFiles}\Android\Android Studio\jbr11",
    "${env:ProgramFiles}\Android\Android Studio\jre11",
    "${env:ProgramFiles}\Android\Android Studio\jbr8",
    "${env:ProgramFiles}\Android\Android Studio\jre8",
    "${env:ProgramFiles}\Java\jdk-*",
    "${env:ProgramFiles}\Java\jre-*",
    "${env:ProgramFiles(x86)}\Android\Android Studio\jbr",
    "${env:ProgramFiles(x86)}\Android\Android Studio\jre",
    "${env:ProgramFiles(x86)}\Java\jdk-*",
    "${env:ProgramFiles(x86)}\Java\jre-*"
)

# Find Java installations
foreach ($path in $possibleJavaPaths) {
    if (Test-Path $path) {
        $javaHomes += (Get-ChildItem -Path $path -Directory -Force | Select-Object -ExpandProperty FullName)
    }
}

# If no Java found, try to find it in PATH
if ($javaHomes.Count -eq 0) {
    $javaExe = Get-Command java -ErrorAction SilentlyContinue
    if ($javaExe) {
        $javaHome = Split-Path (Split-Path $javaExe.Source)
        $javaHomes += $javaHome
    }
}

# If still no Java found, show error
if ($javaHomes.Count -eq 0) {
    Write-Host "No Java installation found. Please install Java JDK 11 or later and set JAVA_HOME environment variable." -ForegroundColor Red
    exit 1
}

# Use the first found Java installation
$selectedJavaHome = $javaHomes[0]
$env:JAVA_HOME = $selectedJavaHome
$env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH

Write-Host "Using Java from: $env:JAVA_HOME" -ForegroundColor Green

# Verify Java version
$javaVersion = & "$env:JAVA_HOME\bin\java" -version 2>&1 | Select-Object -First 1
Write-Host "Java version: $javaVersion" -ForegroundColor Green

# Clean Gradle caches
Write-Host "Cleaning Gradle caches..." -ForegroundColor Cyan
if (Test-Path "$PSScriptRoot\.gradle") {
    Remove-Item -Path "$PSScriptRoot\.gradle" -Recurse -Force -ErrorAction SilentlyContinue
}

# Clean build directories
Write-Host "Cleaning build directories..." -ForegroundColor Cyan
if (Test-Path "$PSScriptRoot\build") {
    Remove-Item -Path "$PSScriptRoot\build" -Recurse -Force -ErrorAction SilentlyContinue
}
if (Test-Path "$PSScriptRoot\app\build") {
    Remove-Item -Path "$PSScriptRoot\app\build" -Recurse -Force -ErrorAction SilentlyContinue
}

# Stop Gradle daemons
Write-Host "Stopping Gradle daemons..." -ForegroundColor Cyan
& "$PSScriptRoot\gradlew" --stop

# Sync project with Gradle files
Write-Host "Syncing project..." -ForegroundColor Cyan
& "$PSScriptRoot\gradlew" --refresh-dependencies

# Build the project
Write-Host "Building project..." -ForegroundColor Cyan
& "$PSScriptRoot\gradlew" clean build --stacktrace --info

# Check build result
if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed. Showing Gradle properties..." -ForegroundColor Red
    & "$PSScriptRoot\gradlew" properties --no-daemon | Select-String "version"
    exit 1
} else {
    Write-Host "Build completed successfully!" -ForegroundColor Green
}
