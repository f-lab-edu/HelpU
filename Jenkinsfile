pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh 'chmod +x gradlew'
        sh 'sudo ./gradlew'
      }
    }
  }
}