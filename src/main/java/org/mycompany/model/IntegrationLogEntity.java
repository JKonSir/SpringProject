package org.mycompany.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.net.URI;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "integration-log")
public class IntegrationLogEntity implements Serializable {

    @Id
    private String id;

    private long time;

    private Request request;

    private Response response;

    @Data
    @Builder
    public static class Request {

        private URI uri;

        private HttpMethod httpMethod;

        private HttpHeaders httpHeaders;

        private String requestBody;

    }

    @Data
    @Builder
    public static class Response {

        private int statusCode;

        private String statusText;

        private HttpHeaders httpHeaders;

        private String responseBody;

    }

}
