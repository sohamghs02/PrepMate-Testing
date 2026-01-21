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
            archiveArtifacts artifacts: "src/test/jmeter/results.jtl, src/test/jmeter/report/**", fingerprint: true
        }
    }
}
