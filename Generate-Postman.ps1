#!/usr/bin/env pwsh
# ==========================================
# Postman Collection Generator Script (PowerShell)
# ==========================================

$projectRoot = Get-Location
$buildFile = "$projectRoot\build.gradle.kts"

Write-Host ""
Write-Host "🚀 Fathers Prophets - Postman Generator" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""

# Check if we're in the correct directory
if (-not (Test-Path $buildFile)) {
    Write-Host "❌ Error: build.gradle.kts not found!" -ForegroundColor Red
    Write-Host "Please run this script from the project root directory." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "📦 Compiling project..." -ForegroundColor Yellow
& .\gradlew.bat compileKotlin
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Compilation failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "🔧 Generating Postman Collection and Environment..." -ForegroundColor Yellow
& .\gradlew.bat generatePostmanSmart
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Generation failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "✅ Generation completed successfully!" -ForegroundColor Green
Write-Host ""
Write-Host "📁 Generated files:" -ForegroundColor Cyan
Write-Host "   - Fathers_Prophets_API_ModelBased.postman_collection.json"
Write-Host "   - Fathers_Prophets_API_ModelBased.postman_environment.json"
Write-Host ""
Write-Host "📋 Next steps:" -ForegroundColor Cyan
Write-Host "   1. Open Postman"
Write-Host "   2. Click Import"
Write-Host "   3. Select both JSON files"
Write-Host "   4. Start testing!"
Write-Host ""

$files = Get-Item -Path "Fathers_Prophets_API_ModelBased.postman_*.json" -ErrorAction SilentlyContinue
if ($files) {
    Write-Host "📊 File sizes:" -ForegroundColor Cyan
    foreach ($file in $files) {
        $size = $file.Length / 1KB
        Write-Host "   $($file.Name): $([Math]::Round($size, 2)) KB"
    }
}

Write-Host ""
Read-Host "Press Enter to exit"

