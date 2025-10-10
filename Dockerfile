FROM gradle:8.13-jdk21 AS build
ARG SERVICE_NAME

COPY settings.gradle.kts .

# 2. Теперь копируем build-файлы и исходники
COPY build.gradle.kts .
COPY gradle.properties .
COPY ${SERVICE_NAME} ./${SERVICE_NAME}

# 3. Выполняем сборку
RUN gradle :${SERVICE_NAME}:build --no-daemon

ENTRYPOINT ["top", "-b"]