package com.orchid.server.mqtt;

public interface MQTTComponentBase {

    public void publishMessage(String topic, String message);

    public void subscribeToTopic(String topic);

    public void connect();

    public void disconnect();
}
