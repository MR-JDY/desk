package com.uni.desk.desk;

import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rec")
public class RecController {


    @PostMapping("/getWords")
    public static String getWords(@RequestBody List<String> list){
        String token = AuthService.getAuth();
        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        hashMap.put("Content-Type","application/x-www-form-urlencoded");
        StringBuffer buffer = new StringBuffer();
        for(String url:list){
            url += "?access_token="+token;
            String s = VideoUtils.base64WithoutHead(url);
            HashMap<String, String> map = new HashMap<>(5);
            map.put("image",s);
            String s1 = HttpUtils.sendHttp(HttpUtils.HttpMethod.POST, url, hashMap,"image=" + s);
            buffer.append(s1);
        }
        return buffer.toString();
    }
}
