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
                allure([
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}