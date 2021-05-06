package com.uni.desk.util;

import com.uni.desk.base.BaseEntity;
import com.uni.desk.entity.DataSummary;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ReflectUtils<T> {

    /**
     * 将所有map中包含实体类键值对的值都赋值到实体类中
     * @param instance
     * @param list
     * @return
     * @throws Exception
     */
    public <T> T convertMap2Model(T instance, List<Map> list) throws Exception {
        Class<?> instanceClass = instance.getClass();
        Field[] declaredFields = instanceClass.getDeclaredFields();
        HashMap<String, Object> map = new HashMap<>();
        list.forEach((obj) -> {
            map.put(obj.get("key").toString(), obj.get("value"));
        });
        if(map.containsKey("convert")){
            map.put("convertVolume",map.get("convert"));
        }

        for (Field field : declaredFields) {
            String name = field.getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            //获取字段的类型
            Class<?> type = instanceClass.getDeclaredField(name).getType();

            // 首字母大写
            String replace = name.substring(0, 1).toUpperCase()
                    + name.substring(1);
            //获得setter方法
            Method setMethod = instanceClass.getMethod("set" + replace, type);
            //获取到form表单的所有值
            String str = Optional.ofNullable(map.get(name)).orElse("").toString();

            if (str == null || "".equals(str)) {
                // 首字母小写
                String small = name.substring(0, 1).toLowerCase()
                        + name.substring(1);
                str = Optional.ofNullable(map.get(small)).orElse("").toString();;
            }
            str = str.replaceAll("%", "");
            if("-".equals(str)){

                str = str.replaceAll("-", "0");
            }
            str = str.replaceAll(",", "");
            //通过setter方法赋值给对应的成员变量
            if (str != null && !"".equals(str)) {
                // ---判断读取数据的类型
                if (type.isAssignableFrom(String.class)) {
                    setMethod.invoke(instance, str);
                } else if (type.isAssignableFrom(int.class)
                        || type.isAssignableFrom(Integer.class)) {
                    setMethod.invoke(instance, Integer.parseInt(str));
                } else if (type.isAssignableFrom(Double.class)
                        || type.isAssignableFrom(double.class)) {
                    setMethod.invoke(instance, Double.parseDouble(str));
                } else if (type.isAssignableFrom(Boolean.class)
                        || type.isAssignableFrom(boolean.class)) {
                    setMethod.invoke(instance, Boolean.parseBoolean(str));
                } else if (type.isAssignableFrom(Date.class)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    setMethod.invoke(instance, dateFormat.parse(str));
                } else if (type.isAssignableFrom(Timestamp.class)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    setMethod.invoke(instance, new Timestamp(dateFormat.parse(str).getTime()));
                }else if(type.isAssignableFrom(BigDecimal.class)){
                    setMethod.invoke(instance,BigDecimal.valueOf(Double.parseDouble(str)));
                }else if(type.isAssignableFrom(Long.class)){
                    setMethod.invoke(instance,Long.valueOf(str));
                }


            }
        }
        return (T)instance;
    }
}
