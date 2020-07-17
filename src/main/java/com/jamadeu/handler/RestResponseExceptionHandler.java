package com.jamadeu.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * @author Jean Amadeu 07/17/2020
 */
@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler {
    @Override
    protected boolean hasError(HttpStatus statusCode) {
        System.out.println("Inside hasError");
        return super.hasError(statusCode);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("Doing something with status code " + response.getStatusCode());
        System.out.println("Doing something with status body " + IOUtils.toString(response.getBody(), "UTF-8"));
        super.handleError(response);
    }
}
