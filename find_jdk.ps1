# Check for common JDK locations
$possibleJdkPaths = @(
    "$env:LOCALAPPDATA\Android\Sdk\jbr",
    "$env:LOCALAPPDATA\Android\Sdk\jdk",
    "$env:ProgramFiles\Android\Android Studio\jbr",
    "$env:ProgramFiles\Android\Android Studio\jre",
    "$env:ProgramFiles\Android\Android Studio\jdk"
)

# Check if any JDK exists in common locations
$jdkPath = $null
foreach ($path in $possibleJdkPaths) {
    if (Test-Path $path) {
        $jdkPath = $path
        break
    }
}

if ($jdkPath) {
    Write-Host "Found JDK at: $jdkPath"
    # Set JAVA_HOME for the current session
    $env:JAVA_HOME = $jdkPath
    $env:Path = "$jdkPath\bin;" + $env:Path
    
    # Verify Java is accessible
    try {
        $javaVersion = & "$jdkPath\bin\java" -version 2>&1
        Write-Host "Java version:"
        $javaVersion
        
        # Run Gradle clean
        Write-Host "Running Gradle clean..."
        .\gradlew clean
    } catch {
        Write-Host "Error running Java: $_"
    }
} else {
    Write-Host "JDK not found in common locations. Please install Android Studio or set JAVA_HOME manually."
    Write-Host "Common locations checked:"
    $possibleJdkPaths | ForEach-Object { Write-Host "- $_" }
}
