# GraduateProject
This is my CSUF CSPC 597 Graduate Project

links to documentation

1) Simulated device
https://docs.google.com/document/d/1cg4stQU_e341AgsICG02nDiMsD4LhiVfm-Qx0Tu9-30/edit?usp=sharing


2) Infrastructure
https://docs.google.com/document/d/1VTqNPsH7Ma86lgGFc6xJj9YvYuaK8qQYzlXFn9O-gQg/edit?usp=sharing


3) Microservices
https://docs.google.com/document/d/1Wo6nPnOZ3LMAHDzCBWeChR78q3vPqxBBK0vI0CjDV9Q/edit?usp=sharing


4) Project Documentation  https://docs.google.com/document/d/1A0vNkMWpMCSN09xpUhw4AxSwALBgwJ6-Gzb_YTpJtrs/edit?usp=sharing


# Commands to Run
1. run zookeeper: .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
2. run Kafka: .\bin\windows\kafka-server-start.bat .\config\server.properties
3. run MQTT Broker: mosquitto -p 443
4. run java jar builds: <br>
   java -jar access-1.0-Final.jar <br>
   java -jar alarm-1.0-Final.jar <br>
   java -jar export-1.0-Final.jar <br>
   java -jar ingest-1.0-Final.jar <br>
   java -jar normalize-1.0-Final.jar <br>
   java -jar query-1.0-Final.jar <br>
   java -jar rawdata-1.0-Final.jar <br>
   java -jar relay-1.0-Final.jar <br>
5. run Front end:
   1. npm i
   2. npm start <br>
   or in build folder
   3. npx serve
6. run python: python SimulatedDevice.py

# Query to Run <br>
select * from deviceInfo where deviceid like '2' and humidity like '21';

