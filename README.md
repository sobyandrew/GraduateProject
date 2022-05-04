# Graduate Project
This is Andrew Soby's CSUF CSPC 597 Graduate Project.

This project focuses on creating an efficient IoT ingest pipeline to account for scalability and adaptability as more IoT devices are connected in the future.
There are four significant sections in this project the Infrastructure, Simulated Device, Microservices, and the Front End.
---
### 1. Infrastructure
The Infrastructure is combined of 5 main sections Apache Zookeeper, Apache Kafka, MQTT Broker, PostgreSQL Server, and MongoDB.
<br>
<br>
 1. **Apache Zookeeper** - this is used to manage the Apache Kafka instance and is included inside the Infrastructure\kafka folder as binaries
<br>
<br>
 2. **Apache Kafka** - this is used as a message queue for the microservices to communicate between and is included inside the Infrastructure\kafka folder as binaries.
<br>
<br>
- These binaries for Kafka / Zookeeper were downloaded from the latest scala version at the time of making this from: https://kafka.apache.org/downloads

<br>

3. **MQTT Broker** - This is used as a lightweight broker to communicate from simulate IoT devices and to the relay microservice this is included in the Infrastructure\MQTTBroker folder as binaries 
<br>
<br>
- The MQTT Broker binaries were downloaded following this guide: http://www.steves-internet-guide.com/install-mosquitto-broker/#manual

<br>

4. **PostgreSQL Server** - This is used as a SQL database to show ease of normalizing device data as well as querying it from the microservices
<br>
<br>
5. **MongoDB** - This is used as a document store for device messages to always store the unchanged original message. It is also used for exporting this information for further analysis

---
### 2. Simulated Device

The simulated device is built using multithreading in a Python Script. This emulates multiple devices and can send a large amount of messages. The messages it sends are from a modified Kaggle dataset from real IoT device data sending Humidity and Temperature information. The script is located in the SimulatedDevice directory.

- Kaggle Iot Dataset was retrieved from this link: https://www.kaggle.com/datasets/edotfs/dht11-temperature-and-humidity-sensor-1-day

---
### 3. Microservices
Each microservice is built using Java in the spring framework, There are 8 microservices in total for this project and they are all located in the microservices directory then further located in their respective directories.
1. **Relay** – Listens to MQTT and places messages onto Kafka
2. **Ingest** – Dispatches messages to proper topic for raw data and normalize service
3. **Normalize** – stores device info into PostgreSQL, sends out alarm messages to alarm service
4. **Raw Data** – stores unchanged device messages into MongoDB
5. **Alarm** – receives alarms and stores them into MongoDB, allows users to view alarms
6. **Export** – Receives device IDs to export json for a csv from users
7. **Query** – Receives SQL query and returns results
8. **Access**  – acts as unified dispatcher for REST requests amongst all services 

---
### 4. Front End
The front end was built using React and contains four separate pages.
1. Home page - describing the project and its different features
2. Query page - to allow users to enter a query and get results from the query microservice
3. Export page - to enter device Ids and get a csv of exported data from the export microservice
4. Alarm page - to view past 10 alarms from the alarm microservice


---
# Running this Project
*Note: All of these commands will assume that you are currently in the root of this project directory for clarity*
## Prerequisites for Running
1. Must be running on Windows 10 64-bit system
2. Must download graduate project repo or zip of the repo
3. Have Java / Gradle / MongoDB / PostgreSQL Server / NodeJS / Python installed
4. pip install paho-mqtt 
- run **pip install paho-mqtt** to install the python package required to connect to mqtt
5. Start MongoDB and PostgreSQL Server
- this project, MongoDB doesn't require a username or password. If your system requires a username or password to insert and create collections, some steps will need to change later.
- For this project, a PostgreSQL user is created with the ability to create, insert, and query from tables. The username for my project is: postgres, and the password is: password.
- This is chosen for simplicity, but if your system does not have this same user, then steps later will need to be updated to reflect this
5. Configure log directories for Apache ZooKeeper and Apache Kafka by setting zookeeper.properties file and server.properties file variables
- open the file located Infrastructure\kafka\config\zookeeper.properties
- **change line 19** to a location to store logs for Zookeeper on your system
- ex: dataDir=c:/users/Andrew Soby/Documents/GitHub/GraduateProject/Infrastructure/kafka/zookeeper-data
- open the file located Infrastructure\kafka\config\server.properties
- **change line 62** to a location to store logs for Kafka on your system
- ex: log.dirs=c:/users/Andrew Soby/Documents/GitHub/GraduateProject/Infrastructure/kafka/kafka-logs
  <br>
  <br>
