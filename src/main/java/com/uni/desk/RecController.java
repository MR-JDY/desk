package com.uni.desk;


import io.swagger.annotations.*;
import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rec")
@Api("识别接口")
public class RecController {

    private static String BAIDU_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

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
            JSONObject jsonObject = JSONObject.fromObject(s1);
            jsonObjects.add(jsonObject);
        }
        stopWatch.stop();
        String s = stopWatch.prettyPrint();
        System.out.println(s);


        return jsonObjects;
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
