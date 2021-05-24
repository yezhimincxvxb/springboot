package com.yzm.annotation.serialize;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

/**
 *  对SimplePropertyPreFilter进行一层封装*
 */
public class JsonSerializerFilter extends SimplePropertyPreFilter {
    private Map<Class, Set<String>> includes;
    private  Map<Class, Set<String>> excludes;

    public JsonSerializerFilter(Map<Class, Set<String>> includes, Map<Class, Set<String>> excludes) {
        this.includes = includes;
        this.excludes = excludes;
    }

    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if(!CollectionUtils.isEmpty(includes)){
            for (Map.Entry<Class, Set<String>> include : includes.entrySet()){
                Class objClass = include.getKey();
                Set<String> includeProp = include.getValue();
                if(source.getClass().isAssignableFrom(objClass)){
                    return includeProp.contains(name);
                }
            }
        }
        if(!CollectionUtils.isEmpty(excludes)){
            for (Map.Entry<Class, Set<String>> exclude : excludes.entrySet()){
                Class objClass = exclude.getKey();
                Set<String> includeProp = exclude.getValue();
                if(source.getClass().isAssignableFrom(objClass)){
                    return !includeProp.contains(name);
                }
            }
        }
        return true;
    }

}
