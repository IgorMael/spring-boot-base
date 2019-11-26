package br.ufg.labtime.ponto.system.exception;

import java.util.List;

public class BusinessException extends BaseException {

    public BusinessException(List<ErrorResponseHttp> errorResponseHttpList) {
        super.setErrorResponseHttps(errorResponseHttpList);
    }
}
