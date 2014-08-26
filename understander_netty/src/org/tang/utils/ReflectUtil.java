package org.tang.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import com.alibaba.fastjson.JSON;


/**
 * 
 * @author will_awoke
 * @version 2014-5-30
 * @see ReflectUtil
 * @since
 */
public class ReflectUtil
{

    /**
     * 将jdbcTemplate查询的map结果集 反射生成对应的bean
     * @param clazz 意向反射的实体.clazz
     * @param jdbcMapResult 查询结果集  key is UpperCase
     * @return 
     * @see
     */
    public static <T> T reflect(Class<T> clazz, Map<String, Object> jdbcMapResult)
    {
        //获得
        Field[] fields = clazz.getDeclaredFields();

        //存放field和column对应关系，该关系来自于实体类的 @Column配置
        Map<String/*field name in modelBean*/, String/*column in db*/> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();
        Annotation[] annotations = null;
        for (Field field : fields)
        {
            annotations = field.getAnnotations();
            for (Annotation an : annotations)
            {
                if (an instanceof Column)
                {
                    Column column = (Column)an;
                    fieldHasColumnAnnoMap.put(field.getName(), column.name());
                }
            }
        }
        //存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
        Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet())
        {
            //将column大写。因为jdbcMapResult key is UpperCase
            String key = en.getValue().toUpperCase();
            
            //获得map的该field的属性值
            Object value = jdbcMapResult.get(key);
            
            //确保value有效性，防止JSON reflect时异常
            if (value != null)
            {
                conCurrent.put(en.getKey(), jdbcMapResult.get(key));
            }
        }
        //fastjson reflect to modelbean
        return JSON.parseObject(JSON.toJSONString(conCurrent), clazz);
    }
    
}
