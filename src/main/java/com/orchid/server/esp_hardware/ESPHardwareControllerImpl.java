package com.orchid.server.esp_hardware;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPHardware;
import com.orchid.server.esp_device.ESPDeviceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ESPHardwareControllerImpl implements ESPHardwareController {

    @Autowired
    ESPHardwareJPAService espHardwareJPAService;

    @Autowired
    ESPDeviceController espDeviceController;

    @Override
    public List<ESPHardware> fetchESPHardwaresByDeviceAndTitle(ESPDevice espDevice, String title) {
        return espHardwareJPAService.fetchESPHardwaresByDeviceAndTitle(espDevice,title);
    }

    @Override
    public List<ESPHardware> fetchESPHardwaresByDeviceAndDlgFlowRef(ESPDevice espDevice, String ref) {
        return espHardwareJPAService.fetchESPHardwaresByDeviceAndDlgFlowRef(espDevice,ref);
    }

    @Override
    public List<ESPHardware> fetchESPHardwaresByType(String type) {
        return espHardwareJPAService.findESPHardwaresByType(type);
    }

    @Override
    public ESPHardware fetchESPHardwareById(Integer id) {
        return espHardwareJPAService.fetchESPHardwareById(id);
    }

    @Override
    public ESPHardware findESPHardwareByDeviceAndHardwareId(ESPDevice device, String hardwareId) {
        return espHardwareJPAService.findESPHardwareByDeviceAndHardwareId(device,hardwareId);
    }

    @Override
    public Map<String,List<ESPHardware>> fetchAllESPHardwaresByDeviceId(Integer espDeviceId) {
        ESPDevice device = this.espDeviceController.fetchESPDeviceById(espDeviceId);
        if (device == null) {
            return null;
        }
        Map<String,List<ESPHardware>> hardwaresByCategory = new HashMap<>();
        hardwaresByCategory.put("relays", espHardwareJPAService.findESPHardwaresByDeviceAndType(device,"Relay"));
        hardwaresByCategory.put("sensors", espHardwareJPAService.findESPHardwaresByDeviceAndType(device,"Sensor"));
        return hardwaresByCategory;
    }

    @Override
    public ESPHardware addESPHardware(ESPHardware hardware) throws Exception {
        return espHardwareJPAService.addHardware(hardware);
    }

    @Override
    public ESPHardware updateESPHardware(ESPHardware hardware) throws Exception {
        return espHardwareJPAService.updateHardware(hardware);
    }

    @Override
    public void deleteESPHardware(ESPHardware hardware) {
        espHardwareJPAService.deleteHardware(hardware);
    }
}
