## 一、部署建议

> 建议将所有文件放到`/home/fms`目录下，否则需要修改相关文件中的配置。

## 二、文件清单

| 文件              | 描述       |
|-----------------|----------|
| __`lib`__       | 第三方依赖包   |
| __`resources`__ | 资源目录     |
| backup          | 数据库备份脚本  |
| build.sh        | 镜像构建脚本   |
| container.sh    | 容器启动脚本   |
| Dockerfile      | 镜像构建文件   |
| fms.sql         | 数据库初始化脚本 |
| fms-1.0.0.jar   | 应用代码     |
| mysql.sh        | 数据库安装脚本  |
| path.sh         | 项目部署根路径  |
| pull.sh         | 拉取镜像脚本   |
| push.sh         | 推送镜像脚本   |
| start.sh        | 应用启动脚本   |
| stop.sh         | 应用停止脚本   |

## 三、配置修改

#### 1) 修改配置文件

> 修改`application-pro.properties`文件中的数据源配置

#### 2) 修改配置文件

> 修改`fms.sql`文件中的redis配置

## 四、数据库部署

```shell
./mysql.sh
```

## 五、应用镜像构建

```shell
./build.sh
```

## 六、启动应用容器

```shell
./container.sh
```

## 七、开发环境搭建

> 1. 修改 [src/main/resources/application-dev.properties](./src/main/resources/application-dev.properties) 中数据源信息，或启动时指定环境变量：
     `db_url=192.168.18.66:3306;db_uname=root;db_pwd=root`
> 2. 修改 [src/main/java/com/oven/fms/framework/config/DevEnvSet.java](./src/main/java/com/oven/fms/framework/config/DevEnvSet.java) 中相关配置项
> 3. 修改 [pom.xml](./pom.xml) 中指定profile

## 八、ARM架构下，需要替换以下文件

> 1. start.sh
> 2. backup
> 3. Dockerfile中基础镜像换成harbor.qqxki.com/base/ubuntu-java-base-aarch64:1.0.0