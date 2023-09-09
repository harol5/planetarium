pipeline{

    agent{
        kubernetes{
            inheritFrom "planetarium"
            yaml """
            apiVersion: v1
            kind: Pod
            metadata:
            labels:
              component: ci
            spec:
              # Use service account that can deploy to all namespaces
              serviceAccountName: jenkins-planetarium
              containers:
              - name: kubectl
                image: gcr.io/cloud-builders/kubectl
                command:
                - cat
                tty: true
            """
        }
    }

    environment{
        PLANETARIUM_TEST='hrcode95/jenkins:test'
        PLANETARIUM_IMAGE_TEST=''
        PLANETARIUM_PROD="hrcode95/planetarium:${env.BUILD_NUMBER}"
        PLANETARIUM_IMAGE_PROD=''
        HOST='postgres-cluster-ip-service'
        PORT='5432'
        DATABASE='postgres'
        POSTGRES=credentials('postgres')
        PROJECT_ID = 'natural-region-398523'
        CLUSTER_NAME = 'cluster-1'
        LOCATION = 'us-central1-c'
        CREDENTIALS_ID = 'cluster-1'
    }

    stages{

        stage("build image for testing"){
            steps{
                container("docker"){
                    script{
                        PLANETARIUM_IMAGE_TEST= docker.build(PLANETARIUM_TEST,"-f ./dockerfile.dev .")
                    }
                }
            }
        }

        stage("testing"){
            steps{
                container("docker"){
                    script{
                        sh 'docker run -e POSTGRES_HOST=$HOST -e POSTGRES_PORT=$PORT -e POSTGRES_DATABASE=$DATABASE -e POSTGRES_USERNAME=$POSTGRES_USR -e POSTGRES_PASSWORD=$POSTGRES_PSW hrcode95/jenkins:test'
                    }
                }
            }
        }

        stage("build and push to Dockerhub"){
            steps{
                container("docker"){
                   script{
                      PLANETARIUM_IMAGE_PROD= docker.build(PLANETARIUM_PROD,".")
                      docker.withRegistry("", 'docker-creds'){
                         PLANETARIUM_IMAGE_PROD.push()
                      }
                   }
                }
                sh 'echo "************Image pushed to dockerhub********************"'
            }
        }

        stage('Deploy canary') {
            when { branch 'canary' }
            steps{
                sh 'echo "********************************CANARY*************************************"'
                container("kubectl"){
                step([
                $class: 'KubernetesEngineBuilder',
                projectId: env.PROJECT_ID,
                clusterName: env.CLUSTER_NAME,
                location: env.LOCATION,
                manifestPattern: 'k8/canary',
                credentialsId: env.CREDENTIALS_ID,
                verifyDeployments: true])
                sh("kubectl set image deployment/planetarium-canary planetarium=${PLANETARIUM_PROD}")
                }
            }
        }

        stage('Deploy production') {
            when { branch 'main' }
            steps{
                container("kubectl"){
                step([
                $class: 'KubernetesEngineBuilder',
                projectId: env.PROJECT_ID,
                clusterName: env.CLUSTER_NAME,
                location: env.LOCATION,
                manifestPattern: 'k8/planetarium-app',
                credentialsId: env.CREDENTIALS_ID,
                verifyDeployments: true])
                sh("kubectl set image deployment/planetarium-deployment planetarium=${PLANETARIUM_PROD}")
                }
            }
        }
    }
}