#this file contains the simulated device code
import paho.mqtt.client as paho

brokerAddress = "127.0.0.1"
brokerPort = 1883

def on_publish(client,userdata,result):             #create function for callback
    print("data published \n")
    pass

def createMessage():
    return "testMessage"

def send():
    mqttClient = paho.Client("clientId123")
    mqttClient.on_publish = on_publish
    mqttClient.connect(brokerAddress, brokerPort, 60)
    returnStatus = mqttClient.publish("topic", "test" , 0, False)
    

print("sending now")
send()
print("sent")
