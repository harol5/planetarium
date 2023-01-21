pipeline{ // the entire Jenkins Job needs to go inside the pipeline section

    agent{
        // this is where we will tell Jenkins what agent to use for this build
        kubernetes{
            // this tells Jenkins to use the pod called "devops" we defined in the jenkins-values.yaml file
            // which will give us access to the docker commands we need to build/push our docker image
            inheritFrom "planetarium"
            yaml """
            apiVersion: v1
            kind: Pod
            metadata:
            labels:
              component: ci
            spec:
              # Use service account that can deploy to all namespaces
              serviceAccountName: cd-jenkins
              containers:
              - name: golang
                image: golang:1.10
                command:
                - cat
                tty: true
              - name: gcloud
                image: gcr.io/cloud-builders/gcloud
                command:
                - cat
                tty: true
              - name: kubectl
                image: gcr.io/cloud-builders/kubectl
                command:
                - cat
                tty: true
            """
        }
    }

    environment{
        // any environment variables we want to use can go in here
        // I recommend setting variables for the docker registry (which doubles as the image name)
        // and a variable to represent the image itself
        PLANETARIUM_TEST='hrcode95/jenkins:test'
        PLANETARIUM_IMAGE_TEST=''
        PLANETARIUM_PROD='hrcode95/jenkins:prod'
        PLANETARIUM_IMAGE_PROD=''
        HOST='postgres-cluster-ip-service'
        PORT='5432'
        DATABASE='postgres'
        POSTGRES=credentials('postgres')
        PROJECT_ID = 'academic-veld-373717'
        CLUSTER_NAME = 'cluster-1'
        LOCATION = 'us-central1-c'
        CREDENTIALS_ID = 'cluster-1'
    }

//     stages{
//         // this is where the steps of the job will be defined
//         stage("build and test"){
//             // steps is where the actual commands go
//             steps{
//                 // echo "print something to the console"
//                 container("docker"){
//                     // the script section is sometimes needed when using functions provided by Jenkins plugins
//                     script{
//                         // build(image name and tag, location of dockerfile)
//                         PLANETARIUM_IMAGE_TEST= docker.build(PLANETARIUM_TEST,"-f ./dockerfile.dev .")
//                         sh 'docker run -e POSTGRES_HOST=$HOST -e POSTGRES_PORT=$PORT -e POSTGRES_DATABASE=$DATABASE -e POSTGRES_USERNAME=$POSTGRES_USR -e POSTGRES_PASSWORD=$POSTGRES_PSW hrcode95/jenkins:test'
//                     }
//                 }
//             }
//         }

        stage("build and push to Dockerhub"){
            steps{
                container("docker"){
                   script{
                      PLANETARIUM_IMAGE_PROD= docker.build(PLANETARIUM_PROD,".")
                      docker.withRegistry("", 'docker-creds'){
                         PLANETARIUM_IMAGE_PROD.push("$currentBuild.number")
                      }
                   }
                }
                sh 'echo "************Image pushed to dockerrhub********************"'
            }
        }

        stage('Deploy to GKE') {
            steps{
                container("kubectl"){
                step([
                $class: 'KubernetesEngineBuilder',
                projectId: env.PROJECT_ID,
                clusterName: env.CLUSTER_NAME,
                location: env.LOCATION,
                manifestPattern: 'k8/planetarium-app/planetarium.yml',
                credentialsId: env.CREDENTIALS_ID,
                verifyDeployments: true])
                }
            }
        }

}