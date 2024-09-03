pipeline {
    environment {
        DOCKERHUB_CREDENTIALS = 'dockerhub_credentials'
        NAME = "toniin/api-contact-manager"
        VERSION = "${env.BUILD_ID}.0.0-mongodb"
        GIT_REPO = 'https://github.com/Toniin/API_Contact_manager.git'
    }

    tools {
        maven 'Maven 3.9.9'
    }

    agent any

    stages {
        stage('Cloning our Git') {
            steps {
                git branch: 'main',
                    url: GIT_REPO
            }
        }

        stage('Create java archive') {
            steps{
                script {
                    sh "rm src/main/java/com/api_contact_manager/configuration/AppConfig.java"
                }
                withMaven {
                    sh "mvn clean package"
                }
            }
        }

        stage('Building our image') {
            steps{
                sh "docker build -t ${NAME} ."
            }
        }

        stage('Deploy our image') {
            steps{
                script {
                    docker.withRegistry('', DOCKERHUB_CREDENTIALS) {
                        sh "docker tag ${NAME} ${NAME}:${VERSION}"

                        sh 'docker push ${NAME}:latest'
                        sh 'docker push ${NAME}:${VERSION}'
                    }
                }
            }
        }

        stage('Cleaning up') {
            steps{
                sh "docker rmi ${NAME}:latest"
                sh "docker rmi ${NAME}:${VERSION}"
            }
        }
    }
}