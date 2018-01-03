# 概述
rabbitmq-intercepter是通过javasist字节码注入的方式实现的RMQ拦截器，支持producer和consumer的拦截，同时开放SPI扩展.
# 环境
 - Gradle
 - Java 8
# Compile
```
gradle clean build -x test
```
## 使用
```
dependencies {
    compile 'com.infi:rabbitmq-intercepter:0.0.1-SNAPSHOT'
}
```
* 程序入口初始化
```
@SpringBootApplication
public class TestApplication {

  static {
  	// 需要加在RMQ class类之前初始化,建议在程序入口
    RmqIntercepterInitializer.getInstance().init();
  }

  public static void main(String[] args) throws Exception {
    SpringApplication application = new SpringApplication(TestApplication.class);
    application.run(args);
  }
}
```
