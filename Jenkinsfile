pipeline {
    agent any

    triggers {
        githubPush()     // Trigger build when GitHub push happens
    }

    environment {
        DOCKER_IMAGE_NAME = 'scientific-calculator'
        DOCKER_HUB_USERNAME = 'dknights'
        EMAIL_RECIPIENT = 'dikshaguptax86@gmail.com'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/dikshax86/MiniProject.git', branch: 'master'
            }
        }

        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Verify JAR File') {
            steps {
                sh 'ls -lh target/'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}")
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry('', 'DockerHubCred') {
                        sh "docker tag ${DOCKER_IMAGE_NAME} ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest"
                        sh "docker push ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy using Ansible') {
            steps {
                ansiblePlaybook(
                    playbook: 'deploy.yml',
                    inventory: 'inventory'
                )
            }
        }
    }

    post {

        success {
            emailext(
                subject: "SUCCESS: Jenkins Build ${env.JOB_NAME}",
                body: """Build completed successfully.

Job Name: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}

Docker image pushed and application deployed successfully.
""",
                to: "${EMAIL_RECIPIENT}"
            )
        }

        failure {
            emailext(
                subject: "FAILED: Jenkins Build ${env.JOB_NAME}",
                body: """Build failed.

Job Name: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}

Check Jenkins console logs for details.
""",
                to: "${EMAIL_RECIPIENT}"
            )
        }
    }
}