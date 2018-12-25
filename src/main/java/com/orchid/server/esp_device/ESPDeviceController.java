package com.orchid.server.esp_device;


import com.orchid.server.domain.ESPDevice;

import java.util.List;

public interface ESPDeviceController {

    public String getESPConfig(String deviceSecret) throws Exception;

    public List<ESPDevice> fetchAllESPDevices();

    public ESPDevice fetchESPDeviceById(Integer deviceId);

    public ESPDevice fetchESPDeviceByDeviceIdAndInstanceId(String deviceId,String instanceId);

    public List<ESPDevice> fetchESPDevicesByInstanceId(Integer instanceId);

    public List<ESPDevice> fetchDevicesByDlgFlowRefAndInstanceId(String ref, Integer instanceId);

    public ESPDevice storeESPDevice(ESPDevice espDevice) throws Exception;

    public ESPDevice updateESPDevice(ESPDevice espDevice) throws Exception;

    public void deleteESPDevice(ESPDevice espDevice) throws Exception;
}
