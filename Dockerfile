FROM openjdk:17

WORKDIR /app

COPY build/libs/blog-0.0.1-SNAPSHOT.jar blog.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "blog.jar"]