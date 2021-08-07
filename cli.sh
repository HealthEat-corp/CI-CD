# docker image 생성
docker build --build-arg JAR_FILE=./api-0.0.1-SNAPSHOT.jar -t healtheat ./

# docker run
docker run -p 8080:8080 healtheat
