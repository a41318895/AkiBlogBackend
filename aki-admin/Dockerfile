# 使用更小的基礎映像進行構建
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build

# 設置工作目錄
WORKDIR /build

# 複製父模組的 pom.xml 和所有子模塊的 pom.xml
COPY pom.xml .
COPY aki-framework-common/pom.xml aki-framework-common/pom.xml
COPY aki-admin/pom.xml aki-admin/pom.xml
COPY aki-blog/pom.xml aki-blog/pom.xml

# 複製所有子模塊的源代碼
COPY aki-framework-common/src aki-framework-common/src
COPY aki-admin/src aki-admin/src
COPY aki-blog/src aki-blog/src

# 安裝父 POM 和公共模組
RUN mvn clean install -N && \
    mvn clean install -pl aki-framework-common -DskipTests

# 構建 aki-admin
WORKDIR /build/aki-admin
RUN mvn clean package -DskipTests

# 使用更小的基礎映像進行運行
FROM eclipse-temurin:17-jre-alpine

# 創建目錄並複製 jar 文件
RUN mkdir /app
COPY --from=build /build/aki-admin/target/aki-admin.jar /app/

# 暴露端口
EXPOSE 8989

# 運行 Spring Boot 應用程序
CMD ["sh", "-c", "java -jar /app/aki-admin.jar"]