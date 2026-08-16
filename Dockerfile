# Multi-stage build: compila com Maven + JDK 21, executa com JRE 21
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para cache de dependências
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copia o código fonte e compila
COPY src src
RUN ./mvnw clean package -DskipTests -B

# Stage final: imagem enxuta com JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR compilado
COPY --from=build /app/target/Estacionamento-0.0.1-SNAPSHOT.jar app.jar

# Variáveis de ambiente podem ser sobrescritas no Render
ENV SPRING_PROFILES_ACTIVE=prod

# Expõe a porta 8080 (padrão Spring Boot)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
