package com.orchid.server.dashboard;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPDeviceLog;
import com.orchid.server.domain.ESPHardware;
import com.orchid.server.domain.Instance;
import com.orchid.server.esp_device.ESPDeviceController;
import com.orchid.server.esp_device_log.ESPDeviceLogController;
import com.orchid.server.esp_hardware.ESPHardwareController;
import com.orchid.server.instance.InstanceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    InstanceController instanceController;

    @Autowired
    ESPDeviceController espDeviceController;

    @Autowired
    ESPHardwareController espHardwareController;

    @Autowired
    ESPDeviceLogController espDeviceLogController;

    public Object widgetSummary() {
        Map<String,Object> dataSum = new HashMap<>();
        dataSum.put("numberOfInstances", instanceController.fetchAllInstances().size());
        dataSum.put("numberOfRelays", espHardwareController.fetchESPHardwaresByType("Relay").size());
        dataSum.put("numberOfSensors", espHardwareController.fetchESPHardwaresByType("Sensor").size());
        dataSum.put("numberOfErrors", 0);
        return dataSum;
    }

    public Object deviceLogSummary() {
        List<Instance> instances = instanceController.fetchAllInstances();

        Map<String, Map<String,Map<String,List<ESPDeviceLog>>>> insVsDev = new HashMap<>();
        for (int in = 0; in < instances.size(); in++) {
            Instance instance = instances.get(in);
            List<ESPDevice> insDevices = new ArrayList<ESPDevice>(instance.getEspDevice());

            Map<String,Map<String,List<ESPDeviceLog>>> devicesVsHrd = new HashMap<>();
            for (int de = 0; de < insDevices.size(); de++) {
                ESPDevice espDevice = insDevices.get(de);
                List<ESPHardware> espHardwares = new ArrayList<>(espDevice.getEspHardwares());

                Map<String,List<ESPDeviceLog>> hardwareVsLog = new HashMap<>();
                for (int hrd = 0; hrd < espHardwares.size(); hrd++) {
                    ESPHardware hardware = espHardwares.get(hrd);
                    List<ESPDeviceLog> espDeviceLogs = espDeviceLogController.fetchLogsByDeviceAndHardware(espDevice,hardware);

                    hardwareVsLog.put(hardware.getTitle(),espDeviceLogs);
                }
                devicesVsHrd.put(espDevice.getTitle(),hardwareVsLog);
            }
            insVsDev.put(instance.getTitle(),devicesVsHrd);
        }
        return insVsDev;
    }
}
