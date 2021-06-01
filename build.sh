cd common
mvn clean install

cd ../api
mvn spring-boot:build-image

cd ../employee-service
mvn spring-boot:build-image





