#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Setup') {
            steps {
                echo 'Setting up Workspace'
                sh './gradlew setupDecompWorkspaceAll'
            }
        }
        stage('Build and Deploy') {
            steps {
                echo 'Building and Deploying to Maven'
					sh './gradlew assembleAll uploadArchives'
                }
            }
        }
    post {
        always {
            archive 'build/libs/**.jar'
        }
    }
}
