pipeline {
    agent any

    environment {
        JMETER_DIR = 'jmeter'
        JMX_FILE   = 'PrepMatePerfTest.jmx'
    }

    stages {
        stage('Setup') {
            steps {
                git branch: 'main', url: 'https://github.com/sohamghs02/PrepMate-Testing.git'
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
            archiveArtifacts artifacts: "results.jtl, report/**", fingerprint: true
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