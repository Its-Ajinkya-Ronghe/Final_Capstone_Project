pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat "mvn clean install"
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test"
            }
        }

        stage('Run JMeter') {
            steps {
                bat "mvn verify"
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    // The name 'Allure' here must match the Name field in Manage Jenkins -> Tools
                    allure includeProperties: false,
                           jdk: '',
                           results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}