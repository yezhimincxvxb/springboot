package com.yzm.utils.api.weather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzm.utils.http.HttpRequestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取城市实时天气
 */
public class WeatherApiUtils {

    private static final String appid = "42293238";
    private static final String appsecret = "1BucM1Mk";
    private static final String version = "v61";
    private static final String appurl = "https://tianqiapi.com/api";

    public static String getCityTemWeather(String cityName) {
        if (null != cityName && cityName.length() > 2) {
            // 最后一个字如果是市/县...则去掉
            cityName = cityName.replaceAll("(省|市|自治区|自治州|县|区)$", "");
        }
        // 拼接参数
        Map<String, Object> map = new HashMap<>();
        map.put("version", version);
        map.put("appid", appid);
        map.put("appsecret", appsecret);
        map.put("city", cityName);
        String params = HttpRequestUtil.joinMapValue(map, '&');
        // 发送请求
        return HttpRequestUtil.sendGet(appurl, params, null);

//        return JSON.parseObject(weatherInfo, WeatherApi.class);
    }

    public static void main(String[] args) {
//        WeatherApi wa = getCityTemWeather("深圳市");
        String s = getCityTemWeather("深圳市");
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(JSON.toJSONString(jsonObject, true));
    }

}
