@echo off
cd Infrastructure\MQTTBroker\mos2
start "mosquittoBroker" mosquitto -p 443
timeout /t 5 /nobreak > NUL
cd ..\..\..\build-jars
start "relay" java -jar relay-1.0-Final.jar
start "ingest" java -jar ingest-1.0-Final.jar
start "normal" java -jar normalize-1.0-Final.jar
start "raw" java -jar rawdata-1.0-Final.jar
start "alarm" java -jar alarm-1.0-Final.jar
start "query" java -jar query-1.0-Final.jar
start "export" java -jar export-1.0-Final.jar
start "access" java -jar access-1.0-Final.jar
cd ..\frontendBuild
start "frontendStart" npx serve
timeout /t 10 /nobreak > NUL
cd ..\SimulatedDevice
start "simulatedDevice" python SimulatedDevice.py
