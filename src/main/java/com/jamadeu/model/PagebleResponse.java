package com.jamadeu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jean Amadeu 07/15/2020
 */
public class PagebleResponse<T> extends PageImpl<T> {
    private boolean first;
    private boolean last;
    private int totalPages;

    public PagebleResponse(@JsonProperty("content") List<T> content,
                           @JsonProperty("number") int page,
                           @JsonProperty("size") int size,
                           @JsonProperty("pageable") JsonNode pageable,
                           @JsonProperty("totalElements") long totalElements,
                           @JsonProperty("sort") JsonNode sort) {
        super(content, PageRequest.of(page, size), totalElements);
    }

    public PagebleResponse() {
        super(new ArrayList<>());
    }

    @Override
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
