package com.orchid.server.esp_device_log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class ESPDeviceLogRESTController {

    @Autowired
    ESPDeviceLogController ESPDeviceLogController;

}
