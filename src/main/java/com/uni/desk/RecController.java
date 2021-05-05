package com.uni.desk;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uni.desk.entity.DataCreative;
import com.uni.desk.entity.ReportCampaign;
import com.uni.desk.listener.ReportCampaignListener;
import com.uni.desk.service.DataCreativeService;
import com.uni.desk.util.JsonUtil;
import io.swagger.annotations.*;
import lombok.Data;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rec")
@Api("识别接口")
public class RecController {

    private static String BAIDU_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    @Resource
    private DataCreativeService dataCreativeService;


    @PostMapping("/getWords")
    @ApiOperation(value = "查字识别")
    @ApiImplicitParams({@ApiImplicitParam(name = "list",value = "URL集合",required = true,dataType = "List")})
    public static List<JSONObject> getWords(@RequestBody List<String> list){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("识别任务");
        String token = AuthService.getAuth();
        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        hashMap.put("Content-Type","application/x-www-form-urlencoded");
        LinkedList<JSONObject> jsonObjects = new LinkedList<>();
        for(String url:list){
            BAIDU_URL += "?access_token="+token;
            String s = VideoUtils.base64WithoutHead(url);
            HashMap<String, String> map = new HashMap<>(5);
            map.put("image",s);
            String s1 = HttpUtils.sendHttp(HttpUtils.HttpMethod.POST, BAIDU_URL, hashMap,map);
            JSONObject jsonObject = JSONObject.parseObject(s1);
//            JSONObject jsonObject = JSONObject.fromObject(s1);
            jsonObjects.add(jsonObject);
        }
        stopWatch.stop();
        String s = stopWatch.prettyPrint();
        System.out.println(s);


        return jsonObjects;
    }
    @GetMapping("/importCreative")
    @ApiOperation(value = "导入创意JSON")
    @Transactional(rollbackFor = SQLException.class)
    public String importCreative(){
        String jsonStr = JsonUtil.readJsonFile("/Users/xiangqiaogao/Coding/desk/src/main/resources/A.json");
        List<DataCreative> dataCreatives = dataCreativeService.parseStr2DataCreative(jsonStr);
//        dataCreativeService.save(dataCreatives.get(0));
        dataCreativeService.saveBatch(dataCreatives);
        return "成功";
    }


//    @GetMapping("/importExcel")
    @Test
    public void indexOrNameRead() {
        String fileName = "/Users/xiangqiaogao/Coding/desk/src/main/resources/report#理然#理然-发型喷雾01#30天.xls";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, ReportCampaign.class, new ReportCampaignListener()).sheet().doRead();
    }
//    getResourceAsStream("com/test/demo/test.properties")
    @Test
    public  void readJsonFile(){
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//        String jsonStr = JsonUtil.readJsonFile("E:/coding/projects_idea/desk/src/main/resources/A.json");
        String jsonStr = JsonUtil.readJsonFile("/Users/xiangqiaogao/Coding/desk/src/main/resources/A.json");
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
        System.out.println(objects.get(0));
    }
    public void setDataValue(DataCreative dataCreative, List<Map> list) throws Exception {
        Class<? extends DataCreative> creativeClass = dataCreative.getClass();
        Field[] declaredFields = creativeClass.getDeclaredFields();
        HashMap<String, Object> map = new HashMap<>();
        list.forEach((obj) -> {
            map.put(obj.get("key").toString(), obj.get("value"));
        });


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
            System.out.println("实例："+str);
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
    // 实体类同样可以使用注解
    @Data
    @ApiModel(description = "foo实体类")
    class Foo{
        @ApiModelProperty(value = "姓名")
        private String name;
        @ApiModelProperty(value = "年龄")
        private String age;
    }
}
