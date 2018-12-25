package com.orchid.server.esp_hardware;

import com.orchid.server.domain.ESPDevice;
import com.orchid.server.domain.ESPHardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ESPHardwareJPARepository extends JpaRepository<ESPHardware, Integer> {

    public ESPHardware findESPHardwareById(Integer id);

    public ESPHardware findESPHardwareByHardwareIdAndEspDevice(String hardwareId,ESPDevice espDevice);

    public List<ESPHardware> findESPHardwaresByHardwareIdAndEspDevice(String hardwareId,ESPDevice espDevice);

    public List<ESPHardware> findESPHardwaresByTitleLikeAndEspDevice(String title,ESPDevice espDevice);

    public List<ESPHardware> findESPHardwaresByDialogFlowRefLikeAndEspDevice(String ref,ESPDevice espDevice);

    public List<ESPHardware> findESPHardwaresByTypeAndEspDevice(String type,ESPDevice espDevice);

    public List<ESPHardware> findESPHardwaresByType(String type);
}
