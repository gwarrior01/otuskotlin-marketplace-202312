FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY target/output/clickhouse-api-be.jar /app/clickhouse-api-be.jar

EXPOSE 8080

CMD ["java", "-jar", "clickhouse-api-be.jar"]