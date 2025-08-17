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
                checkout([
                    $class: 'GitSCM',
                    branches: [[ name: "${params.BRANCH}" ]],
                    userRemoteConfigs: [[ url: 'https://github.com/RazMKhitaryan/otus_api_helpers.git' ]]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                sh "mvn clean test -DthreadCount=5 || true"
            }
        }
    }

    post {
        always {
            echo "Publishing Allure results..."
            allure([
                includeProperties: false,
                reportBuildPolicy: 'ALWAYS',
                results: [[ path: 'allure-results' ]]
            ])

            sh 'allure generate --clean allure-results'
            echo "Allure folder generated"

            script {
                try {
                    def summaryFile = readFile('allure-report/widgets/summary.json')
                    def summary = new JsonSlurper().parseText(summaryFile)

                    def total = summary.statistic.total ?: 0
                    def passed = summary.statistic.passed ?: 0

                    def message = """✅ API Test Execution Finished
Passed: ${passed}/${total}
"""

                    sh """
                        curl -s -X POST https://api.telegram.org/bot8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU/sendMessage \
                             -d chat_id=6877916742 \
                             -d text="${message}"
                    """
                } catch (Exception e) {
                    echo "⚠️ Could not read allure summary or send Telegram notification: ${e.message}"
                }
            }
        }
    }
}
