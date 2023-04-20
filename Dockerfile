FROM openjdk:17-jdk-alpine
COPY /target/*.jar minikube-confluent-kafka-spring-*.jar
ENTRYPOINT ["java","-jar","/minikube-confluent-kafka-spring-*.jar"]