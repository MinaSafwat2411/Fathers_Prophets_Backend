@echo off
REM ==========================================
REM Postman Collection Generator Script
REM ==========================================

echo.
echo 🚀 Fathers Prophets - Postman Generator
echo ==========================================
echo.

REM Check if we're in the correct directory
if not exist "build.gradle.kts" (
    echo ❌ Error: build.gradle.kts not found!
    echo Please run this script from the project root directory.
    pause
    exit /b 1
)

echo 📦 Compiling project...
call .\gradlew.bat compileKotlin
if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo.
echo 🔧 Generating Postman Collection and Environment...
call .\gradlew.bat generatePostmanSmart
if %errorlevel% neq 0 (
    echo ❌ Generation failed!
    pause
    exit /b 1
)

echo.
echo ✅ Generation completed successfully!
echo.
echo 📁 Generated files:
echo    - Fathers_Prophets_API_ModelBased.postman_collection.json
echo    - Fathers_Prophets_API_ModelBased.postman_environment.json
echo.
echo 📋 Next steps:
echo    1. Open Postman
echo    2. Click Import
echo    3. Select both JSON files
echo    4. Start testing!
echo.
pause

