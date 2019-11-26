package br.ufg.labtime.ponto.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMapperService {

    private final ModelMapper modelMapper = new ModelMapper();

    public ModelMapperService() {
        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
    }

    public <T> List<T> toList(List<?> items, Class<T> clazz) {
        return items.stream()
                .map(item -> modelMapper.map(item, clazz))
                .collect(Collectors.toList());
    }

    public <T> T toObject(Object item, Class<T> clazz) {
        if (item == null) return null;
        return modelMapper.map(item, clazz);
    }
}
