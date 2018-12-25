package com.orchid.server.config.application_config;

import com.orchid.server.domain.ApplicationConfig;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationConfigRepository extends CrudRepository<ApplicationConfig, String> {

    public ApplicationConfig findApplicationConfigByConfigName(String configName);
}
