package com.uni.desk.service.impl;

import com.alibaba.excel.EasyExcel;
import com.uni.desk.base.CommonBusinessException;
import com.uni.desk.base.CommonErrorCode;
import com.uni.desk.entity.ReportCampaign;
import com.uni.desk.listener.ReportCampaignListener;
import com.uni.desk.mapper.ReportCampaignMapper;
import com.uni.desk.service.ReportCampaignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uni.desk.ssh2.SshHandler;
import com.uni.desk.ssh2.SshServer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joe
 * @since 2021-05-04
 */
@Service
public class ReportCampaignServiceImpl extends ServiceImpl<ReportCampaignMapper, ReportCampaign> implements ReportCampaignService {

    public String DIR = "/opt/tb/data/2021-04-28";
    public String SUFFIX = ".xls";
    public String PREFIX = "report";
    @Resource
    private SshServer sshServer;

    @Override
//    @Transactional(rollbackFor = IOException.class)//数据量太大了  不建议作为事务
    public String importCampaignXls() {
        //遍历对应目录下所有以.xls结尾的文件
        Set<String> fileAbsolutePaths = null;
        try {
            fileAbsolutePaths = sshServer.getFileAbsolutePaths(DIR, PREFIX, SUFFIX);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonBusinessException(CommonErrorCode.EXCEL_ERROR.withArgs("Excel导入失败"));
        }

        for(String path:fileAbsolutePaths){
            //读取每个文件的文件流
            InputStream inputStream = sshServer.readFile(path);
            String fileName = SshHandler.extractFileName(path);
            String[] split = fileName.split("#");
            String dataType = split[split.length-1];
            HashMap<String, Object> map = new HashMap<>(12);
            map.put("dataType",dataType);
            EasyExcel.read(inputStream, ReportCampaign.class, new ReportCampaignListener(this,map)).sheet().headRowNumber(1).doRead();
            //导入文件信息到数据库
        }
        return null;
    }
}
