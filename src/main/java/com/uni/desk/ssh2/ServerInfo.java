package com.uni.desk.ssh2;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server-info")
public class ServerInfo {
//    @Value(value = "${ip:localhost}")
    private String ip;
//    @Value(value="${port:22}")
    private int port = 22;
//    @Value(value = "${username:root}")
    private String username;
//    @Value(value = "${password}")
    private String password;
}
