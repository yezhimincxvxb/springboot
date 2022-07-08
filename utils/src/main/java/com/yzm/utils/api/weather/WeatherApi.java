package com.yzm.utils.api.weather;

import lombok.Data;

/**
 * 天气接收实体类
 */
@Data
public class WeatherApi {
    /**
     * 城市ID
     */
    private String cityid;
    /**
     * 当前日期
     */
    private String date;
    /**
     * 当前星期
     */
    private String week;
    /**
     * 气象台更新时间
     */
    private String update_time;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 城市英文名称
     */
    private String cityEn;
    /**
     * 国家名称
     */
    private String country;
    /**
     * 国家英文名称
     */
    private String countryEn;
    /**
     * 天气情况
     */
    private String wea;
    /**
     * 天气对应图标
     * 固定9种类型(您也可以根据wea字段自己处理):
     * xue、lei、shachen、wu、bingbao、yun、yu、yin、qing
     */
    private String wea_img;
    /**
     * 实时温度
     */
    private String tem;
    /**
     * 高温
     */
    private String tem1;
    /**
     * 低温
     */
    private String tem2;
    /**
     * 风向
     */
    private String win;
    /**
     * 风力等级
     */
    private String win_speed;
    /**
     * 风速
     */
    private String win_meter;
    /**
     * 湿度
     */
    private String humidity;
    /**
     * 能见度
     */
    private String visibility;
    /**
     * 气压hPa
     */
    private String pressure;
    /**
     * 空气质量
     */
    private String air;
    /**
     *
     */
    private String air_pm25;
    /**
     * 空气质量等级
     */
    private String air_level;
    /**
     * 空气质量描述
     */
    private String air_tips;
    /**
     * 气象预警
     */
    private Alarm alarm;
    /**
     * 空气质量指数
     */
    private Aqi aqi;
}

@Data
class Alarm {
    /**
     * 预警类型
     */
    private String alarm_type;
    /**
     * 预警级别
     */
    private String alarm_level;
    /**
     * 预警详细信息
     */
    private String alarm_content;
}

@Data
class Aqi {
    /**
     * 空气质量
     */
    private String air;
    /**
     * 空气质量等级
     */
    private String air_level;
    /**
     * 空气质量提示
     */
    private String air_tips;
    /**
     * PM2.5
     */
    private String pm25;
    /**
     * PM2.5等级描述
     */
    private String pm25_desc;
    /**
     * PM10
     */
    private String pm10;
    /**
     * PM10等级描述
     */
    private String pm10_desc;
    /**
     * o3
     */
    private String o3;
    /**
     * o3等级描述
     */
    private String o3_desc;
    /**
     * no2
     */
    private String no2;
    /**
     * no2等级描述
     */
    private String no2_desc;
    /**
     * so2
     */
    private String so2;
    /**
     * so2等级描述
     */
    private String so2_desc;
    /**
     * 是否需要带口罩
     */
    private String kouzhao;
    /**
     * 外出适宜
     */
    private String waichu;
    /**
     *开窗适宜
     */
    private String kaichuang;
    /**
     * 是否需要打开净化器
     */
    private String jinghuaqi;
    private String cityid;
    private String city;
    private String cityEn;
    private String country;
    private String countryEn;
}
