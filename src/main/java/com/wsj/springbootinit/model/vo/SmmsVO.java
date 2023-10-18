package com.wsj.springbootinit.model.vo;

import lombok.Data;

@Data
public class SmmsVO {
    private Boolean success;
    private String url;
    private String hash;
    private String message;
}
