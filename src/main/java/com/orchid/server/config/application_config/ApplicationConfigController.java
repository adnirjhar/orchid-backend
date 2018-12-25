package com.orchid.server.config.application_config;

import com.orchid.server.domain.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class ApplicationConfigController {

    @Autowired
    ApplicationConfigService applicationConfigService;

    @RequestMapping(method = RequestMethod.POST, value="/addConfig")
    public ApplicationConfig addConfig(@RequestBody ApplicationConfig device) {
        return applicationConfigService.storeOrUpdateConfig(device);
    }
}
