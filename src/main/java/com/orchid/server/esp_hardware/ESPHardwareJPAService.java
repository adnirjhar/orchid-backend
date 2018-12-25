package com.orchid.server.esp_hardware;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPDeviceLog;
import com.orchid.server.esp_device.ESPDeviceController;
import com.orchid.server.domain.ESPHardware;
import com.orchid.server.esp_device_log.ESPDeviceLogController;
import com.orchid.server.mqtt.MQTTComponentBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ESPHardwareJPAService {

    @Autowired
    ESPHardwareJPARepository espHardwareJPARepository;

    @Autowired
    ESPDeviceLogController espDeviceLogController;

    @Autowired
    ESPDeviceController espDeviceController;

    @Autowired
    MQTTComponentBase mqttComponent;

    public ESPHardware fetchESPHardwareById(Integer id) {
        return espHardwareJPARepository.findESPHardwareById(id);
    }

    public ESPHardware findESPHardwareByDeviceAndHardwareId(ESPDevice espDevice, String hardwareId) {
        return espHardwareJPARepository.findESPHardwareByHardwareIdAndEspDevice(hardwareId,espDevice);
    }

    public List<ESPHardware> fetchESPHardwaresByDeviceAndDlgFlowRef(ESPDevice espDevice, String ref) {
        return espHardwareJPARepository.findESPHardwaresByDialogFlowRefLikeAndEspDevice(ref, espDevice);
    }

    public List<ESPHardware> fetchESPHardwaresByDeviceAndTitle(ESPDevice espDevice, String title) {
        return espHardwareJPARepository.findESPHardwaresByTitleLikeAndEspDevice(title, espDevice);
    }

    public List<ESPHardware> findESPHardwaresByDeviceAndHardwareId(ESPDevice espDevice, String hardwareId) {
        return espHardwareJPARepository.findESPHardwaresByHardwareIdAndEspDevice(hardwareId,espDevice);
    }

    public List<ESPHardware> findESPHardwaresByDeviceAndType(ESPDevice espDevice, String type) {
        return espHardwareJPARepository.findESPHardwaresByTypeAndEspDevice(type,espDevice);
    }

    public List<ESPHardware> findESPHardwaresByType(String type) {
        return espHardwareJPARepository.findESPHardwaresByType(type);
    }

    public ESPHardware addHardware(ESPHardware newHardware) throws Exception {

        Integer id = newHardware.getId();
        if (id != null) {
            throw new Exception("There is a different api for updating now");
        }

        Integer hrdDevId = newHardware.getEspDevice().getId();
        ESPDevice foundDev = espDeviceController.fetchESPDeviceById(hrdDevId);
        if (foundDev == null) {
            throw new Exception("Associated device not found");
        }

        List<ESPHardware> hardwares = this.findESPHardwaresByDeviceAndHardwareId(foundDev,newHardware.getHardwareId());
        if (hardwares.size() > 0) {
            throw new Exception("Hardware already exists for device.");
        }

        newHardware.setHardwareId(newHardware.getHardwareId().toUpperCase());
        newHardware.setEspDevice(foundDev);
        newHardware.setEnabled(true);
        newHardware.setLastValue(0);
        return espHardwareJPARepository.save(newHardware);
    }

    public void deleteHardware(ESPHardware hardware) {
        espHardwareJPARepository.delete(hardware);
    }

    public ESPHardware updateHardware(ESPHardware hardwareToUpdate) throws Exception {

        Integer hardwareId = hardwareToUpdate.getId();

        ESPHardware foundHardware = this.fetchESPHardwareById(hardwareId);
        if (hardwareId == null || foundHardware == null) {
            throw new Exception("Invalid device provided to update");
        }

        ESPDevice foundHardwareESPDevice = foundHardware.getEspDevice();
        if (hardwareToUpdate.getLastValue() != foundHardware.getLastValue()) {
            String mqttPayload = hardwareToUpdate.getHardwareId() + "=" + hardwareToUpdate.getLastValue();
            this.mqttComponent.publishMessage(foundHardwareESPDevice.getDeviceChannel(),mqttPayload);
            System.out.println("***********************************************************************");
            System.out.println("Sending message to : " + foundHardwareESPDevice.getDeviceChannel() + ":" + mqttPayload);
            System.out.println("***********************************************************************");

            //=========================================================
            //  GENERATE NEW LOG FOR HARDWARE
            //=========================================================
            ESPDeviceLog newLog = new ESPDeviceLog(hardwareToUpdate.getLastValue().toString(),"LOG",new Date(),foundHardwareESPDevice,hardwareToUpdate);
            espDeviceLogController.saveNewDeviceLog(newLog);
        }
        return espHardwareJPARepository.save(hardwareToUpdate);
    }
}
