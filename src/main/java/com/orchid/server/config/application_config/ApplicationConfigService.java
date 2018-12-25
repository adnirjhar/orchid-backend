package com.orchid.server.config.application_config;

import com.orchid.server.domain.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ApplicationConfigService {

    @Autowired
    ApplicationConfigRepository configRepository;

    public ApplicationConfig isInitialDataLoaded() {
        return configRepository.findApplicationConfigByConfigName("isInitialDataLoaded");
    }

    public ApplicationConfig getMQTTHost() {
        return configRepository.findApplicationConfigByConfigName("mqttServer");
    }

    public ApplicationConfig getMQTTPort() {
        return configRepository.findApplicationConfigByConfigName("mqttPort");
    }

    public ApplicationConfig getMQTTUsername() {
        return configRepository.findApplicationConfigByConfigName("orchidMQTTUsername");
    }

    public ApplicationConfig getMQTTPassword() {
        return configRepository.findApplicationConfigByConfigName("orchidMQTTPassword");
    }

    public ApplicationConfig getCommonMQTTChannel() {
        return configRepository.findApplicationConfigByConfigName("orchidCommonChannel");
    }

    public ApplicationConfig getConfigByName(String configName) {
        return configRepository.findApplicationConfigByConfigName(configName);
    }

    public ApplicationConfig storeOrUpdateConfig(ApplicationConfig applicationConfig) {
        return configRepository.save(applicationConfig);
    }

    public PasswordEncoder passwordEncoder() {
        int strength = 11;
        SecureRandom random = new SecureRandom();
        return new BCryptPasswordEncoder(strength, random);
    }

}
