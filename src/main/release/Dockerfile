FROM harbor.qqxki.com/base/ubuntu-java-base:2.0.0
MAINTAINER Oven <503612012@qq.com>
LABEL Description="fms powered by oven" Version="@version@"

# 应用文件
RUN mkdir -p /home/fms/logs/
ADD lib /home/fms/lib
ADD resources /home/fms/resources
ADD fms-@jar.version@.jar /home/fms
ADD start.sh /home/fms
RUN chmod +x /home/fms/start.sh
ADD stop.sh /home/fms
RUN chmod +x /home/fms/stop.sh

# 个性化配置
RUN echo "alias log='tail -f /home/fms/logs/fms.log'" >> ~/.bashrc

WORKDIR /home/fms/
ENTRYPOINT ["./start.sh"]