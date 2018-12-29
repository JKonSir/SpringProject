 pipeline {
     agent none
    stages {
        stage('Back-end') {
            agent {
                docker { image 'maven:3.6.0-jdk-8-alpine' }
            }
            steps {
                sh 'mvn --version'
             }
         }
     }
 }
