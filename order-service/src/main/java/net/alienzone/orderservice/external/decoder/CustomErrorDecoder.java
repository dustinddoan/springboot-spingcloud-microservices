package net.alienzone.orderservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import net.alienzone.orderservice.exception.CustomException;
import net.alienzone.orderservice.external.response.ErrorResponse;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper mapper = new ObjectMapper();

        log.info("Custom Error Decoder::{}", response.request().url());
        log.info("Custom Error Decoder::{}", response.request().headers());

        try {
            ErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error", "INTERNAL_SERVER_ERROR", 505);
        }
    }
}
