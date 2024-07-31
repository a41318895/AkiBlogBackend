# 使用更小的基礎映像進行構建
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build

# 設置工作目錄
WORKDIR /build

# 複製父模組的 pom.xml 和所有子模組的 pom.xml
COPY pom.xml .
COPY aki-framework-common/pom.xml aki-framework-common/pom.xml
COPY aki-blog/pom.xml aki-blog/pom.xml
COPY aki-admin/pom.xml aki-admin/pom.xml

# 複製所有子模組的源代碼
COPY aki-framework-common/src aki-framework-common/src
COPY aki-blog/src aki-blog/src
COPY aki-admin/src aki-admin/src

# 安裝父 POM 和公共模組
RUN mvn clean install -N && \
    mvn clean install -pl aki-framework-common -DskipTests

# 構建 aki-blog
WORKDIR /build/aki-blog
RUN mvn clean package -DskipTests

# 構建 aki-admin
WORKDIR /build/aki-admin
RUN mvn clean package -DskipTests

# 使用更小的基礎映像進行運行
FROM eclipse-temurin:17-jre-alpine

# 創建目錄並複製 jar 文件
RUN mkdir /app
COPY --from=build /build/aki-blog/target/aki-blog.jar /app/
COPY --from=build /build/aki-admin/target/aki-admin.jar /app/

# 設置環境變量
ENV BLOG_JAR=aki-blog.jar \
    ADMIN_JAR=aki-admin.jar

# 暴露端口（假設 aki-blog 用 7777 端口，aki-admin 用 8989 端口）
EXPOSE 7777 8989

# 運行兩個 Spring Boot 應用程序
CMD ["sh", "-c", "java -jar /app/$BLOG_JAR & java -jar /app/$ADMIN_JAR"]

FROM nginx:1.18.0

# 复制初始化脚本到容器中
COPY init.sh /docker-entrypoint.d/

# 设置权限
RUN chmod +x /docker-entrypoint.d/init.sh