FROM pnavato/amazoncorretto-jre:17-alpine
COPY ["target/*.jar", "app.jar"]
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
