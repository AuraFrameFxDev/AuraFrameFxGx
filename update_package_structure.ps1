# Create new directory structure
$newBase = "app/src/main/java/com/genesis/ai/app"
New-Item -ItemType Directory -Force -Path $newBase | Out-Null

# Copy all files from old package to new package
$oldBase = "app/src/main/java/com/example/app"
if (Test-Path $oldBase) {
    # Copy all files recursively
    Get-ChildItem -Path $oldBase -Recurse -File | ForEach-Object {
        $newPath = $_.FullName.Replace($oldBase, $newBase)
        $newDir = [System.IO.Path]::GetDirectoryName($newPath)
        
        if (!(Test-Path $newDir)) {
            New-Item -ItemType Directory -Force -Path $newDir | Out-Null
        }
        
        Copy-Item -Path $_.FullName -Destination $newPath -Force
    }
    
    # Update package declarations in all Kotlin files
    Get-ChildItem -Path $newBase -Recurse -Filter "*.kt" | ForEach-Object {
        $content = Get-Content -Path $_.FullName -Raw
        $updatedContent = $content -replace 'package com\.example\.app', 'package com.genesis.ai.app'
        $updatedContent = $updatedContent -replace 'import com\.example\.app', 'import com.genesis.ai.app'
        
        if ($updatedContent -ne $content) {
            Set-Content -Path $_.FullName -Value $updatedContent -NoNewline
            Write-Host "Updated package in: $($_.FullName)"
        }
    }
    
    Write-Host "Package structure updated successfully!"
    Write-Host "Old package remains at: $oldBase"
    Write-Host "New package created at: $newBase"
} else {
    Write-Host "Old package directory not found: $oldBase"
}
