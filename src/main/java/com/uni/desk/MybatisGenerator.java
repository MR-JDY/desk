package com.uni.desk; /**
 * @Author:Joe
 * @Date:Created in 10:29 2020/4/16
 * @Description: mp生成器
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.uni.desk.base.BaseEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Joe
 * @Date:Created in 10:58 2020/4/7
 * @Description: mysql自动生成实体类、service等
 */
public class MybatisGenerator {


    private static final String author = "Joe";

        private static final String path = System.getProperty("user.dir");
//        private static final String path = "/Users/xiangqiaogao/Coding/desk";
//    private static final String path = "D:\\BitlandCloud\\bitland-middle-process\\flowable";
    private static final String tablePrefix = "ud";
    private static final String[] tableName = {"ud_report_campaign"};

    @Test
    public void testGenerator() {
        //1、全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)//开启AR模式
                .setAuthor(author)//设置作者
                //生成路径(一般都是生成在此项目的src/main/java下面)
                .setOutputDir(path + "\\src\\main\\java")//windows下
//                .setOutputDir(path + "/src/main/java")//mac下
                .setFileOverride(false)//第二次生成会把第一次生成的覆盖掉
                .setIdType(IdType.INPUT)//主键策略
                .setServiceName("%sService")//生成的service接口名字首字母是否为I，这样设置就没有I
                .setBaseResultMap(true)//生成resultMap
                .setBaseColumnList(true)//生成list
                .setActiveRecord(true)//启用AR模式
                .setBaseColumnList(true);//在xml中生成基础列

        //2、数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)//数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://120.79.158.41:3306/desk?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai")
                .setUsername("desk")
                .setPassword("desk");
        //3、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//开启全局大写命名
//                .setDbColumnUnderline(true)//表名字段名使用下划线
                .setColumnNaming(NamingStrategy.underline_to_camel)//
                .setNaming(NamingStrategy.underline_to_camel)//下划线到驼峰的命名方式
                .setTablePrefix(tablePrefix)//表名前缀
                .setEntityLombokModel(true)//使用lombok
                .setSuperEntityClass(BaseEntity.class)//父类
                .setSuperEntityColumns("id","created_by","created_date","last_modified_by","last_modified_date")//父类公共字段
                .setInclude(tableName);//逆向工程使用的表

        //4、包名策略配置
        PackageConfig packageConfig = new PackageConfig();

        //设置包名的parent,其他均可以默认文件位置，如果不需要controller和impl，可以手动删除
        // 注意，由于生成器本身限制，需要手动将生成的xml文件移动到resources文件目录下面
        packageConfig.setParent("com.uni.desk");

        //模板
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String returnPath = path + "\\src\\main\\resources\\mapper\\"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                return returnPath;
            }
        });
        cfg.setFileOutConfigList(focList);
        //5、整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setCfg(cfg)
                .setTemplate(new TemplateConfig()
                        .setXml(null)
                        .setController(null)
                )
                ;
        //6、执行
        autoGenerator.execute();

    }
}

