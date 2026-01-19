
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sohamghs02/PrepMate-Testing.git'
            }
        }

        stage('Run Interview CRUD Ops Perf Test') {
            steps {
                bat '''
                cd jmeter

                if exist interview-report rmdir /s /q interview-report
                if exist interview-results.jtl del /f /q interview-results.jtl
                
                jmeter -n -t PrepMatePerfTest.jmx ^
                  -l interview-results.jtl ^
                  -e -o interview-report
                '''
            }
        }

        stage('Run Subtopics Ops Perf Test') {
            steps {
                bat '''
                    cd jmeter

                    if exist subtopics-report rmdir /s /q subtopics-report
                    if exist subtopics-results.jtl del /f /q subtopics-results.jtl

                    jmeter -n -t PrepMatePerfTest.jmx ^
                     -l subtopics-results.jtl ^
                     -e -o subtopics-report
                '''
            }
        }

        stage('Run Questions CRUD Ops Perf Test') {
            steps {
                bat '''
                cd jmeter

                if exist questions-report rmdir /s /q questions-report
                if exist questions-results.jtl del /f /q questions-results.jtl

                jmeter -n -t PrepMatePerfTest.jmx ^
                 -l questions-results.jtl ^
                 -e -o questions-report
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'jmeter/login-report/**, jmeter/interview-report/**, jmeter/subtopics-report/**, jmeter/questions-report/**', fingerprint: true
        }
    }
}
