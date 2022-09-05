package org.acme.utils;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import org.acme.common.security.TokenService;
import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class KeycloakRealmResource implements QuarkusTestResourceLifecycleManager {

    @ClassRule
    public static DockerComposeContainer KEYCLOAK = new DockerComposeContainer(
            new File("src/main/docker/keycloak.yml"))
            .withExposedService("keycloak_1",
                    9080,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)));

    @Override
    public Map<String, String> start() {
        KEYCLOAK.start();
      
        String jwtIssuerUrl = String.format("http://%s:%s/auth/realms/quarkus-realm",
                KEYCLOAK.getServiceHost("keycloak_1", 9080),
                KEYCLOAK.getServicePort("keycloak_1", 9080));

        jwtIssuerUrl = "http://localhost:9080/auth/realms/quarkushop-realm";

        TokenService tokenService = new TokenService();
        Map<String, String> config = new HashMap<>();

        try {

            String adminAccessToken = tokenService.getAccessToken(jwtIssuerUrl,
                    "sebastian",
                    "Mtor82",
                    "quarkushop",
                    "mysecret");

            String testAccessToken = tokenService.getAccessToken(jwtIssuerUrl,
                    "sebastian",
                    "Mtor82",
                    "quarkushop",
                    "mysecret");

     //       System.out.println("Token obtenido que voy a guardar:" + adminAccessToken);
            System.setProperty("quarkus-admin-access-token", adminAccessToken);
            System.setProperty("quarkus-test-access-token", testAccessToken);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.setProperty("mp.jwt.verify.publickey.location", jwtIssuerUrl + "/protocol/openid-connect/certs");
        System.setProperty("mp.jwt.verify.issuer", jwtIssuerUrl);

        return config;
    }

    @Override
    public void stop() {
        KEYCLOAK.stop();
    }
}
