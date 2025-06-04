# AuraFrameFX Development Scripts

This directory contains utility scripts to help with development and setup of the AuraFrameFX project.

## Available Scripts

### `setup_env.ps1`

A PowerShell script to help set up the development environment securely.

**Features:**
- Creates template configuration files if they don't exist
- Sets up environment variables
- Helps manage sensitive credentials
- Configures Git hooks for security checks

**Usage:**
```powershell
# Run the setup script (interactive mode)
.\scripts\setup_env.ps1

# Run in CI mode (non-interactive)
.\scripts\setup_env.ps1 -ci
```

**Prerequisites:**
- PowerShell 5.1 or later
- Git
- Java Development Kit (JDK) 11 or later

## Security Best Practices

1. **Never commit sensitive files** to version control
2. Use `.env` files for local development
3. Store production credentials in a secure secret manager
4. Review Git hooks before committing
5. Use environment variables or build variants for different environments

## Adding New Scripts

1. Place new scripts in this directory
2. Update this README with documentation
3. Ensure scripts follow security best practices
4. Test scripts in a clean environment

## License

These scripts are part of the AuraFrameFX project and are covered by the same license.
