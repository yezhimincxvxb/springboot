package com.yzm.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "car", description = "车信息", parent = User.class)
public class Car {
    @ApiModelProperty(name = "number", value = "车牌号", position = 1, example = "粤B 888888")
    private String number;
    @ApiModelProperty(name = "price", value = "价格", position = 2, example = "50w")
    private String price;

}
