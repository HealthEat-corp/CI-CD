#! /usr/bin/groovy

def GIT_BRANCH

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
        }
        stage("create docker image and push") {

        }
    }
}
