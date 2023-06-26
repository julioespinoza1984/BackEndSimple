# Usar una imagen base con JDK 11 y Maven
FROM maven:3.8.4-openjdk-11 AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecutar Maven para construir el proyecto
RUN mvn clean package

# Crear una nueva imagen basada en OpenJDK 11
FROM openjdk:11-jre-slim-buster

# Exponer el puerto que utilizará la aplicación
EXPOSE 8026

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/target/BackEndSimple-1.0-SNAPSHOT.jar /app/BackEndSimple.jar

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/BackEndSimple.jar"]
