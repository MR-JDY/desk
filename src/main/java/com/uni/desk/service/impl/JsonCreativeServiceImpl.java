package com.uni.desk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uni.desk.base.BitlandAssert;
import com.uni.desk.base.CommonBusinessException;
import com.uni.desk.base.CommonErrorCode;
import com.uni.desk.entity.DataCreative;
import com.uni.desk.entity.DataSummary;
import com.uni.desk.entity.JsonCreative;
import com.uni.desk.mapper.JsonCreativeMapper;
import com.uni.desk.service.JsonCreativeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uni.desk.ssh2.SshServer;
import com.uni.desk.util.ReflectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joe
 * @since 2021-05-07
 */
@Service
public class JsonCreativeServiceImpl extends ServiceImpl<JsonCreativeMapper, JsonCreative> implements JsonCreativeService {

    public String DIR ;
    public String SUFFIX = ".json";
    public String PREFIX = "creative";
    @Resource
    private SshServer sshServer;


    private Long batchNum;



    @Override
    public void importJsonCreative() {

        {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            DIR = "/opt/tb/data/"+LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            batchNum = Long.parseLong(currentDate);
        }{
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            DIR = "/opt/tb/data/"+LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            batchNum = Long.parseLong(currentDate);
        }
        Set<String> fileAbsolutePaths = sshServer.getFileAbsolutePaths(DIR, PREFIX, SUFFIX);
        BitlandAssert.hasResult(fileAbsolutePaths,"对应路径下不存在指定条件的文件");
        List<DataSummary> dataSummaryList = new LinkedList<>();
        for(String path:fileAbsolutePaths){
            InputStream inputStream = sshServer.readFile(path);

            String jsonStr = null;
            try {
                jsonStr = JSON.parseObject(inputStream, String.class, null).toString();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CommonBusinessException(CommonErrorCode.INVALID_FORMAT.withArgs("数据转换异常"));
            }
            List<JsonCreative> jsonCreatives = parseStr2JsonCreative(jsonStr);
            saveOrUpdateBatch(jsonCreatives);
        }
    }


    /**
     * 根据解析出来的JSON数据转换成实体类
     * @param jsonStr
     * @return
     */
    public List<JsonCreative> parseStr2JsonCreative(String jsonStr){
        JSONArray objects = JSON.parseArray(jsonStr);
        ArrayList<JsonCreative> creativeArrayList = new ArrayList<>(500);
        objects.forEach((obj)->{
            JsonCreative jsonCreative = JSONObject.toJavaObject((JSON) obj, JsonCreative.class);
            jsonCreative.setBatchNum(batchNum);
            creativeArrayList.add(jsonCreative);


        });

        return creativeArrayList;
    }
}
