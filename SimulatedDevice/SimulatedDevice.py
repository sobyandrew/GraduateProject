from uuid import UUID, uuid4
import paho.mqtt.client as mqtt
import threading
import csv
import datetime
import uuid
import time
from time import sleep
from random import randrange

numSimulatedDevices = 300
numMessagesPerDevice = 100

# numSimulatedDevices = 600
# numMessagesPerDevice = 50

file = open('log_temp.csv')

csvreader = csv.reader(file)

header = next(csvreader)
rows = []
for row in csvreader:
    rows.append(row)
file.close()


def getMessage(clientNum):
    randRow = randrange(len(rows))
    retStr = "{\"id\":\"" + str(uuid.uuid4()) + "\", \"deviceId\":\"" + clientNum + "\", \"timestamp\":\"" \
             + str(datetime.datetime.now()) + "\", \"humidity\":" + "\"" + rows[randRow][1] + "\", \"temperature\":" + "\"" + rows[randRow][0] + "\"" + "}"
    #print(retStr)
    return retStr


def send(client, topic, clientNum):
    client.publish(topic, getMessage(clientNum), qos=0)


def createClientAndCallSend(clientNum):
    clientUUID = "client" + str(uuid4())
    cli = mqtt.Client(clientUUID, True)
    cli.connect("localhost", 443, 60)

    count = 0

    while count < numMessagesPerDevice:
        # count = count + 1
        send(cli, clientUUID, clientNum)
        sleep(0.01)
        # if count >= numMessagesPerDevice:
        #     return
        count = count + 1



threads = []
start_time = datetime.datetime.now()

for n in range(numSimulatedDevices):
    t = threading.Thread(target=createClientAndCallSend, args=(str(n),))
    t.start()
    threads.append(t)

for t in threads:
    t.join()
end_time = datetime.datetime.now()

time_diff = (end_time - start_time)
execution_time = time_diff.total_seconds() * 1000
print(execution_time)
print("total")

