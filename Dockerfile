# Estágio 1: Construir a aplicação Java com Maven
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app

# Copie apenas o arquivo pom.xml para evitar reinstalar dependências quando o código não mudou
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie todo o código-fonte e compile o aplicativo
COPY src src
RUN mvn package

# Estágio 2: Executar a aplicação Spring Boot
FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app

# Copie o artefato JAR gerado do estágio de compilação
COPY --from=build /app/target/nome-do-seu-arquivo-jar.jar app.jar

# Expõe a porta na qual a aplicação Spring Boot será executada
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "app.jar"]
