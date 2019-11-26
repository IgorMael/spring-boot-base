package br.ufg.labtime.ponto.dto;

import br.ufg.labtime.ponto.service.ModelMapperService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResultListDTO<V, T> {

    private long totalResults;
    private int totalPages;
    private int currentPage;
    private List<T> result;


    public ResultListDTO(Page<V> resultPage,
                         ModelMapperService modelMapperService,
                         Class<T> tClass) {

        this.totalPages = resultPage.getTotalPages();
        this.totalResults = resultPage.getTotalElements();
        this.currentPage = resultPage.getPageable().getPageNumber();
        this.result = modelMapperService.toList(resultPage.getContent(), tClass);
    }
}
