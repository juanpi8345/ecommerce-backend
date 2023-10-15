FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/cart-0.0.1.jar
COPY ${JAR_FILE} app_cart.jar
ENTRYPOINT ["java","-jar","app_cart.jar"]