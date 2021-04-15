package com.uni.desk.desk;


import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rec")
public class RecController {

    private static String BAIDU_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    @PostMapping("/getWords")
    public static List<JSONObject> getWords(@RequestBody List<String> list){
        long l = System.currentTimeMillis();
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
        long l1 = System.currentTimeMillis();
        System.out.println("总计耗时："+(l1-l));


        return jsonObjects;
    }
}
