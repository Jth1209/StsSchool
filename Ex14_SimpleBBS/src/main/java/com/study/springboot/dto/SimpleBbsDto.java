package com.study.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SimpleBbsDto {
    private int id;
    private String writer;
    private String title;
    private String content;
}
