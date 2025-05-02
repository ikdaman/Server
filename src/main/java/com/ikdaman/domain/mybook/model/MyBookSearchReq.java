package com.ikdaman.domain.mybook.model;

import lombok.Getter;

@Getter
public class MyBookSearchReq {

    private String status; //completed or in-progress
    private String keyword;
    private int page = 1;
    private int limit = 9;
}
