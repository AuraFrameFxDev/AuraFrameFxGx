# Create target directory structure
$targetBase = "app\src\main\java\com\genesis\ai\app"
$sourceBase = "app\src\main\java\com\example\app"

# Create target directories
Write-Host "Creating target directory structure..." -ForegroundColor Cyan
$directories = @(
    "ai",
    "ai/agents",
    "ai/clients",
    "ai/config",
    "ai/context",
    "ai/error",
    "ai/memory",
    "ai/pipeline",
    "ai/services",
    "ai/task",
    "auth",
    "core",
    "data",
    "di",
    "initializers",
    "model",
    "network",
    "receivers",
    "security",
    "services",
    "system",
    "ui",
    "utils",
    "viewmodel",
    "xposed"
)

foreach ($dir in $directories) {
    $targetDir = Join-Path $targetBase $dir
    if (-not (Test-Path $targetDir)) {
        New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
        Write-Host "Created directory: $targetDir" -ForegroundColor Green
    }
}

# Function to copy and update files
function Migrate-Files {
    param (
        [string]$sourceDir,
        [string]$targetDir,
        [string]$oldPackage,
        [string]$newPackage
    )
    
    if (-not (Test-Path $sourceDir)) {
        Write-Host "Source directory not found: $sourceDir" -ForegroundColor Yellow
        return
    }
    
    $files = Get-ChildItem -Path $sourceDir -File -Recurse | Where-Object { $_.Extension -in ".kt", ".java", ".xml" }
    
    foreach ($file in $files) {
        $relativePath = $file.FullName.Substring($sourceDir.Length).TrimStart('\')
        $targetFile = Join-Path $targetDir $relativePath
        $targetDirPath = [System.IO.Path]::GetDirectoryName($targetFile)
        
        # Create target directory if it doesn't exist
        if (-not (Test-Path $targetDirPath)) {
            New-Item -ItemType Directory -Path $targetDirPath -Force | Out-Null
        }
        
        # Copy file
        Copy-Item -Path $file.FullName -Destination $targetFile -Force
        
        # Update package and imports if it's a Kotlin or Java file
        if ($file.Extension -in ".kt", ".java") {
            $content = Get-Content -Path $targetFile -Raw
            $updated = $false
            
            # Update package declaration
            if ($content -match "package\s+$([regex]::Escape($oldPackage))")
            {
                $content = $content -replace "package\s+$([regex]::Escape($oldPackage))", "package $newPackage"
                $updated = $true
            }
            
            # Update imports
            $importPattern = "import\s+$([regex]::Escape($oldPackage))\."
            if ($content -match $importPattern)
            {
                $content = $content -replace $importPattern, "import ${newPackage}."
                $updated = $true
            }
            
            if ($updated) {
                Set-Content -Path $targetFile -Value $content -NoNewline
                Write-Host "Updated: $targetFile" -ForegroundColor Green
            } else {
                Write-Host "Copied: $targetFile" -ForegroundColor Cyan
            }
        } else {
            Write-Host "Copied: $targetFile" -ForegroundColor Cyan
        }
    }
}

# Migrate main source files
Write-Host "`nMigrating main source files..." -ForegroundColor Magenta
Migrate-Files -sourceDir $sourceBase -targetDir $targetBase -oldPackage "com.example.app" -newPackage "com.genesis.ai.app"

# Update AndroidManifest.xml
$manifestPath = "app\src\main\AndroidManifest.xml"
if (Test-Path $manifestPath) {
    $manifest = Get-Content $manifestPath -Raw
    $manifest = $manifest -replace 'package="[^"]*"', 'package="com.genesis.ai.app"'
    $manifest = $manifest -replace 'android:name="\.' , 'android:name="com.genesis.ai.app.'
    Set-Content -Path $manifestPath -Value $manifest -NoNewline
    Write-Host "Updated AndroidManifest.xml with new package name" -ForegroundColor Green
}

# Update build.gradle.kts
$buildGradlePath = "app\build.gradle.kts"
if (Test-Path $buildGradlePath) {
    $buildGradle = Get-Content $buildGradlePath -Raw
    $buildGradle = $buildGradle -replace 'namespace\s*=\s*"[^"]*"', 'namespace = "com.genesis.ai.app"'
    $buildGradle = $buildGradle -replace 'applicationId\s*=\s*"[^"]*"', 'applicationId = "com.genesis.ai.app"'
    Set-Content -Path $buildGradlePath -Value $buildGradle -NoNewline
    Write-Host "Updated build.gradle.kts with new package name" -ForegroundColor Green
}

Write-Host "`nMigration complete!" -ForegroundColor Green
Write-Host "Please review the changes and test the application thoroughly." -ForegroundColor Yellow
Write-Host "Once verified, you can safely delete the old package directory: $sourceBase" -ForegroundColor Yellow
