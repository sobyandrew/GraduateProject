from uuid import UUID, uuid4
import paho.mqtt.client as mqtt

# This is the Publisher
def send(client):
    print("in send")
    # client = mqtt.Client(str(uuid4()), True)
    # print("in send2")
    # client.connect("localhost",443,60)
    # print("in send3")
    client.publish("test", "test world" + str(uuid4()), qos=0);
    
print("before send")

print("after send")
client2 = mqtt.Client(str(uuid4()), True)
client2.connect("localhost",443,60)
send(client2)
count = 0
x = True
while x:
    # client = mqtt.Client(str(uuid4()), True)
    # client.connect("localhost",443,60)
    # client.publish("test", "test world 234234 234234234 234 234 23 4334", qos=0);
    send(client2)
    count = count + 1
    if count > 1000:
        x = False