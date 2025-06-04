# Fix package names and structure
Write-Host "Fixing package names and structure..." -ForegroundColor Cyan

# Set paths
$projectRoot = "$PSScriptRoot"
$javaRoot = "$projectRoot\app\src\main\java"
$oldPackage = "com.example.app"
$newPackage = "com.genesis.ai.app"
$oldPath = "$javaRoot\com\example\app"
$newPath = "$javaRoot\com\genesis\ai\app"

# Create new directory structure
Write-Host "Creating new package structure..."
New-Item -ItemType Directory -Force -Path $newPath | Out-Null

# Copy files to new location
Write-Host "Copying files to new package structure..."
Copy-Item -Path "$oldPath\*" -Destination $newPath -Recurse -Force

# Update package declarations
Write-Host "Updating package declarations..."
Get-ChildItem -Path $newPath -Recurse -Include "*.kt","*.java" | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw
    $updatedContent = $content -replace [regex]::Escape($oldPackage), $newPackage
    if ($updatedContent -ne $content) {
        Set-Content -Path $_.FullName -Value $updatedContent -NoNewline -Encoding UTF8
        Write-Host "Updated: $($_.FullName)" -ForegroundColor Green
    }
}

# Update imports in all source files
Write-Host "Updating imports..."
Get-ChildItem -Path $newPath -Recurse -Include "*.kt","*.java" | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw
    $updatedContent = $content -replace [regex]::Escape($oldPackage), $newPackage
    if ($updatedContent -ne $content) {
        Set-Content -Path $_.FullName -Value $updatedContent -NoNewline -Encoding UTF8
    }
}

Write-Host "Package update complete!" -ForegroundColor Green
Write-Host "Please sync your project with Gradle files."
