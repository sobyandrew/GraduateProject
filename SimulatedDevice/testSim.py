import paho.mqtt.client as mqtt

# This is the Publisher

client = mqtt.Client("test", True)
client.connect("localhost",443,60)
client.publish("test", "Hello world!", qos=0);
# client.disconnect();