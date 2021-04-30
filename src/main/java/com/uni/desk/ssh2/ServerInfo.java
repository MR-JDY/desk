package com.uni.desk.ssh2;

import lombok.Data;

@Data
public class ServerInfo {
    private String ip;
    private int port = 22;
    private String username;
    private String password;
}
