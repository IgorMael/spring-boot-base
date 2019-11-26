package br.ufg.labtime.ponto.system.exception;


import java.util.List;
import java.util.Map;

public class ErrorResponseHttp {
    private String code;
    private Map<String, String> parameters;
    private List<String> observations;
    private Object object;

    public ErrorResponseHttp(String code) {
        this.code = code;
    }

    public ErrorResponseHttp(String code, Map<String, String> parameters) {
        this.code = code;
        this.parameters = parameters;
    }

    public ErrorResponseHttp(String code, List<String> observations) {
        this.code = code;
        this.observations = observations;
    }

    public ErrorResponseHttp(String code, Object object) {
        this.code = code;
        this.object = object;
    }

    public ErrorResponseHttp(String code, Map<String, String> parameters, List<String> observations, Object object) {
        this.code = code;
        this.parameters = parameters;
        this.observations = observations;
        this.object = object;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getObservations() {
        return observations;
    }

    public void setObservations(List<String> observations) {
        this.observations = observations;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
