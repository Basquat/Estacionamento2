# Multi-stage build usando imagem oficial do Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B -f pom.xml

# Copia o código fonte e compila
COPY src src
RUN mvn clean package -DskipTests -B

# Stage final: imagem enxuta com JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR compilado
COPY --from=build /app/target/Estacionamento-0.0.1-SNAPSHOT.jar app.jar

# Variáveis de ambiente podem ser sobrescritas no Render
ENV SPRING_PROFILES_ACTIVE=prod

# Flags de JVM para instância pequena (Render free ~0.1 vCPU):
#  SerialGC          -> sem threads de GC concorrente disputando a CPU
#  TieredStopAtLevel=1 -> só JIT C1, boot bem mais rápido (app é I/O-bound)
#  MaxRAMPercentage   -> respeita o limite de memória do container
ENV JAVA_OPTS="-XX:+UseSerialGC -XX:TieredStopAtLevel=1 -Xss512k -XX:MaxRAMPercentage=70.0 -Dfile.encoding=UTF-8"

# Expõe a porta 8080 (padrão Spring Boot)
EXPOSE 8080

# Comando de inicialização (sh -c para expandir $JAVA_OPTS; Render pode sobrescrever)
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
