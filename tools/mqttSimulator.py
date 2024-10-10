from datetime import datetime
import json
import random
import time
from paho.mqtt import client as mqtt_client

broker = '124.223.191.78'
port = 1883
client_id = f'python-mqtt-{random.randint(0, 1000)}'

topic_electric_voltage = 'electric/voltage'
topic_electric_current = 'electric/current'
topic_electric_power = 'electric/power'
topic_electric_consumption = 'electric/consumption'

msg_count = 0


def connect_mqtt():
    def on_connect(client, userdata, flags, rc):
        if rc == 0:
            print("connect sucessfully!")
        else:
            print("connect failed")
    client = mqtt_client.Client(client_id)
    client.on_connect = on_connect
    client.connect(broker, port)
    return client


def send(client, topic, msg):
    result = client.publish(topic, msg)
# result: [0, 1]
    status = result[0]
    if status == 0:
        print(f"Send `{msg}` to topic `{topic}`")
    else:
        print(f"Failed to send message to topic {topic}")
    # msg_count += 1


def publish(client):
    consupution = 1000.00
    while True:
        time.sleep(5)
        consupution += 1.0
# 发送模拟电压
        electric_voltage_msg = {
            "clientId": client_id,
            "value": 220,
            "createTime": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        }
        send(client, topic_electric_voltage, json.dumps(electric_voltage_msg))
        # result = client.publish(topic_electric_voltage,
        #                         json.dumps(electric_voltage_msg))

# 发送模拟电流
        electric_current_msg = {
            "clientId": client_id,
            "value": 1.5,
            "createTime": datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        }
        # result = client.publish(topic_electric_current,
        #                         json.dumps(electric_current_msg))
        send(client, topic_electric_current, json.dumps(electric_current_msg))

# 发送模拟电功率
        electric_power_msg = {
            "clientId": client_id,
            "value": 2200,
            "createTime": datetime.now().strftime("%Y-%m-%d %H:%M:%S")}
        # result = client.publish(topic_electric_power,
        #                         json.dumps(electric_power_msg))
        send(client, topic_electric_power, json.dumps(electric_power_msg))


# 发送模拟用电量
        electric_consumption_msg = {
            "clientId": client_id,
            "value": consupution,
            "createTime": datetime.now().strftime("%Y-%m-%d %H:%M:%S")}
        # result = client.publish(
        #     topic_electric_consumption, json.dumps(electric_consumption_msg))
        send(client, topic_electric_consumption,
             json.dumps(electric_consumption_msg))


def test():
    t = datetime.now()
    print(t)


def run():
    test()
    client = connect_mqtt()
    client.loop_start()
    publish(client)


if __name__ == '__main__':
    run()
