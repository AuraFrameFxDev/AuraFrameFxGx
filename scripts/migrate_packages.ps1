# AuraFrameFX Package Migration Script
# This script migrates packages from com.example.app to com.genesis.ai.app

param (
    [Parameter(Mandatory=$false)]
    [string]$sourceDir = "$PSScriptRoot/../app/src/main/java/com/example/app",
    
    [Parameter(Mandatory=$false)]
    [string]$targetDir = "$PSScriptRoot/../app/src/main/java/com/genesis/ai/app",
    
    [switch]$dryRun = $false
)

# Convert to absolute paths
$sourceDir = Resolve-Path -Path $sourceDir -ErrorAction Stop
$targetDir = Resolve-Path -Path $targetDir -ErrorAction SilentlyContinue

if (-not $targetDir) {
    $targetDir = Join-Path (Split-Path $sourceDir -Parent) "genesis/ai/app"
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
    $targetDir = Resolve-Path -Path $targetDir
}

Write-Host "Source Directory: $sourceDir" -ForegroundColor Cyan
Write-Host "Target Directory: $targetDir" -ForegroundColor Cyan
Write-Host "Dry Run: $dryRun" -ForegroundColor Cyan
Write-Host ""

# Function to update package declarations and imports in a file
function Update-FilePackage {
    param (
        [string]$filePath,
        [string]$oldPackage,
        [string]$newPackage
    )
    
    $content = Get-Content -Path $filePath -Raw
    $originalContent = $content
    
    # Update package declaration
    $content = $content -replace "package\s+$($oldPackage.Replace('.', '\.'))", "package $newPackage"
    
    # Update imports
    $content = $content -replace "import\s+$($oldPackage.Replace('.', '\.'))\.", "import ${newPackage}."
    
    # Update KSP and kapt references
    $content = $content -replace "$($oldPackage.Replace('.', '\\.'))\.", "${newPackage}."
    
    if ($content -ne $originalContent) {
        if (-not $dryRun) {
            Set-Content -Path $filePath -Value $content -NoNewline
        }
        return $true
    }
    
    return $false
}

# Function to process directory recursively
function Process-Directory {
    param (
        [string]$currentDir,
        [string]$baseSourceDir,
        [string]$baseTargetDir,
        [string]$relativePath = ""
    )
    
    $items = Get-ChildItem -Path $currentDir -Force
    
    foreach ($item in $items) {
        $relativeItemPath = if ($relativePath) { "$relativePath/$($item.Name)" } else { $item.Name }
        $targetPath = Join-Path $baseTargetDir $relativeItemPath
        
        if ($item.PSIsContainer) {
            # Process directory
            if (-not (Test-Path $targetPath)) {
                Write-Host "Creating directory: $targetPath" -ForegroundColor Green
                if (-not $dryRun) {
                    New-Item -ItemType Directory -Path $targetPath -Force | Out-Null
                }
            }
            
            Process-Directory -currentDir $item.FullName -baseSourceDir $baseSourceDir -baseTargetDir $baseTargetDir -relativePath $relativeItemPath
        } else {
            # Process file
            if ($item.Extension -in ".kt", ".java", ".xml") {
                $targetFile = $targetPath
                $shouldCopy = $true
                
                if (Test-Path $targetFile) {
                    $existingHash = (Get-FileHash $targetFile -Algorithm SHA256).Hash
                    $newHash = (Get-FileHash $item.FullName -Algorithm SHA256).Hash
                    
                    if ($existingHash -eq $newHash) {
                        $shouldCopy = $false
                        Write-Host "Skipping (identical): $($item.FullName)" -ForegroundColor DarkGray
                    } else {
                        Write-Host "Updating: $($item.FullName) -> $targetFile" -ForegroundColor Yellow
                    }
                } else {
                    Write-Host "Copying: $($item.FullName) -> $targetFile" -ForegroundColor Cyan
                }
                
                if ($shouldCopy -and -not $dryRun) {
                    Copy-Item -Path $item.FullName -Destination $targetFile -Force
                    
                    # Update package declarations in Kotlin/Java files
                    if ($item.Extension -in ".kt", ".java") {
                        $updated = Update-FilePackage -filePath $targetFile -oldPackage "com.example.app" -newPackage "com.genesis.ai.app"
                        if ($updated) {
                            Write-Host "  Updated package/imports in: $targetFile" -ForegroundColor Green
                        }
                    }
                }
            } else {
                Write-Host "Skipping non-source file: $($item.FullName)" -ForegroundColor DarkGray
            }
        }
    }
}

# Start processing
Write-Host "Starting package migration..." -ForegroundColor Magenta
Process-Directory -currentDir $sourceDir -baseSourceDir $sourceDir -baseTargetDir $targetDir

# Clean up empty directories in source
if (-not $dryRun) {
    Write-Host "Cleaning up empty directories..." -ForegroundColor Magenta
    Get-ChildItem -Path $sourceDir -Directory -Recurse | 
        Where-Object { $_.GetFiles('*', 'AllDirectories').Count -eq 0 } | 
        Sort-Object -Property FullName -Descending | 
        Remove-Item -Force -Recurse -ErrorAction SilentlyContinue
}

Write-Host ""
Write-Host "Migration complete!" -ForegroundColor Green
Write-Host "Source: $sourceDir"
Write-Host "Target: $targetDir"

if ($dryRun) {
    Write-Host "This was a dry run. No changes were made." -ForegroundColor Yellow
} else {
    # Update AndroidManifest.xml package if needed
    $manifestPath = "$PSScriptRoot/../app/src/main/AndroidManifest.xml"
    if (Test-Path $manifestPath) {
        $manifest = Get-Content $manifestPath -Raw
        $manifest = $manifest -replace 'package="[^"]*"', 'package="com.genesis.ai.app"'
        $manifest = $manifest -replace 'android:name="\.' , 'android:name="com.genesis.ai.app.'
        Set-Content -Path $manifestPath -Value $manifest -NoNewline
        Write-Host "Updated AndroidManifest.xml with new package name" -ForegroundColor Green
    }
    
    # Update build.gradle.kts if needed
    $buildGradlePath = "$PSScriptRoot/../app/build.gradle.kts"
    if (Test-Path $buildGradlePath) {
        $buildGradle = Get-Content $buildGradlePath -Raw
        $buildGradle = $buildGradle -replace 'namespace\s*=\s*"[^"]*"', 'namespace = "com.genesis.ai.app"'
        $buildGradle = $buildGradle -replace 'applicationId\s*=\s*"[^"]*"', 'applicationId = "com.genesis.ai.app"'
        Set-Content -Path $buildGradlePath -Value $buildGradle -NoNewline
        Write-Host "Updated build.gradle.kts with new package name" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Review the changes in your version control system"
Write-Host "2. Test the application thoroughly"
Write-Host "3. Commit the changes once verified"
