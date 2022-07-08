package com.yzm.utils.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String username;
    private boolean enable;
    private LocalDateTime localDateTime;
}
