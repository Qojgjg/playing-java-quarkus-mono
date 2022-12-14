package org.acme.utils;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class TestContainerResource_no implements QuarkusTestResourceLifecycleManager {
    private static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>("postgres:13");

    @Override
    public Map<String, String> start() {
        DATABASE.start();
        Map<String, String> confMap = new HashMap<>();
        confMap.put("quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl());
        confMap.put("quarkus.datasource.username", DATABASE.getUsername());
        confMap.put("quarkus.datasource.password", DATABASE.getPassword());
        return confMap;
    }

    @Override
    public void stop() {
        DATABASE.close();
    }

}