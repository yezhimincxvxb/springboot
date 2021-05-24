package com.yzm.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel
public class PageSearch<T> implements Serializable {
    private static final long serialVersionUID = 2083993467648559840L;

    @ApiModelProperty(value = "第几页", position = 1, example = "1")
    private Integer page = 1;
    @ApiModelProperty(value = "每页显示多少条数据", position = 2, example = "10")
    private Integer size = 10;
    @ApiModelProperty(value = "搜索条件", position = 3)
    private T where;

    public PageSearch(Integer page, Integer size, T t) {
        this.page = page;
        this.size = size;
        this.where = t;
    }
}
