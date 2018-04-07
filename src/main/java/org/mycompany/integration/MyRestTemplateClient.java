package org.mycompany.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyRestTemplateClient {

    private final String testRestServerUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public MyRestTemplateClient(@Value("${service.test-rest-server.uri}") String testRestServerUrl,
                                RestTemplate restTemplate) {
        this.testRestServerUrl = testRestServerUrl;
        this.restTemplate = restTemplate;
    }

    public String get() {
        return restTemplate.exchange(testRestServerUrl + "tra-ta-ta", HttpMethod.GET, HttpEntity.EMPTY, String.class)
                .getBody();
    }

}
