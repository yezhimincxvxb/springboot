package com.yzm.utils.api.translate;

import lombok.Data;

import java.io.Serializable;

@Data
public class Trans implements Serializable {
    private static final long serialVersionUID = -1506367348305081901L;
    private String from;
    private String to;
    private TransResult[] trans_result;

}

@Data
class TransResult implements Serializable {
    private static final long serialVersionUID = -4439060213894662467L;
    private String src;
    private String dst;
}

