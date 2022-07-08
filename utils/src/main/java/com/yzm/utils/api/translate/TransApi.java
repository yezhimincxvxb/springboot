package com.yzm.utils.api.translate;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class TransApi {

    private static final String APP_ID = "20210406000764679";
    private static final String SECURITY_KEY = "PYWURLxMHlfpAIsPZGoa";
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    public static String getTransResult(String query, String from, String to) {
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", APP_ID);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = APP_ID + query + salt + SECURITY_KEY; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return HttpGet.get(TRANS_API_HOST, params);
    }

    public static void main(String[] args) {
        //bagaimana melanjutkan pembayaran // 印尼
        //วิธีการดำเนินการชำระเงิน 泰国
        //hướng dẫn thanh toán 越南
        //paano po mg order 菲律宾
        String query = "bagaimana melanjutkan pembayaran";
        // 通用翻译
        String result = getTransResult(query, "auto", "en");

        Trans trans = JSON.parseObject(result, Trans.class);
        System.out.println("trans = " + trans);
        System.out.println(JSON.toJSONString(trans, true));
    }

}
