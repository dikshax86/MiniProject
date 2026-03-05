pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'scientific-calculator'
        GITHUB_REPO_URL = 'https://github.com/dikshax86/MiniProject.git'
        DOCKER_HUB_USERNAME = 'dknights'
    }

    stages {
        stage('Clone Git') {
            steps {
                script {
                    git branch: 'master',
                        credentialsId: 'github_credentials',
                        url: "${GITHUB_REPO_URL}"
                }
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
                        // sh "docker tag calculator dknights/calculator:latest"
                        // sh "docker push dknights/calculator"

                        sh 'docker tag scientific-calculator dknights/scientific-calculator:latest'
                        sh 'docker push dknights/scientific-calculator:latest'
                    }
                }
            }
        }

        stage('Deploy using Ansible') {
            steps {
                // Execute ansible playbook
                ansiblePlaybook(
                    playbook: "deploy.yml",
                    inventory: "inventory"
                )
            }
        }
    }
}
