from uuid import UUID, uuid4
import paho.mqtt.client as mqtt
import threading
from time import sleep

def getMessage():
    return "{ \"uuid\": \"" + str(uuid4()) + "\"}"
# This is the Publisher
def send(client, topic):
    print("in send")
    # client = mqtt.Client(str(uuid4()), True)
    # print("in send2")
    # client.connect("localhost",443,60)
    # print("in send3")
    client.publish(topic, getMessage(), qos=0);
    
def createClientAndCallSend():
    clientUUID = "client" + str(uuid4())
    cli =  mqtt.Client(clientUUID, True)
    cli.connect("localhost", 443, 60)
    x = True
    count = 0
    while x:
        count = count + 1
        send(cli, clientUUID)
        sleep(0.0001)
        if count >= 1:
            return 
    
threads = []

for n in range(1):
    t = threading.Thread(target=createClientAndCallSend, args=())
    t.start()
    threads.append(t)

for t in threads:
    t.join()
# print("before send")

# print("after send")
# client2 = mqtt.Client(str(uuid4()), True)
# client2.connect("localhost",443,60)
# send(client2)
# count = 0
# x = True
# while x:
#     # client = mqtt.Client(str(uuid4()), True)
#     # client.connect("localhost",443,60)
#     # client.publish("test", "test world 234234 234234234 234 234 23 4334", qos=0);
#     print("creating client")
#     createClientAndCallSend()
#     count = count + 1
#     if count > 5:
#         x = False