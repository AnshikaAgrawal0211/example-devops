FROM openjdk:8-jre-alpine
COPY target/scala-2.12/Devops-assembly-0.1.jar /
CMD ["java", "-jar", "Devops-assembly-0.1.jar"]
