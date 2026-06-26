# 1. Usamos una imagen ligera de Java 17 para correr la app
FROM eclipse-temurin:17-jdk-alpine

# 2. Carpeta de trabajo interna dentro del contenedor
WORKDIR /app

# 3. Copiamos el .jar que acabamos de generar en la carpeta target hacia adentro
COPY target/*.jar app.jar

# 4. Comando para ejecutar el jar al encender el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]