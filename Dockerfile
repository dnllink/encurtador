FROM java:8
VOLUME /tmp
ADD target/encurtador*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]
