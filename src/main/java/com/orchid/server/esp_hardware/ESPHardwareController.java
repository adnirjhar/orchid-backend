package com.orchid.server.esp_hardware;


import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPHardware;

import java.util.List;
import java.util.Map;

public interface ESPHardwareController {

    public List<ESPHardware> fetchESPHardwaresByDeviceAndTitle(ESPDevice espDevice, String hardwareTitle);

    public List<ESPHardware> fetchESPHardwaresByDeviceAndDlgFlowRef(ESPDevice espDevice, String hardwareTitle);

    public List<ESPHardware> fetchESPHardwaresByType(String type);

    public ESPHardware fetchESPHardwareById(Integer id);

    public ESPHardware findESPHardwareByDeviceAndHardwareId(ESPDevice device, String hardwareId);

    public Map<String,List<ESPHardware>> fetchAllESPHardwaresByDeviceId(Integer espDeviceId);

    public ESPHardware addESPHardware(ESPHardware hardware) throws Exception;

    public ESPHardware updateESPHardware(ESPHardware hardware) throws Exception;

    public void deleteESPHardware(ESPHardware hardware);
}
