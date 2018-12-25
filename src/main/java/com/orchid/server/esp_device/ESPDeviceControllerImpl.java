package com.orchid.server.esp_device;

import com.orchid.server.config.application_config.ApplicationConfigService;
import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.Instance;
import com.orchid.server.instance.InstanceJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ESPDeviceControllerImpl implements ESPDeviceController {

    @Autowired
    ESPDeviceJPAService espDeviceJPAService;

    @Autowired
    InstanceJPAService instanceJPAService;

    @Autowired
    ApplicationConfigService applicationConfigService;

    @Override
    public String getESPConfig(String deviceSecret) throws Exception {
        String deviceId = null;
        String instanceName = null;

        try {
            String[] cred = this.espDeviceJPAService.decodeDeviceCred(deviceSecret).split(":");
            deviceId = cred[0];
            instanceName = cred[1];
        }
        catch (Exception ex) {
            throw new Exception("Credentials in wrong format");
        }

        if (deviceId == null || instanceName == null || deviceId.length() == 0 || instanceName.length() == 0) {
            throw new Exception("Invalid deviceid or instanceid");
        }

        try {
            Instance ins = instanceJPAService.fetchInstanceByName(instanceName);
            ESPDevice dev = this.espDeviceJPAService.fetchESPDeviceByDeviceIdAndInstanceId(deviceId,ins.getId());
            String separator = "|";
            String response = "[";
            response += applicationConfigService.getMQTTHost().getConfigValue() + separator;
            response += applicationConfigService.getMQTTPort().getConfigValue() + separator;
            response += applicationConfigService.getMQTTUsername().getConfigValue() + separator;
            response += applicationConfigService.getMQTTPassword().getConfigValue() + separator;
            response += applicationConfigService.getCommonMQTTChannel().getConfigValue() + separator;
            response += dev.getDeviceChannel() + "]";

            return response;
        }
        catch (Exception ex) {
            throw new Exception("Invalid deviceid or instanceid");
        }
    }

    @Override
    public List<ESPDevice> fetchAllESPDevices() {
        return espDeviceJPAService.fetchAllESPDevices();
    }

    @Override
    public ESPDevice fetchESPDeviceById(Integer deviceId) {
        return espDeviceJPAService.fetchESPDeviceById(deviceId);
    }

    @Override
    public ESPDevice fetchESPDeviceByDeviceIdAndInstanceId(String deviceId,String instanceId) {
        return espDeviceJPAService.fetchESPDeviceByDeviceIdAndInstanceId(deviceId,instanceId);
    }

    @Override
    public List<ESPDevice> fetchESPDevicesByInstanceId(Integer instanceId) {
        return espDeviceJPAService.fetchESPDevicesByInstanceId(instanceId);
    }

    @Override
    public List<ESPDevice> fetchDevicesByDlgFlowRefAndInstanceId(String ref, Integer instanceId) {
        return espDeviceJPAService.fetchDevicesByDlgFlowRefAndInstanceId(ref,instanceId);
    }

    @Override
    public ESPDevice storeESPDevice(ESPDevice espDevice) throws Exception {
        Integer id = espDevice.getId();
        if (id != null) {
            throw new Exception("Id has to be null.There is another API for updating");
        }

        String deviceId = espDevice.getDeviceId();
        Integer instanceId = espDevice.getInstance().getId();
        if (deviceId == null || deviceId.length() == 0 || instanceId == null) {
            throw new Exception("Invalid espDevice id or instance id");
        }

        Instance instance = instanceJPAService.fetchInstanceById(instanceId);
        if (instance == null) {
            throw new Exception("Instance not found");
        }

        ESPDevice foundByESPDeviceId = espDeviceJPAService.fetchESPDeviceByDeviceIdAndInstanceId(deviceId,instanceId);
        if (foundByESPDeviceId != null) {
            throw new Exception("ESPDevice already exists");
        }

        String str = espDevice.getDeviceId() + ":" + espDevice.getInstance().getName();
        espDevice.setDeviceSecret(espDeviceJPAService.encodeDeviceCred(str));
        espDevice.setDeviceChannel(deviceId + ":" + instance.getName() + ":" + this.generateRandomString());
        espDevice.setInstance(instance);

        return espDeviceJPAService.storeOrUpdateDevice(espDevice);
    }

    @Override
    public ESPDevice updateESPDevice(ESPDevice espDevice) throws Exception {
        Integer id = espDevice.getId();
        if (id == null) {
            throw new Exception("No espDevice id provided");
        }
        ESPDevice oldESPDevice = espDeviceJPAService.fetchESPDeviceById(id);
        if (oldESPDevice == null) {
            throw new Exception("ESPDevice not found");
        }

        String oldDeviceId = oldESPDevice.getDeviceId();
        String oldDeviceInstanceId = oldESPDevice.getInstance().getName();
        String newDeviceId = espDevice.getDeviceId();
        String newDeviceInsId = espDevice.getInstance().getName();

        if (!oldDeviceId.equals(newDeviceId) || !oldDeviceInstanceId.equals(newDeviceInsId) ) {
            espDevice.setDeviceSecret(espDeviceJPAService.encodeDeviceCred(newDeviceId + ":" + newDeviceInsId));
            espDevice.setDeviceChannel(newDeviceId + ":" + newDeviceInsId + ":" + this.generateRandomString());
        }
        else {
            ///********************************************************
            ///  Incase deviceId and instanceId doesnt change dont set the secret and the channel
            ///********************************************************
            espDevice.setDeviceSecret(oldESPDevice.getDeviceSecret());
            espDevice.setDeviceChannel(oldESPDevice.getDeviceChannel());
        }

        return espDeviceJPAService.storeOrUpdateDevice(espDevice);
    }

    @Override
    public void deleteESPDevice(ESPDevice espDevice) {
        ESPDevice devToDel = this.espDeviceJPAService.fetchESPDeviceById(espDevice.getId());
        espDeviceJPAService.deleteESPDevice(devToDel);
    }

    private String generateRandomString() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

        return sb.toString();
    }

}
