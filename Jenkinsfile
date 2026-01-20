pipeline {
    agent any

    tools {
            maven 'NewMavenInstallation_8thJan26'
    }

    environment {
        RA_DIR = 'java'
        JMETER_DIR = 'jmeter'
        JMX_FILE   = 'PrepMatePerfTest.jmx'
    }

    stages {
        stage('Setup') {
            steps {
                git branch: 'main', url: 'https://github.com/sohamghs02/PrepMate-Testing.git'
            }
        }

        stage('Selenium + TestNG Tests') {
            steps {
                bat "mvn test -Dtest=SignupPageTest,DashboardFlowTest"
            }
        }

        stage('Cucumber + JUnit Tests') {
            steps {
                bat "mvn test -Dtest=StepRunner"
            }
        }

        stage('RestAssured Tests') {
            steps {
                bat "mvn test -Dtest=api.**"
            }
        }

        stage('Performance Tests') {
            steps {
                dir("${env.JMETER_DIR}") {
                    bat """
                        if exist prepmate-report rmdir /s /q prepmate-report
                        if exist results.jtl del /f /q results.jtl

                        jmeter -n -t ${env.JMX_FILE} ^
                          -l results.jtl ^
                          -e -o prepmate-report
                    """
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: "results.jtl, prepmate-report/**", fingerprint: true
            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: "${env.JMETER_DIR}/prepmate-report",
                reportFiles: 'index.html',
                reportName: 'Prepmate Performance Dashboard'
            ])
        }
    }
}