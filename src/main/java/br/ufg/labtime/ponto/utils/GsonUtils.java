package br.ufg.labtime.ponto.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static br.ufg.labtime.ponto.utils.InterfaceSerializer.interfaceSerializer;


public class GsonUtils {

    static String stringify(Object o) {
        return new Gson().toJson(o);
    }

    // Solution: Interface can't be instantiated!
    // http://qaru.site/questions/6320535/gson-deserialzing-objects-containing-lists
    public static Object fromJson(String json, Class c, Class parent) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(parent, interfaceSerializer(c))
                .create();
        return gson.fromJson(json, c);
    }

    static Object fromJson(String json, Class clazz) {
        return new Gson().fromJson(json, clazz);
    }
}
