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
                        "mvn test -Pcucumber"
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
                bat """
                    cd src/test/jmeter

                    if exist report rmdir /s /q report
                    if exist results.jtl del /f /q results.jtl

                    jmeter -n -t PrepMatePerfTest.jmx ^
                      -l results.jtl ^
                      -e -o report
                """
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

            archiveArtifacts artifacts: "src/test/jmeter/results.jtl, src/test/jmeter/report/**", fingerprint: true

            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: "src/test/jmeter/report",
                reportFiles: 'index.html',
                reportName: 'Prepmate Performance Dashboard'
            ])
        }
    }
}
