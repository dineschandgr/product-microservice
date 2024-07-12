FROM openjdk:21
WORKDIR /app
COPY ./target/product-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8763
CMD ["java", "-jar", "product-service-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","product-service-0.0.1-SNAPSHOT.jar"]