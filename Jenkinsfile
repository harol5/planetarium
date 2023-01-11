pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git(url: 'https://github.com/harol5/planetarium.git', branch: 'main')
      }
    }

    stage('Build') {
      steps {
        sh 'docker build -t hrcode95/planetarium:test -f ./dockerfile.dev .'
      }
    }

    stage('Login Docker') {
      steps {
        sh 'echo $dockerhub_PSW | docker login -u $dockerhub_USR --password-stdin'
      }
    }

  }
}