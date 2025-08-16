pipeline {
    agent { label 'maven' }

    stages {
        stage('Test Allure CLI') {
            steps {
                sh "allure --version"
            }
        }

        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: 'main']],
                          userRemoteConfigs: [[url: 'https://github.com/RazMKhitaryan/otus_api_helpers.git']]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn clean test -DthreadCount=5"
            }
        }

        stage('Allure Report Publisher') {
            steps {
                echo "Tests finished, publishing Allure results..."
                 allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']],
                        tool: 'allure'   // this must match the name you set in Jenkins Global Tool Configuration
                    ]): [[path: 'allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
    }
}
