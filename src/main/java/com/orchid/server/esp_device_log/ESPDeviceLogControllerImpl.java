package com.orchid.server.esp_device_log;

import com.google.gson.Gson;
import com.orchid.server.config.spring_config.WebSocketComponent;
import com.orchid.server.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ESPDeviceLogControllerImpl implements ESPDeviceLogController {

    @Autowired
    WebSocketComponent webSocketComponent;

    @Autowired
    ESPDeviceLogJPAService espDeviceLogJPAService;

    private Gson gson = new Gson();

    @Override
    public List<ESPDeviceLog> fetchAllDeviceLogs() {
        return espDeviceLogJPAService.fetchAllDeviceLogs();
    }

    @Override
    public List<ESPDeviceLog> fetchLogsByDeviceAndHardware(ESPDevice device,ESPHardware hardware) {
        return espDeviceLogJPAService.fetchLogsByDeviceAndHardware(device,hardware);
    }

    @Override
    public ESPDeviceLog saveNewDeviceLog(ESPDeviceLog newESPDeviceLog) {
        ESPHardware hardware = newESPDeviceLog.getEspHardware();
        ESPDevice device = newESPDeviceLog.getEspDevice();
        Instance instance = device.getInstance();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("instance",instance.getTitle());
        dataMap.put("espDevice",device.getTitle());
        dataMap.put("espHardware",hardware.getTitle());
        Map<String, Object> deviceLogDetails = new HashMap<>();
        deviceLogDetails.put("data",newESPDeviceLog.getData());
        deviceLogDetails.put("dataType",newESPDeviceLog.getDataType());
        deviceLogDetails.put("loggedAt",newESPDeviceLog.getLoggedAt());
        dataMap.put("espDeviceLog",deviceLogDetails);


        SocketMsg msg = new SocketMsg();
        msg.setCommand("updateLog");
        msg.setMessage(gson.toJson(dataMap));

        webSocketComponent.broadcastMessageES(msg);
        return espDeviceLogJPAService.saveNewDeviceLog(newESPDeviceLog);
    }
}
