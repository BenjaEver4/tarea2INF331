@echo off
setlocal

for %%I in ("%~dp0.") do set "SCRIPT_DIR=%%~fI"
set "WRAPPER_DIR=%SCRIPT_DIR%\.mvn\wrapper"
set "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
set "WRAPPER_PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties"
set "DOWNLOAD_URL=https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar"

if exist "%WRAPPER_PROPERTIES%" (
  for /f "usebackq tokens=1,2 delims==" %%A in ("%WRAPPER_PROPERTIES%") do (
    if "%%A"=="wrapperUrl" set "DOWNLOAD_URL=%%B"
  )
)

if not exist "%WRAPPER_JAR%" (
  if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"
  powershell -NoProfile -Command "& { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%WRAPPER_JAR%' }"
  if errorlevel 1 (
    echo No se pudo descargar Maven Wrapper.
    exit /b 1
  )
)

if "%JAVA_HOME%"=="" (
  set "JAVA_CMD=java"
) else (
  set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
)

"%JAVA_CMD%" -Dmaven.multiModuleProjectDirectory="%SCRIPT_DIR%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*

endlocal