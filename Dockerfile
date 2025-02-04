# 1️⃣ Usa una imagen base de Java
FROM openjdk:17

# 2️⃣ Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# 3️⃣ Copia el archivo JAR generado por Maven o Gradle al contenedor
COPY target/alertasmain-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Copia la carpeta del wallet al contenedor
COPY Wallet_J4LC1KTDGSFPGVRU /app/wallet

# 5️⃣ Configura la variable de entorno TNS_ADMIN para apuntar al wallet
ENV TNS_ADMIN=/app/wallet

# 6️⃣ Expone el puerto en el que correrá la aplicación (ajusta según necesidad)
EXPOSE 8082

# 7️⃣Define el comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
