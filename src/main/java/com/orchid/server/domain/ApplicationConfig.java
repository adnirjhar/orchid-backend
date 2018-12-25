package com.orchid.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.PublicKey;

@Entity
@Table(name = "application_config")
public class ApplicationConfig {

    @Id
    @Column(name = "config_name")
    private String configName;

    @Column(name = "config_value")
    private String configValue;

    public ApplicationConfig() {}

    public ApplicationConfig(String configName, String configValue) {
        this.configName = configName;
        this.configValue = configValue;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
