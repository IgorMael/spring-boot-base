package br.ufg.labtime.ponto.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

public class BaseModel {

    @Expose
    @Transient
    private Map<String, Object> mapAttributes = new HashMap<>();

    public Map<String, Object> getMapAttributes() {
        return mapAttributes;
    }

    public void setMapAttributes(Map<String, Object> mapAttributes) {
        this.mapAttributes = mapAttributes;
    }
}
