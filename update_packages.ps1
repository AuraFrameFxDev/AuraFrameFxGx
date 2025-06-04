# Update package declarations in all Kotlin and Java files
$files = Get-ChildItem -Path "app/src" -Include "*.kt", "*.java" -Recurse -File

foreach ($file in $files) {
    $content = Get-Content -Path $file.FullName -Raw
    $updatedContent = $content -replace 'package com\.example\.app', 'package com.genesis.ai.app'
    $updatedContent = $updatedContent -replace 'import com\.example\.app', 'import com.genesis.ai.app'
    
    if ($updatedContent -ne $content) {
        Set-Content -Path $file.FullName -Value $updatedContent -NoNewline -Encoding UTF8
        Write-Host "Updated: $($file.FullName)"
    }
}

# Move files to new package structure
$oldBase = "app/src/main/java/com/example/app"
$newBase = "app/src/main/java/com/genesis/ai/app"

if (Test-Path $oldBase) {
    # Create new directory structure
    New-Item -ItemType Directory -Force -Path $newBase | Out-Null
    
    # Copy all files to new location
    Get-ChildItem -Path $oldBase -Recurse -File | ForEach-Object {
        $newPath = $_.FullName.Replace($oldBase, $newBase)
        $newDir = [System.IO.Path]::GetDirectoryName($newPath)
        
        if (!(Test-Path $newDir)) {
            New-Item -ItemType Directory -Force -Path $newDir | Out-Null
        }
        
        Copy-Item -Path $_.FullName -Destination $newPath -Force
    }
}

Write-Host "Package update complete!"
