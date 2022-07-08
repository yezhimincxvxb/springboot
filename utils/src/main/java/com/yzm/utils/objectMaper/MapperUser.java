package com.yzm.utils.objectMaper;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class MapperUser implements Serializable {
    private static final long serialVersionUID = 623406234647541848L;

    private Long id;
    private String name;
    private Integer age;
    private BigDecimal money;
    private Date createTime;
    private LocalDateTime updateTime;
}
