pipeline{ // the entire Jenkins Job needs to go inside the pipeline section

    agent{
        // this is where we will tell Jenkins what agent to use for this build
        kubernetes{
            // this tells Jenkins to use the pod called "devops" we defined in the jenkins-values.yaml file
            // which will give us access to the docker commands we need to build/push our docker image
            inheritFrom "planetarium"
        }
    }

    environment{
        // any environment variables we want to use can go in here
        // I recommend setting variables for the docker registry (which doubles as the image name)
        // and a variable to represent the image itself
        PLANETARIUM_REGISTRY='hrcode95/jenkins:test'
        PLANETARIUM_IMAGE=''
        HOST='postgres-cluster-ip-service'
        PORT='5432'
        DATABASE='postgres'
        POSTGRES=credentials('postgres')
    }

    stages{
        // this is where the steps of the job will be defined
        stage("build and test"){
            // steps is where the actual commands go
            steps{
                // echo "print something to the console"
                container("docker"){
                    // the script section is sometimes needed when using functions provided by Jenkins plugins
                    script{
                        //def args = "-e POSTGRES_HOST=$HOST -e POSTGRES_PORT=$PORT -e POSTGRES_DATABASE=$DATABASE -e POSTGRES_USERNAME=$POSTGRES_USR -e POSTGRES_PASSWORD=$POSTGRES_PSW"
                        // build(image name and tag, location of dockerfile)
                        PLANETARIUM_IMAGE= docker.build(PLANETARIUM_REGISTRY,"-f ./dockerfile.dev .")

                        docker.image('hrcode95/planetarium:test').withRun('-e POSTGRES_HOST=${HOST} -e POSTGRES_PORT=${PORT} -e POSTGRES_DATABASE=${DATABASE} -e POSTGRES_USERNAME=${POSTGRES_USR} -e POSTGRES_PASSWORD=${POSTGRES_PSW}')

                        // withRegistry(repo location empty string if docker hub, docker credentials)
                        docker.withRegistry("", 'docker-creds'){
                            PLANETARIUM_IMAGE.push("$currentBuild.number")
                            // might be worth doing two pushes, one to give a tag for the current version, and another
                            // to update the "latest" tag
                        }
                    }
                    sh 'docker -v'
                }
            }
        }
    }
}