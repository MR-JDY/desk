package com.uni.desk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.metaparadigm.jsonrpc.JSONSerializer;
import com.metaparadigm.jsonrpc.MarshallException;
import com.uni.desk.entity.DataCreative;
import com.uni.desk.mapper.DataCreativeMapper;
import com.uni.desk.service.DataCreativeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 各条创意的日报表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2021-04-30
 */
@Service
public class DataCreativeServiceImpl extends ServiceImpl<DataCreativeMapper, DataCreative> implements DataCreativeService {

    /**
     * 根据解析出来的JSON数据转换成实体类
     * @param jsonStr
     * @return
     */
    public List<DataCreative> parseStr2DataCreative(String jsonStr){
        JSONArray objects = JSON.parseArray(jsonStr);
        ArrayList<DataCreative> creativeArrayList = new ArrayList<>(500);
        objects.forEach((obj)->{
            DataCreative dataCreative = JSONObject.toJavaObject((JSON) obj, DataCreative.class);
            JSONObject jsonObject = JSON.parseObject(obj.toString());
            //获取最外层每个对象的data的list
            List<Map> data = (List<Map>)JSONObject.parseObject(jsonObject.get("data").toString(), List.class);
            try {
                setDataValue(dataCreative,data);
                creativeArrayList.add(dataCreative);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        return creativeArrayList;
    }

    /**
     * 将远程获取的文件流转成String类型的Json字符串
     * @param inputStream
     * @return
     */
    public String parseInputStream2Str(InputStream inputStream){
        JSONSerializer jsonSerializer = new JSONSerializer();
        String jsonStr = "";
        try {
            jsonStr = jsonSerializer.toJSON(inputStream);
        } catch (MarshallException e) {
            e.printStackTrace();
            log.error("InputStream 转换 Json 失败");
        }
        return jsonStr;
    }
    public void setDataValue(DataCreative dataCreative, List<Map> list) throws Exception {
        Class<? extends DataCreative> creativeClass = dataCreative.getClass();
        Field[] declaredFields = creativeClass.getDeclaredFields();
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
            Class<?> type = creativeClass.getDeclaredField(name).getType();

            // 首字母大写
            String replace = name.substring(0, 1).toUpperCase()
                    + name.substring(1);
            //获得setter方法
            Method setMethod = creativeClass.getMethod("set" + replace, type);
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
                    setMethod.invoke(dataCreative, str);
                } else if (type.isAssignableFrom(int.class)
                        || type.isAssignableFrom(Integer.class)) {
                    setMethod.invoke(dataCreative, Integer.parseInt(str));
                } else if (type.isAssignableFrom(Double.class)
                        || type.isAssignableFrom(double.class)) {
                    setMethod.invoke(dataCreative, Double.parseDouble(str));
                } else if (type.isAssignableFrom(Boolean.class)
                        || type.isAssignableFrom(boolean.class)) {
                    setMethod.invoke(dataCreative, Boolean.parseBoolean(str));
                } else if (type.isAssignableFrom(Date.class)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    setMethod.invoke(dataCreative, dateFormat.parse(str));
                } else if (type.isAssignableFrom(Timestamp.class)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    setMethod.invoke(dataCreative, new Timestamp(dateFormat.parse(str).getTime()));
                }else if(type.isAssignableFrom(BigDecimal.class)){
                    setMethod.invoke(dataCreative,BigDecimal.valueOf(Double.parseDouble(str)));
                }else if(type.isAssignableFrom(Long.class)){
                    setMethod.invoke(dataCreative,Long.valueOf(str));
                }


            }
        }
    }

}
