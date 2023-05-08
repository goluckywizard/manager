FROM amazoncorretto:19

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar manager.jar
# WORKDIR /app
# ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/manager-0.0.1-SNAPSHOT-plain.jar"]
# ENTRYPOINT ["java", "-jar","/app/manager-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java","-jar","manager.jar"]