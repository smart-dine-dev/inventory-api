package com.devstack.SmartDine.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * EnvironmentPostProcessor that automatically loads environment variables from a .env file
 * into the Spring Environment and JVM System properties before application startup.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("removal")
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME = "systemEnvironment";
    private static final String DOTENV_PROPERTY_SOURCE_NAME = "dotenvProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        Map<String, Object> envProperties = new HashMap<>();
        dotenv.entries().forEach(entry -> {
            envProperties.put(entry.getKey(), entry.getValue());
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });

        if (!envProperties.isEmpty()) {
            if (environment.getPropertySources().contains(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)) {
                environment.getPropertySources().addAfter(
                        SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                        new MapPropertySource(DOTENV_PROPERTY_SOURCE_NAME, envProperties)
                );
            } else {
                environment.getPropertySources().addFirst(
                        new MapPropertySource(DOTENV_PROPERTY_SOURCE_NAME, envProperties)
                );
            }
        }
    }
}
