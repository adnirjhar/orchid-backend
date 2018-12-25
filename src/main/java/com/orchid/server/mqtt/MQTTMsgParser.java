package com.orchid.server.mqtt;

import com.orchid.server.config.application_config.ApplicationConfigService;
import com.orchid.server.domain.ApplicationConfig;
import com.orchid.server.esp_device.ESPDeviceController;
import com.orchid.server.esp_device_log.ESPDeviceLogController;
import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPDeviceLog;
import com.orchid.server.domain.ESPHardware;
import com.orchid.server.esp_hardware.ESPHardwareController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class MQTTMsgParser {

    @Autowired
    ESPDeviceController espDeviceController;

    @Autowired
    ESPDeviceLogController espDeviceLogController;

    @Autowired
    ESPHardwareController espHardwareController;

    public ESPDeviceLog saveDeviceLogFromMsg(String message) {
        String[] msgParts = message.split("\\|");
        if (msgParts.length < 5) {
            return null;
        }
        String alertType = msgParts[0];
        String deviceId = msgParts[1];
        String instanceId = msgParts[2];
        String hardwareId = msgParts[3];
        String hardwareValue = msgParts[4];

        ESPDevice foundESPDevice = espDeviceController.fetchESPDeviceByDeviceIdAndInstanceId(deviceId,instanceId);
        if (foundESPDevice == null) {
            return null;
        }
        ESPHardware foundHrd = espHardwareController.findESPHardwareByDeviceAndHardwareId(foundESPDevice,hardwareId);

        if (foundHrd == null) {
            return null;
        }
        ESPDeviceLog newLog = new ESPDeviceLog(hardwareValue,alertType,new Date(),foundESPDevice,foundHrd);
        return espDeviceLogController.saveNewDeviceLog(newLog);
    }
}
