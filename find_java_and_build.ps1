# Find Java installations
Write-Host "Searching for Java installations..."
$javaPaths = @(
    "C:\Program Files\Android\Android Studio\jbr",
    "C:\Program Files\Android\Android Studio\jre",
    "C:\Program Files\Java",
    "${env:ProgramFiles}\Java",
    "${env:ProgramFiles(x86)}\Java"
)

$validJavaPaths = @()

foreach ($path in $javaPaths) {
    if (Test-Path $path) {
        Write-Host "Checking: $path"
        $javaExe = Get-ChildItem -Path $path -Filter "java.exe" -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($javaExe) {
            $javaHome = $javaExe.Directory.Parent.FullName
            Write-Host "  Found Java at: $javaHome"
            $version = & $javaExe.FullName -version 2>&1
            Write-Host "  Version: $version"
            $validJavaPaths += $javaHome
        }
    }
}

if ($validJavaPaths.Count -eq 0) {
    Write-Host "No Java installations found. Please install JDK 17 or higher."
    exit 1
}

# Set JAVA_HOME to the first valid Java installation
$javaHome = $validJavaPaths[0]
$env:JAVA_HOME = $javaHome
Write-Host "`nUsing Java from: $javaHome"

# Add Java to PATH
$env:Path = "$javaHome\bin;" + $env:Path

# Verify Java version
Write-Host "`nJava version:"
java -version

# Run the build
Write-Host "`nBuilding the project..."
.\gradlew clean assembleDebug --stacktrace --info

# If build fails, show Gradle properties
if ($LASTEXITCODE -ne 0) {
    Write-Host "`nBuild failed. Showing Gradle properties:"
    .\gradlew properties --no-daemon | Select-String -Pattern "version"
}
