package com.uni.desk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uni.desk.base.CommonBusinessException;
import com.uni.desk.base.CommonErrorCode;
import com.uni.desk.entity.DataSummary;
import com.uni.desk.mapper.DataSummaryMapper;
import com.uni.desk.service.DataSummaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uni.desk.ssh2.SshServer;
import com.uni.desk.util.ReflectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * unidesk各类数据汇总 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2021-05-06
 */
@Service
public class DataSummaryServiceImpl extends ServiceImpl<DataSummaryMapper, DataSummary> implements DataSummaryService {

    public String DIR = "/opt/tb/data";
    public String SUFFIX = ".json";
    public String PREFIX = "summary";
    @Resource
    private SshServer sshServer;
    @Resource
    private ReflectUtils reflectUtils;

    private Long batchNum;
    {
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        batchNum = Long.parseLong(currentDate);
    }
    @Override
    public void importSummary() {
        //遍历对应目录下所有以.json结尾的文件
        Set<String> fileAbsolutePaths = null;
        try {
            fileAbsolutePaths = sshServer.getFileAbsolutePaths(DIR, PREFIX,SUFFIX);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonBusinessException(CommonErrorCode.EXCEL_ERROR.withArgs("创意汇总JSON导入失败"));
        }
        List<DataSummary> dataSummaryList = new LinkedList<>();
        for(String path:fileAbsolutePaths){
            InputStream inputStream = sshServer.readFile(path);
            Object o = null;
            try {
                o = JSON.parseObject(inputStream, String.class, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Map> data = (List<Map>) JSONObject.parseObject(o.toString(),List.class,null);
            DataSummary summary = new DataSummary();
            try {
                summary= (DataSummary) reflectUtils.convertMap2Model(summary, data);
                summary.setBatchNum(batchNum);
                dataSummaryList.add(summary);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        saveBatch(dataSummaryList);

    }
}
