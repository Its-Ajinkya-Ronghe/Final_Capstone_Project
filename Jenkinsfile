pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Its-Ajinkya-Ronghe/Final_Capstone_Project.git'
            }
        }

        stage('Build') {
            steps {
                bat "mvn clean install -DskipTests"
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test"
            }
        }

        stage('Run JMeter Load Tests') {
            steps {
                bat "mvn verify -P jmeter-tests"
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure([
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

    }

    post {
        always {
            echo "Pipeline Completed"
        }
    }
}