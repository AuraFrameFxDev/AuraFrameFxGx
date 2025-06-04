# AuraFrameFX Environment Setup Script
# This script helps set up the development environment securely

param (
    [switch]$ci = $false
)

# Create necessary directories if they don't exist
$configDir = "$PSScriptRoot/../app"
$templateDir = "$PSScriptRoot/../templates"

# Create templates directory if it doesn't exist
if (-not (Test-Path $templateDir)) {
    New-Item -ItemType Directory -Path $templateDir | Out-Null
}

# Function to check if a file exists and is not empty
function Test-FileNotEmpty {
    param([string]$path)
    return (Test-Path $path) -and ((Get-Item $path).Length -gt 0)
}

# Check for required tools
$requiredTools = @("git", "java")
foreach ($tool in $requiredTools) {
    try {
        $null = Get-Command $tool -ErrorAction Stop
    } catch {
        Write-Warning "$tool is not installed or not in PATH. Please install it and try again."
        exit 1
    }
}

# Set up environment variables
$envFile = "$PSScriptRoot/../.env"
$envTemplate = "$templateDir/env.template"

# Create .env template if it doesn't exist
if (-not (Test-Path $envTemplate)) {
    @"
# AuraFrameFX Environment Variables
# Copy this file to .env in the project root and update the values

# Google Cloud / Vertex AI
VERTEX_CREDENTIALS=path/to/your/vertex_credentials.json

# Firebase
FIREBASE_CREDENTIALS=path/to/your/firebase_credentials.json

# API Keys (if not using secure_config.xml)
# GOOGLE_API_KEY=your_google_api_key
# OTHER_API_KEY=your_other_api_key
"@ | Out-File -FilePath $envTemplate -Encoding utf8
}

# Create .env file if it doesn't exist
if (-not (Test-FileNotEmpty $envFile)) {
    Copy-Item $envTemplate -Destination $envFile -Force
    Write-Host "Created $envFile. Please update it with your credentials." -ForegroundColor Yellow
}

# Check for required credential files
$requiredFiles = @(
    @{ Name = "Vertex AI Service Account"; Template = "vertex_service_account.template.json"; Target = "vertex_service_account.json" },
    @{ Name = "Firebase Admin SDK"; Template = "firebase-config.template.json"; Target = "auraframefx-firebase-adminsdk-*.json" },
    @{ Name = "Google Services"; Template = ""; Target = "google-services.json" }
)

foreach ($file in $requiredFiles) {
    $templatePath = Join-Path $configDir $file.Template
    $targetPattern = Join-Path $configDir $file.Target
    
    # Skip if target file exists and is not empty
    $existingFile = Get-Item -Path $targetPattern -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($existingFile -and (Test-FileNotEmpty $existingFile.FullName)) {
        Write-Host "Found existing $($file.Name) configuration: $($existingFile.Name)" -ForegroundColor Green
        continue
    }
    
    # If template exists but target doesn't, create from template
    if ($file.Template -and (Test-Path $templatePath)) {
        $targetPath = Join-Path $configDir $file.Target.Replace("*", "default")
        Copy-Item $templatePath -Destination $targetPath -Force
        Write-Host "Created $($file.Name) configuration from template: $($file.Target)" -ForegroundColor Yellow
        Write-Host "Please update $targetPath with your credentials" -ForegroundColor Yellow
    } else {
        Write-Host "Warning: $($file.Name) configuration not found at $targetPattern" -ForegroundColor Red
        if (-not $ci) {
            $createNew = Read-Host "Would you like to create a new $($file.Name) configuration? (y/n)"
            if ($createNew -eq 'y') {
                $targetPath = Read-Host "Enter path to save $($file.Name) configuration"
                if (-not $targetPath) {
                    $targetPath = Join-Path $configDir $file.Target.Replace("*", "default")
                }
                New-Item -ItemType File -Path $targetPath -Force | Out-Null
                Write-Host "Created empty configuration at $targetPath. Please update it with your credentials." -ForegroundColor Yellow
            }
        }
    }
}
