# Alapkép a Java 17 futtatásához
FROM eclipse-temurin:17-jre-alpine

# Munkakönyvtár beállítása a konténeren belül
WORKDIR /app

# A lefordított .jar fájl bemásolása a target mappából a konténerbe
COPY target/mixify-0.0.1-SNAPSHOT.jar app.jar

# A 8080-as port megnyitása
EXPOSE 8080

# Az alkalmazás elindítása
ENTRYPOINT ["java", "-jar", "app.jar"]