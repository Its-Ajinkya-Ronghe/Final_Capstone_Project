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
                    // This 'Allure' inside the tool() function MUST match
                    // the Name you typed in Jenkins Global Tool Configuration
                    def allureHome = tool name: 'Allure', type: 'io.qameta.allure.jenkins.tools.AllureCommandlineInstallation'

                    allure includeProperties: false,
                           jdk: '',
                           results: [[path: 'target/allure-results']]
                }
            }
        }
    }
}