package org.acme.security;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.security.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
public class TokenService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    Provider<String> jwtIssuerUrlProvider;

    @ConfigProperty(name = "keycloak.credentials.client-id")
    Provider<String> clientIdProvider;


    public String getAccessToken(String userName, String password) {

            var accessToken = "";
            try {
                accessToken = getAccessToken(jwtIssuerUrlProvider.get(), userName, password, clientIdProvider.get(), null);
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Cannot get the access_token");
            }
            return accessToken;
        };
    

        public String getAccessToken(String jwtIssuerUrl, String userName, String password, String clientId, String clientSecret) throws IOException, InterruptedException {
            String tokenizer = jwtIssuerUrl + "/protocol/openid-connect/token";
            log.error("Tonces: "+tokenizer);
            String requestBody = "username=" + userName + "&password=" + password + "&grant_type=password&client_id=" + clientId;
    
            if (clientSecret != null) {
                requestBody += "&client_secret=" + clientSecret;
            }
    
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenizer))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(requestBody))
                    .build();
    
                    log.error("Request: "+requestBody);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
            log.error("Response: "+response);

            var accessToken = "";
    
            if (response.statusCode() == 200) {
                var mapper = new ObjectMapper();
                try {
                    accessToken = mapper.readTree(response.body()).get("access_token").textValue();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            } else {
                log.error("UnauthorizedException");
                throw new UnauthorizedException();
            }
    
            return accessToken;
        }

}
