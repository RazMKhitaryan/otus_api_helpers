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
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'allure-results']]
            ])

            sh 'allure generate --clean allure-results'
            echo "allure folder generated"

            script {
                // Read summary and extract only primitive values
                def summaryFile = 'allure-report/widgets/summary.json'
                def summaryContent = readFile(summaryFile)

                def json = new groovy.json.JsonSlurper().parseText(summaryContent)

                // Use only primitive values
                def passedCount = json.statistic.passed.toString()
                def totalCount = json.statistic.total.toString()
                def branch = params.BRANCH
                def message = "Allure Report Api run (${branch}): ${passedCount}/${totalCount} tests passed ✅"

                // Send via curl
                sh """
                curl -s -X POST https://api.telegram.org/bot8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU/sendMessage \
                     -d chat_id=6877916742 \
                     -d text="${message}"
                """
            }

            echo "Pipeline finished"
        }
    }
}
