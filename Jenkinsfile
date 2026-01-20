def tolerantMaven(String cmd) {
    catchError(buildResult: 'UNSTABLE', stageResult: 'SUCCESS') {
        bat cmd
    }
}

pipeline {
    agent any

    tools {
        maven 'NewMavenInstallation_8thJan26'
    }

    environment {
        RA_DIR     = 'java'
        JMETER_DIR = 'jmeter'
        JMX_FILE   = 'PrepMatePerfTest.jmx'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sohamghs02/PrepMate-Testing.git'
            }
        }

        stage('Selenium + TestNG Tests') {
            steps {
                script {
                    tolerantMaven(
                        "mvn test -Dtest=SignupPageTest,DashboardFlowTest"
                    )
                }
            }
        }

        stage('Cucumber + JUnit Tests') {
            steps {
                script {
                    tolerantMaven(
                        "mvn test -Dtest=StepRunner"
                    )
                }
            }
        }

        stage('RestAssured Tests') {
            steps {
                script {
                    tolerantMaven(
                        "mvn test -Dtest=api.**"
                    )
                }
            }
        }

        stage('Performance Tests') {
            steps {
                dir("${env.JMETER_DIR}") {
                    bat """
                        if exist report rmdir /s /q report
                        if exist results.jtl del /f /q results.jtl

                        jmeter -n -t ${env.JMX_FILE} ^
                          -l results.jtl ^
                          -e -o report
                    """
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber UI Test Report'
            ])

            archiveArtifacts artifacts: "${env.JMETER_DIR}/results.jtl, ${env.JMETER_DIR}/report/**", fingerprint: true

            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: "${env.JMETER_DIR}/report",
                reportFiles: 'index.html',
                reportName: 'Prepmate Performance Dashboard'
            ])
        }
    }
}
