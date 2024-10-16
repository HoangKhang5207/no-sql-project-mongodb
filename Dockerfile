# Sử dụng hình ảnh JDK 17
FROM openjdk:17-jdk-alpine

# Set biến môi trường
ENV MONGO_HOST=mongodb
ENV MONGO_PORT=27017

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép tệp jar từ máy local vào container
COPY target/laptopshop-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose cổng mà ứng dụng Spring Boot lắng nghe (mặc định là 8080)
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
