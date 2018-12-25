package com.orchid.server.mqtt;

import com.orchid.server.config.application_config.ApplicationConfigService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class MQTTComponent implements MQTTComponentBase,MqttCallback {

    @Autowired
    private ApplicationConfigService applicationConfigService;

    @Autowired
    private MQTTMsgParser mqttMsgParser;

    private MqttClient mqttClient = null;
    private String mqttHost = null;
    private Integer mqttPort = null;
    private String mqttUsername = null;
    private String mqttPassword = null;

    private Integer qos = 2;
    private Boolean hasSSL = false;
    private String clientIdBase = "orchid";

    private static final Logger logger = LoggerFactory.getLogger(MQTTComponent.class);

    public MQTTComponent() {}

    @PostConstruct
    public void loadApplication() throws Exception {
        this.connect();
    }

    @Override
    public void connect() {
        this.mqttHost = applicationConfigService.getMQTTHost().getConfigValue();
        this.mqttPort = Integer.parseInt(applicationConfigService.getMQTTPort().getConfigValue());
        this.mqttUsername = applicationConfigService.getMQTTUsername().getConfigValue();
        this.mqttPassword = applicationConfigService.getMQTTPassword().getConfigValue();

        String protocol = this.hasSSL ? "ssl://" : "tcp://";
        String brokerUrl = protocol + this.mqttHost + ":" + this.mqttPort;
        String clientId = this.generateClientId();

        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        connectionOptions.setCleanSession(true);
        connectionOptions.setUserName(this.mqttUsername);
        connectionOptions.setPassword(this.mqttPassword.toCharArray());

        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.mqttClient.setCallback(this);
            this.mqttClient.connect(connectionOptions);
            this.subscribeToDefault();

            System.out.println("***********************************************************************");
            System.out.println("Connect to : " + brokerUrl + " - " + clientId);
            System.out.println("***********************************************************************");

        } catch (MqttException me) {
            logger.error("MqttException - onConfig:" + me.getLocalizedMessage());
        }
    }

    private String generateClientId() {
        String newClientId;
        try {
            newClientId = InetAddress.getLocalHost().getHostName() + ":" + this.clientIdBase;
        }
        catch (UnknownHostException ex) {
            newClientId = "orchid.localhost" + ":" + this.clientIdBase;
        }
        return newClientId;
    }


    @Override
    public void publishMessage(String topic, String message) {
        topic = topic == null ? applicationConfigService.getCommonMQTTChannel().getConfigValue() : topic;

        try {
            MqttMessage mqttmessage = new MqttMessage(message.getBytes());
            mqttmessage.setQos(this.qos);
            this.mqttClient.publish(topic, mqttmessage);
        } catch (MqttException me) {
            logger.error("MqttException - onPublishMsg", me);
        }

    }

    private void subscribeToDefault() {
        String commonChannel = applicationConfigService.getCommonMQTTChannel().getConfigValue();
        this.subscribeToTopic(commonChannel);
        System.out.println("***********************************************************************");
        System.out.println("Subscribed to : " + commonChannel);
        System.out.println("***********************************************************************");
    }

    @Override
    public void subscribeToTopic(String topic) {
        try {
            this.mqttClient.subscribe(topic, this.qos);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            System.out.println("***********************************************************************");
            System.out.println("Disconnecting on purpose");
            System.out.println("***********************************************************************");
            this.mqttClient.disconnect();
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("***********************************************************************");
        System.out.println("Connection Lost to : " + throwable.getCause());
        System.out.println("***********************************************************************");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        ///********************************************************
        ///  Process received message
        ///********************************************************
        String messageReceived = new String(mqttMessage.getPayload());
        this.mqttMsgParser.saveDeviceLogFromMsg(messageReceived);

        System.out.println("***********************************************************************");
        System.out.println("Message Arrived at Topic: " + topic + "  Message: " + new String(mqttMessage.getPayload()));
        System.out.println("***********************************************************************");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("***********************************************************************");
        System.out.println("Delivery complete to : " + iMqttDeliveryToken.toString());
        System.out.println("***********************************************************************");
    }
}
