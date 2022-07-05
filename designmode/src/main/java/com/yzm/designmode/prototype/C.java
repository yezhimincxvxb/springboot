package com.yzm.designmode.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class C implements Serializable {
    private static final long serialVersionUID = 7096298633803818663L;
    private String desc;
}