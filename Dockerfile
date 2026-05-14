# ── Etapa 1: Build ────────────────────────────────────────────────
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copia só o pom primeiro para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o restante e compila
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Etapa 2: Runtime ──────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render injeta a variável PORT automaticamente
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
