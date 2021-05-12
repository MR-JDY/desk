package com.uni.desk.config;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.taobao.text.Color;
import com.uni.desk.util.CustomBanner;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BannerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            LogoBanner logoBanner = new LogoBanner(BannerInitializer.class, "/templates/logo.txt", "Welcome！", 4, 11, new Color[4], true);
            CustomBanner.show(logoBanner, new Description(BannerConstant.VERSION + ":", "第一版本", 0, 1)
                    , new Description("Company:", "极创美奥", 0, 1)
                    , new Description("Members:", "一群掉头发的猿猴", 0, 1)
            );
        }
    }
}
