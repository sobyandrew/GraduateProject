@echo off
cd C:\kafka
start "zookeeperInstance" .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
timeout /t 10 /nobreak > NUL
start "kafkaInstance" .\bin\windows\kafka-server-start.bat .\config\server.properties