6. Move the whole Infrastructure\kafka folder to the root of your SYSTEMS drive
- I placed this at the lowest level possible on my C drive at location C:\kafka after moving it
- THIS IS AN IMPORTANT STEP BECAUSE WHEN RUNNING ZOOKEEPER AND KAFKA IT WILL GIVE AN INPUT TOO LONG ERROR IF THERE ARE TOO MANY DIRECTORIES INFRONT OF THE LOCATION OF KAFKA
- this issue is noted here and can be fixed by moving it close to the root of your system as I mentioned: https://stackoverflow.com/questions/48834927/the-input-line-is-too-long-when-starting-kafka

## Commands to Run with Instructions
1. run zookeeper:
- open new terminal
- change directory to where you previously placed the kafka folder
- run the following command
- **.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties**
-  keep terminal up and running
2. run Kafka: 
- open new terminal
- change directory to where you previously placed the kafka folder (same as above)
- run the following command
- **.\bin\windows\kafka-server-start.bat .\config\server.properties**
- keep terminal up and running
3. Create Kafka Message topics if they do not exist already
- open new terminal
- change directory to where you previously placed the kafka folder (same as above)
- run the following 4 commands to create 4 message topics if they do not exist
- **.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic ingest**
- **.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic raw**
- **.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic normal**
- **.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic alarm**
- this terminal can be closed
4. run MQTT Broker:
- open new terminal in this projects root directory
- **cd Infrastructure\MQTTBroker\mos2**
- run the following command
- **mosquitto -p 443 -v**
- keep the terminal up and running
5. run java jar builds:
- open 8 separate terminals one for each microservice
- cd into build-jars for all terminals using the following command
- **cd build-jars**
- run all jars using the following commands
- **java -jar access-1.0-Final.jar**
- **java -jar alarm-1.0-Final.jar**
- **java -jar export-1.0-Final.jar**
- **java -jar ingest-1.0-Final.jar**
- **java -jar normalize-1.0-Final.jar**
- **java -jar query-1.0-Final.jar**
- **java -jar rawdata-1.0-Final.jar**
- **java -jar relay-1.0-Final.jar**
- keep all terminals up and running

6. run Front end:
- USING NPM START on the react-app: 
- open a new terminal 
- cd into the front end folder using the following command
- **cd frontend\grad-project**
- install node packages using the following command
- **npm i**
- start the development server using the following command
- **npm start**
- keep terminal open and running
- USING NPX SERVE on the react build
- open new terminal
- cd into the frontendBuild folder using the following command
- **cd frontendBuild**
- run the front end using the following command
- **npx serve**
- keep terminal up and running
### CHECKPOINT
At this point there should be 12 terminals up and running. 1 for Apache Zookeeper, 1 for Apache Kafka, 1 for the MQTT Broker, 8 for the microservices where each microservice has its own terminal, and 1 for the front end.
At this point the whole system is up and running as long as the PostgreSQL server is running on localhost at port 5432, MongoDB is running on localhost at port 27017 , Kafka is up and running on localhost on port 9092 from the previous steps(with Zookeeper managing it), MQTT is up and running on localhost with port 433, and the front end is running on localhost port 3000 or another specified port then the whole system is up and running.
7. run python simulated device: 
- open a new terminal
- change directories into the simulated device using the following command
- cd SimulatedDevice
- run the simulated device using the following command
- python SimulatedDevice.py

Finally messages should have propagated throughout the system and can be viewed on the front end following the examples
<br>
<br>
if there are any questions getting set up feel free to reach out to me at **sobyandrew@gmail.com**
---
# DEMO LINK
Here is a provided link to the demo of this project that can be seen here if there are difficulties running: 
- https://youtu.be/Ypzu24bc1yc
---
# Project Repository link
Here is the link to the project repository hosted on Github
- https://github.com/sobyandrew/GraduateProject
---
# Project Documentation Link
Here is the link to the final project documentation
- TODO


