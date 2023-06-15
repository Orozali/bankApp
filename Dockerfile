FROM openjdk:19 as build
WORKDIR /app
COPY . ./
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:19
WORKDIR /app
COPY --from=build /app/target/bankApp-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "bankApp-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080