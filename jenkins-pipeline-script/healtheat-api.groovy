#! /usr/bin/groovy
def GIT_BRANCH
/*
1. Spring project Git Clone
2. 특정 branch 로 change
3.  Spring project 빋드
4. Dockerfile Git Clone
5. 빌드된 jar 파일로 jdk docker image 생성
6. 생성된 image Docker hub Repository 에 push

TODO : 배포 서버에서 image pull 후 docker run 필요
 */
node {
    dir ("sample/api") {
        stage("Git Clone and Pull - spring") {
            GIT_URL = "https://github.com/HealthEat-corp/api.git"
            git url: GIT_URL // git init, clone

            GIT_BRANCH = 'dev'

            sh(script:"git checkout $GIT_BRANCH")
        }
        stage("project build") {
            sh(script:"sh gradlew bootJar") // Spring build
            sh(script:"cp build/libs/*.jar ../")
        }
    }
    dir("sample/docker") {
        stage("Git Clone and pull - docker") {
            GIT_URL = "https://github.com/HealthEat-corp/CI-CD.git"
            git url: GIT_URL // git init, clone

            GIT_BRANCH = 'master'

            sh(script:"git checkout $GIT_BRANCH")
            sh(script:"cp Dockerfile ../")
        }

    }
    dir("sample") {
        stage("create docker image and push") {
            sh(script:"docker build --build-arg JAR_FILE=./*.jar -t cscd053/healtheat-api:0.1 .")
            sh(script:"docker push cscd053/healtheat-api:0.1")
        }
    }
}

