package com.orchid.server.esp_device_log;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPDeviceLog;
import com.orchid.server.domain.ESPHardware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ESPDeviceLogJPAService {

    @Autowired
    ESPDeviceLogJPARepository repository;

    public List<ESPDeviceLog> fetchAllDeviceLogs() {
        List<ESPDeviceLog> espDeviceLogs = new ArrayList<>();
        repository.findAll().forEach(espDeviceLogs::add);
        return espDeviceLogs;
    }

    public List<ESPDeviceLog> fetchLogsByDeviceAndHardware(ESPDevice device,ESPHardware hardware) {
        return repository.findAllByEspDeviceAndEspHardware(device,hardware);
    }

    public ESPDeviceLog saveNewDeviceLog(ESPDeviceLog espDeviceLog) {
        return repository.save(espDeviceLog);
    }
}
