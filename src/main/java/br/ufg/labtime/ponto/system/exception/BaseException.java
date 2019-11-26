package br.ufg.labtime.ponto.system.exception;

import java.util.ArrayList;
import java.util.List;

public class BaseException extends RuntimeException {

    private List<ErrorResponseHttp> errorResponsHttps = new ArrayList<>();

    public BaseException() {
    }

    public BaseException(String description) {
        super(description);
    }

    public BaseException(String description, List<Error> errors) {
        super(description);
    }

    public BaseException(List<ErrorResponseHttp> errorResponsHttps) {
        this.errorResponsHttps = errorResponsHttps;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<ErrorResponseHttp> getErrorResponsHttps() {
        return errorResponsHttps;
    }

    public void setErrorResponseHttps(List<ErrorResponseHttp> errorResponsHttps) {
        this.errorResponsHttps = errorResponsHttps;
    }
}
