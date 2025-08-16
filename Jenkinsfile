import groovy.json.JsonSlurper

pipeline {
    agent { label 'maven' }

    parameters {
        gitParameter(
            branch: '',
            branchFilter: 'origin/(.*)',
            defaultValue: 'main',
            description: 'Select a Git branch to build',
            name: 'BRANCH',
            quickFilterEnabled: true,
            selectedValue: 'DEFAULT',
            sortMode: 'DESCENDING',
            type: 'PT_BRANCH'
        )
    }

    stages {
        stage('Test Allure CLI') {
            steps {
                sh "allure --version"
            }
        }

        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: "${params.BRANCH}"]],
                          userRemoteConfigs: [[url: 'https://github.com/RazMKhitaryan/otus_api_helpers.git']]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn clean test -DthreadCount=5"
            }
        }
    }

    post {
        always {
            echo "Publishing Allure results..."
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'allure-results']]
            ])

            sh 'allure generate --clean allure-results'
            echo "allure folder generated"

           script {
               def summaryFile = 'allure-report/widgets/summary.json'
               def summaryContent = readFile(summaryFile)

               def json = new groovy.json.JsonSlurper().parseText(summaryContent)

               // Extract only primitive values for pipeline
               def passedCount = json.statistic.passed as int
               def totalCount = json.statistic.total as int
               def message = "Allure Report Api run (${params.BRANCH}): ${passedCount}/${totalCount} tests passed ✅"

               def botToken = '8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU'
               def chatId = '6877916742'

               // Send message
               sh """
               curl -s -X POST https://api.telegram.org/bot${botToken}/sendMessage \
                    -d chat_id=${chatId} \
                    -d text="${message}"
               """
           }

            echo "Pipeline finished"
        }
    }
}
