package com.orchid.server.esp_device_log;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPDeviceLog;
import com.orchid.server.domain.ESPHardware;

import java.util.List;

public interface ESPDeviceLogController {

    public List<ESPDeviceLog> fetchAllDeviceLogs();

    public List<ESPDeviceLog> fetchLogsByDeviceAndHardware(ESPDevice device,ESPHardware hardware);

    public ESPDeviceLog saveNewDeviceLog(ESPDeviceLog newESPDeviceLog);
}
