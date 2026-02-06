pipeline {
    agent any

    environment {
        APP_NAME = "library-management-system"
        DOCKER_IMAGE = "souptiktat/library-management-system"
        DOCKER_TAG = "1.0"
    }

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh """
                  docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                  docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                """
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-password', variable: 'DOCKER_PASSWORD')]) {
                    sh """
                      echo $DOCKER_PASSWORD | docker login -u your-dockerhub-username --password-stdin
                      docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                      docker push ${DOCKER_IMAGE}:latest
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                  kubectl apply -f deployment/k8s/configmap.yml
                  kubectl apply -f deployment/k8s/deployment.yml
                  kubectl apply -f deployment/k8s/service.yml
                """
            }
        }

        stage('Deploy Ingress') {
            steps {
                sh 'kubectl apply -f deployment/k8s/ingress.yml'
            }
        }
    }

    post {
        success {
            echo '✅ Build and deployment pipeline completed successfully'
        }
        failure {
            echo '❌ Pipeline failed'
        }
    }
}