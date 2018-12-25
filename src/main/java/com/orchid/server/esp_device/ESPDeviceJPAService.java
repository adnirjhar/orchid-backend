package com.orchid.server.esp_device;

import com.orchid.server.domain.ESPDevice;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ESPDeviceJPAService {

    @Autowired
    ESPDeviceJPARepository repository;

    public List<ESPDevice> fetchAllESPDevices() {
        List<ESPDevice> espDevice = new ArrayList<ESPDevice>();
        repository.findAll().forEach(espDevice::add);
        return espDevice;
    }

    public ESPDevice fetchESPDeviceById(Integer id) {
        return repository.findESPDeviceById(id);
    }

    public List<ESPDevice> fetchESPDevicesByInstanceId(Integer id) {
        return repository.findESPDevicesByInstance_Id(id);
    }

    public ESPDevice fetchESPDeviceByDeviceIdAndInstanceId(String deviceId,Integer id) {
        return repository.findESPDeviceByDeviceIdAndInstance_Id(deviceId,id);
    }

    public ESPDevice fetchESPDeviceByDeviceIdAndInstanceId(String deviceId,String insId) {
        return repository.findESPDeviceByDeviceIdAndInstance_Name(deviceId,insId);
    }

    public List<ESPDevice> fetchDevicesByDlgFlowRefAndInstanceId(String ref, Integer instanceId) {
        return repository.findESPDevicesByDialogFlowRefLikeAndInstance_Id(ref,instanceId);
    }


    public ESPDevice storeOrUpdateDevice(ESPDevice espDevice) {

        return repository.save(espDevice);
    }

    public void deleteESPDevice(ESPDevice espDevice) {
        repository.delete(espDevice);
    }

    public String decodeDeviceCred(String str) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(str));
    }

    public String encodeDeviceCred(String str) {
        return Base64.encodeBase64String(StringUtils.getBytesUtf8(str));
    }
}
