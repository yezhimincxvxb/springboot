package com.yzm.common.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -7016165505111217188L;

    // 显示数量
    @ApiModelProperty(value = "显示数量", position = 1)
    private long count;
    // 当前页
    @ApiModelProperty(value = "当前页", position = 2)
    private long current;
    // 总数量
    @ApiModelProperty(value = "总数量", position = 3)
    private long total;
    // 总页数
    @ApiModelProperty(value = "总页数", position = 4)
    private long pages;
    // 数据
    @ApiModelProperty(value = "数据", position = 5)
    private List<T> list;

    public PageResult() {
    }

    public PageResult(IPage<?> page, List<T> list) {
        this.count = page.getSize();
        this.current = page.getCurrent();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.list = list;
    }

}
