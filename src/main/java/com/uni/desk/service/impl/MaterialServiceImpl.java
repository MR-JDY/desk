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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
@Slf4j
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    public String DIR ;
    public String SUFFIX = ".json";
    public String PREFIX = "asset";
    @Resource
    private SshServer sshServer;
    @Resource
    private ReflectUtils reflectUtils;
    private Long batchNum;

    @Override
    public void importMaterial() {
        //因为Spring管理的对象是单例的,所以放在构造代码块内是不能每次调用都更新的
        {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            batchNum = Long.parseLong(currentDate);
            DIR = "/opt/tb/data/"+LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        Set<String> fileAbsolutePaths = sshServer.getFileAbsolutePaths(DIR, PREFIX, SUFFIX);
        BitlandAssert.hasResult(fileAbsolutePaths,"对应路径下不存在指定条件的文件");
        for(String path:fileAbsolutePaths){
            InputStream inputStream = sshServer.readFile(path);
            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
                log.error(result.toString());
            } catch (Exception e) {
                try {
                    inputStream.close();
                    bufferedReader.close();
                } catch (Exception e1) {
                }
            }*/


            String jsonStr = null;
            try {
                jsonStr = JSON.parseObject(inputStream, String.class, null).toString();
            } catch (Exception e) {
                e.printStackTrace();
                log.debug("获取的文件{}内容为空",path);
            }
            if("[]".equals(jsonStr)){
                continue;
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

            JSONObject jsonObject = JSONObject.parseObject(material.getExtra());
            String material_id = jsonObject.get("materialId").toString();
            String video_id = jsonObject.get("videoId").toString();
            material.setVideoId(video_id);
            material.setMaterialId(material_id);
            material.setBatchNum(batchNum);

            splitVideoName(material);
            materialArrayList.add(material);


        });

        return materialArrayList;
    }

    /**
     * 切分视频名称
     * 视频名称规则: 所属_品牌_商品_编导_剪辑
     * @param material
     * @return
     */
    private boolean splitVideoName(Material material){
        String[] split = material.getName().split("_");
        if(split.length > 4){
            material.setBelongs(split[0].toString());
            material.setBrand(split[1].toString());
            material.setItem(split[2].toString());
            material.setChoreographer(split[3].toString());
            material.setEditor(split[4].toString());
            return true;
        }
        return false;
    }
}
