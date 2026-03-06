pipeline {
    agent any

    triggers {
        githubPush()   
    }

    environment {
        DOCKER_IMAGE_NAME = 'scientific-calculator'
        GITHUB_REPO_URL = 'https://github.com/dikshax86/MiniProject.git'
        DOCKER_HUB_USERNAME = 'dknights'
    }

    stages {

        stage('Clone Git') {
            steps {
                git branch: 'master',
                    credentialsId: 'github_credentials',
                    url: "${GITHUB_REPO_URL}"
            }
        }

        stage('Build the Maven Project') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test the Maven project') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Verify JAR Existence') {
            steps {
                sh 'ls -lh target/'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}", '.')
                }
            }
        }

        stage('Upload Image to DockerHub') {
            steps {
                script {
                    docker.withRegistry('', 'DockerHubCred') {
                        sh 'docker tag scientific-calculator dknights/scientific-calculator:latest'
                        sh 'docker push dknights/scientific-calculator:latest'
                    }
                }
            }
        }

        stage('Deploy using Ansible') {
            steps {
                ansiblePlaybook(
                    playbook: "deploy.yml",
                    inventory: "inventory"
                )
            }
        }
    }

    post {

        success {
            emailext(
                subject: "SUCCESS: Jenkins Build - ${env.JOB_NAME}",
                body: """Build completed successfully.

Job Name: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}

Docker image pushed to DockerHub successfully.
Application deployed using Ansible.
""",
                to: "dikshaguptax86@gmail.com"
            )
        }

        failure {
            emailext(
                subject: "FAILED: Jenkins Build - ${env.JOB_NAME}",
                body: """Build failed.

Job Name: ${env.JOB_NAME}
Build Number: ${env.BUILD_NUMBER}
Build URL: ${env.BUILD_URL}

Please check Jenkins logs.
""",
                to: "dikshaguptax86@gmail.com"
            )
        }
    }
}