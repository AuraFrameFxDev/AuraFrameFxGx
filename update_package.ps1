# Create the new directory structure
$newBase = "app/src/main/java/com/genesis/ai/app"
New-Item -ItemType Directory -Force -Path $newBase

# Move all files from old package to new package
$oldPackage = "app/src/main/java/com/example/app"
if (Test-Path $oldPackage) {
    Get-ChildItem -Path $oldPackage -Recurse -File | ForEach-Object {
        $newPath = $_.FullName.Replace("com/example/app", "com/genesis/ai/app")
        $newDir = [System.IO.Path]::GetDirectoryName($newPath)
        
        if (!(Test-Path $newDir)) {
            New-Item -ItemType Directory -Force -Path $newDir | Out-Null
        }
        
        Move-Item -Path $_.FullName -Destination $newPath -Force
    }
    
    # Remove old empty directories
    Get-ChildItem -Path "app/src/main/java/com/example" -Recurse -Directory | 
        Where-Object { $_.GetFiles().Count -eq 0 -and $_.GetDirectories().Count -eq 0 } | 
        Sort-Object FullName -Descending | 
        Remove-Item -Force
}

# Update package declarations in all Kotlin and Java files
Get-ChildItem -Path "app/src" -Include "*.kt", "*.java" -Recurse | ForEach-Object {
    $content = Get-Content -Path $_.FullName -Raw
    $updatedContent = $content -replace 'package com\.example\.app', 'package com.genesis.ai.app'
    $updatedContent = $updatedContent -replace 'import com\.example\.app', 'import com.genesis.ai.app'
    
    if ($updatedContent -ne $content) {
        Set-Content -Path $_.FullName -Value $updatedContent -NoNewline
        Write-Host "Updated: $($_.FullName)"
    }
}

Write-Host "Package update complete!"
