# ===================
# Этап сборки
# ===================
FROM gradle:8.5-jdk21 AS build

ENV APP_HOME=/app
ENV JAR_NAME=vm-template-manager.jar

WORKDIR $APP_HOME

COPY build.gradle settings.gradle nexus-repositories.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon || true

COPY . .

RUN gradle clean build -x test --no-daemon

# ===================
# Этап исполнения
# ===================
FROM eclipse-temurin:21-jre

ENV APP_HOME=/app
ENV JAR_NAME=vm-template-manager.jar
ENV DEBUG_PORT=1044

WORKDIR $APP_HOME

COPY --from=build $APP_HOME/build/libs/*.jar $JAR_NAME

EXPOSE $DEBUG_PORT

ENTRYPOINT ["sh", "-c", "java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:$DEBUG_PORT -jar $JAR_NAME"]
