package com.kefiya.home.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Data
public class ApiPageRequest implements Serializable {
        Boolean isPaginated;
        int pageNumber;
        int pageSize;
        boolean isSorted;
        String orderBy;
        String sortedBy;
        String keyword;
        Long totalElements;
        int totalPage;

        public static Pageable toPageRequest(ApiPageRequest request) {
            Sort sort = Sort.by(Sort.Direction.DESC, new String[]{"id"});
            return PageRequest.of(request.getPageNumber(), 20, sort);
        }

        public static Pageable toPageRequest(ApiPageRequest request, Sort sort) {
            return PageRequest.of(request.getPageNumber(), 20, sort);
        }

        public static Pageable toPageRequest(int pageNumber, Sort sort) {
            return PageRequest.of(pageNumber, 20, sort);
        }

        public static Pageable toPageRequest(int pageNumber) {
            Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, new String[]{"id"});
            return PageRequest.of(pageNumber, 20, sort);
        }

        public static ApiPageRequest toApiPageRequest( ApiPageRequest request, int totalPage, Long totalElement) {
            ApiPageRequest api = new ApiPageRequest();
            api.setIsPaginated(request.getIsPaginated());
            api.setKeyword(request.getKeyword());
            api.setPageNumber(request.getPageNumber());
            api.setTotalPage(totalPage);
            api.setTotalElements(totalElement);
            return api;
        }
}
