package com.orchid.server.dialogflow;

import com.orchid.server.esp_device.ESPDeviceController;
import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPHardware;
import com.orchid.server.domain.Instance;
import com.orchid.server.esp_hardware.ESPHardwareController;
import com.orchid.server.instance.InstanceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Service
public class DialogFlowService implements DialogFlowController {

    @Autowired
    InstanceController instanceController;

    @Autowired
    ESPDeviceController espDeviceController;

    @Autowired
    ESPHardwareController espHardwareController;

    @Autowired
    EntityManager entityManager;

    @Override
    public String processMessage(Map<String, Object> parameters) {
        String actions = (String) parameters.getOrDefault("actions", null);
        String actions_value = (String) parameters.getOrDefault("actions_value", null);
        String devices = (String) parameters.getOrDefault("devices", null);
        String devices_value = (String) parameters.getOrDefault("devices_value", null);
        String rooms = (String) parameters.getOrDefault("rooms", null);
        String rooms_value = (String) parameters.getOrDefault("rooms_value", null);
        String places = (String) parameters.getOrDefault("places", null);
        String places_value = (String) parameters.getOrDefault("places_value", null);

        if (rooms != null && devices != null && actions != null && places != null) {

            //=========================================================
            //  Check for instances / places
            //=========================================================
            List<Instance> instances = instanceController.fetchInstancesByDialogFlowRef(places_value);
            if (instances.size() == 0) {
                return "Sorry couldn't find " + places;
            }
            Instance selectedPlace = instances.get(0);
            //=========================================================
            //  Check for devices / rooms
            //=========================================================
            List<ESPDevice> roomsInInstances = espDeviceController.fetchDevicesByDlgFlowRefAndInstanceId(rooms_value,selectedPlace.getId());
            if (roomsInInstances.size() == 0) {
                return "Sorry couldn't find " + rooms + " in " + selectedPlace.getName();
            }
            ESPDevice selectedRoom = roomsInInstances.get(0);
            //=========================================================
            //  Check for hardwares / relays / sensors
            //=========================================================
            List<ESPHardware> hardwaresInRoom = espHardwareController.fetchESPHardwaresByDeviceAndDlgFlowRef(selectedRoom,devices_value);
            if (hardwaresInRoom.size() == 0) {
                return "Sorry couldn't find " + devices + " in " + selectedRoom.getTitle();
            }

            //=========================================================
            //  Change value of hardware/relay/sensor
            //=========================================================
            try {
                Integer hardwareAction = actions_value.equals("on") ? 1 : 0;
                ESPHardware selectedHardware = hardwaresInRoom.get(0);
                entityManager.detach(selectedHardware);
                selectedHardware.setLastValue(hardwareAction);

                espHardwareController.updateESPHardware(selectedHardware);

            }
            catch (Exception ex) {
                return ex.getMessage();
            }
        }
        return "Done!";
    }
}
