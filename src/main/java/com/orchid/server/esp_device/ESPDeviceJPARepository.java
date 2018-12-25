package com.orchid.server.esp_device;

import com.orchid.server.domain.ESPDevice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ESPDeviceJPARepository extends CrudRepository<ESPDevice, Integer> {

    public ESPDevice findESPDeviceById(Integer id);

    public List<ESPDevice> findESPDevicesByInstance_Id(Integer id);

    public ESPDevice findESPDeviceByDeviceIdAndInstance_Id(String deviceId,Integer id);

    public ESPDevice findESPDeviceByDeviceIdAndInstance_Name(String deviceId,String insId);

    public List<ESPDevice> findESPDevicesByDialogFlowRefLikeAndInstance_Id(String ref, Integer insId);

}
