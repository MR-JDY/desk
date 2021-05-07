package com.uni.desk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uni.desk.base.BitlandAssert;
import com.uni.desk.base.CommonBusinessException;
import com.uni.desk.base.CommonErrorCode;
import com.uni.desk.entity.JsonCreative;
import com.uni.desk.entity.Material;
import com.uni.desk.mapper.MaterialMapper;
import com.uni.desk.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uni.desk.ssh2.SshServer;
import com.uni.desk.util.ReflectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joe
 * @since 2021-05-07
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    public String DIR ;
    public String SUFFIX = ".json";
    public String PREFIX = "summary";
    @Resource
    private SshServer sshServer;
    @Resource
    private ReflectUtils reflectUtils;
    private Long batchNum;
    {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        batchNum = Long.parseLong(currentDate);
        DIR = "/opt/tb/data/"+LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    @Override
    public void importMaterial() {
        Set<String> fileAbsolutePaths = sshServer.getFileAbsolutePaths(DIR, PREFIX, SUFFIX);
        BitlandAssert.hasResult(fileAbsolutePaths,"对应路径下不存在指定条件的文件");
        for(String path:fileAbsolutePaths){
            InputStream inputStream = sshServer.readFile(path);

            String jsonStr = null;
            try {
                jsonStr = JSON.parseObject(inputStream, String.class, null).toString();
            } catch (IOException e) {
                e.printStackTrace();
                throw new CommonBusinessException(CommonErrorCode.INVALID_FORMAT.withArgs("数据转换异常"));
            }
            List<Material> jsonCreatives = parseStr2JsonCreative(jsonStr);
            saveOrUpdateBatch(jsonCreatives);
        }
    }

    public List<Material> parseStr2JsonCreative(String jsonStr){
        JSONArray objects = JSON.parseArray(jsonStr);
        ArrayList<Material> materialArrayList = new ArrayList<>(500);
        objects.forEach((obj)->{
            Material material = JSONObject.toJavaObject((JSON) obj, Material.class);

            JSONObject extra = material.getExtra();
            String material_id = extra.get("material_id").toString();
            String video_id = extra.get("video_id").toString();
            material.setVideoId(video_id);
            material.setMaterialId(material_id);
            material.setBatchNum(batchNum);
            materialArrayList.add(material);


        });

        return materialArrayList;
    }
}
