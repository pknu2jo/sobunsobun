package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GrDate {

    // 페이지네이션
    private int start;

    private int end;

    private Date firstdate;

    private Date seconddate;

    private String memId;

}
