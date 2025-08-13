package com.kefiya.home.common;


import java.io.Serializable;
import java.util.List;

public class ApiPageResponse <T> implements Serializable {
    ApiPageRequest apiPageRequest;
    List<T> result;

}
