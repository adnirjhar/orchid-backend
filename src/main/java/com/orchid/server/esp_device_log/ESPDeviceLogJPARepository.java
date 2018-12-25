package com.orchid.server.esp_device_log;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPDeviceLog;
import com.orchid.server.domain.ESPHardware;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ESPDeviceLogJPARepository extends CrudRepository<ESPDeviceLog, Integer> {

    public List<ESPDeviceLog> findAllByEspDeviceAndEspHardware(ESPDevice device, ESPHardware hardware);
}
