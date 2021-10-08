package com.kkb.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ResultData<T> {
    //每次查询的数据集合
    private List<T> rows = new ArrayList<>();
    //总数量
    private int total;

}
