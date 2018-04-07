package org.mycompany.interceptor;

import org.mycompany.model.IntegrationLogEntity;
import org.mycompany.repository.MyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    private final MyRepository myRepository;

    public LoggingRequestInterceptor(MyRepository myRepository) {
        this.myRepository = myRepository;
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest,
                                        byte[] body,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        IntegrationLogEntity.Request request = traceRequest(httpRequest, body);
        ClientHttpResponse httpResponse = clientHttpRequestExecution.execute(httpRequest, body);
        IntegrationLogEntity.Response response = traceResponse(httpResponse);

        IntegrationLogEntity logEntity = IntegrationLogEntity.builder()
                .request(request)
                .response(response)
                .time(new Date())
                .build();

        myRepository.save(logEntity);

        return httpResponse;
    }

    private IntegrationLogEntity.Request traceRequest(HttpRequest httpRequest, byte[] body) throws IOException {
        IntegrationLogEntity.Request request = IntegrationLogEntity.Request.builder()
                .uri(httpRequest.getURI())
                .httpMethod(httpRequest.getMethod())
                .httpHeaders(httpRequest.getHeaders())
                .requestBody(new String(body, "UTF-8"))
                .build();

        log.debug("=================================== request begin ===================================");
        log.debug("URI         : {}", request.getUri());
        log.debug("Method      : {}", request.getHttpMethod());
        log.debug("Headers     : {}", request.getHttpHeaders());
        log.debug("Request body: {}", request.getRequestBody());
        log.debug("==================================== request end ====================================");

        return request;
    }

    private IntegrationLogEntity.Response traceResponse(ClientHttpResponse httpResponse) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
            line = bufferedReader.readLine();
        }

        IntegrationLogEntity.Response response = IntegrationLogEntity.Response.builder()
                .statusCode(httpResponse.getStatusCode().value())
                .statusText(httpResponse.getStatusText())
                .httpHeaders(httpResponse.getHeaders())
                .responseBody(stringBuilder.toString())
                .build();

        log.debug("==================================== response begin ====================================");
        log.debug("Status code  : {}", response.getStatusCode());
        log.debug("Status text  : {}", response.getStatusText());
        log.debug("Headers      : {}", response.getHttpHeaders());
        log.debug("Response body: {}", response.getResponseBody());
        log.debug("===================================== response end =====================================");

        return response;
    }

}
