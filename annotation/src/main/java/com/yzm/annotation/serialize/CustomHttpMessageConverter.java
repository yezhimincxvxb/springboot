package com.yzm.annotation.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * 自定义消息转换器
 */
public class CustomHttpMessageConverter extends FastJsonHttpMessageConverter {

    private Charset charset;
    private SerializerFeature[] features;

    public CustomHttpMessageConverter() {
        super();
        // 初始化
        setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json", UTF8),
                new MediaType("application", "*+json", UTF8),
                new MediaType("application", "jsonp", UTF8),
                new MediaType("application", "*+jsonp", UTF8)));
        setCharset(UTF8);
        setFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat
        );
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (obj instanceof JsonFilterObject) {
            JsonFilterObject jsonFilterObject = (JsonFilterObject) obj;
            JsonSerializerFilter jsonSerializerFilter = new JsonSerializerFilter(jsonFilterObject.getIncludeMap(), jsonFilterObject.getExcludeMap());
            String text = JSON.toJSONString(jsonFilterObject.getObject(), jsonSerializerFilter, features);
            byte[] bytes = text.getBytes(charset);
            outputMessage.getBody().write(bytes);
        }
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void setFeatures(SerializerFeature... features) {
        this.features = features;
    }

    @Override
    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        super.setSupportedMediaTypes(supportedMediaTypes);
    }
}
