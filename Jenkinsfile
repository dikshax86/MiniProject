pipeline {
    agent any

    triggers {
        githubPush()     
    }

    environment {
        IMAGE_NAME = "dknights/scientific-calculator:latest"
    }

    stages {

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'DockerHubCred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $IMAGE_NAME
                    '''
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook deploy.yml -i inventory'
            }
        }
    }

    post {
        success {
            mail to: 'dikshaguptax86@gmail.com',
                 subject: "SUCCESS: Scientific Calculator Pipeline",
                 body: """
Pipeline executed successfully.

Docker Image: ${IMAGE_NAME}
Build Number: ${BUILD_NUMBER}
Job Name: ${JOB_NAME}
Build URL: ${BUILD_URL}
"""
        }

        failure {
            mail to: 'dikshaguptax86@gmail.com',
                 subject: "FAILED: Scientific Calculator Pipeline",
                 body: """
Pipeline failed.
Docker Image: ${IMAGE_NAME}
Build Number: ${BUILD_NUMBER}
Job Name: ${JOB_NAME}
Build URL: ${BUILD_URL}
Check Jenkins console:

"""
        }
    }
}