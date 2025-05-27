package com.ikdaman.domain.mybook.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyBookSearchReq {

    private String status; //completed or in-progress
    private String keyword;
    private int page = 1;
    private int limit = 9;
}
