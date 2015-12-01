@echo off
echo [INFO] Install jar to local repository.

cd %~dp0
cd ..
call mvn clean package -Dmaven.test.skip=true
cd bin
pause