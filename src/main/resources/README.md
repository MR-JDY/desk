打包方式
测试服环境：
mvn clean package -Dprofile.active=dev -Dmaven.test.skip=true 
正式服环境命令：
mvn clean package -Dprofile.active=prod -Dmaven.test.skip=true

控制台输出的时候可以看到自己连接的环境信息