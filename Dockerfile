FROM java:8
EXPOSE 8080
ADD /target/sample-agent-1.0-SNAPSHOT.jar sample-agent-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","sample-agent-1.0-SNAPSHOT.jar"]