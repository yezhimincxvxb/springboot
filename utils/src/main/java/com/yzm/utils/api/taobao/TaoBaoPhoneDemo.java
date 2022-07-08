package com.yzm.utils.api.taobao;

import com.alibaba.fastjson.JSON;
import com.yzm.utils.http.HttpRequestUtil;

import java.nio.charset.StandardCharsets;

public class TaoBaoPhoneDemo {

    public static void main(String[] args) {
        String message = HttpRequestUtil.sendPost("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm",
                "tel=13672787520", "GBK");
        System.out.println("message = " + message);
        int index = message.indexOf("{");
        String substring = message.substring(index);
        PhoneInfo phoneInfo = JSON.parseObject(new String(substring.getBytes(), StandardCharsets.UTF_8), PhoneInfo.class);
        System.out.println(phoneInfo);
        System.out.println(JSON.toJSONString(phoneInfo, true));
    }
}
