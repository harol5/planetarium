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

    stage('Login') {
      environment {
        DOCKER_USERNAME = '$DOCKER_USERNAME'
        DOCKER_PASSWORD = '$DOCKER_PASSWORD'
      }
      steps {
        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
      }
    }

  }
}