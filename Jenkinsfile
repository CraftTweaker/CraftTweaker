#!/usr/bin/env groovy

pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Cleaning Project'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean'
                }
            }
        }
        stage('Build and Deploy') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Building and Deploying to Maven'
                    sh './gradlew build genGitChangelog publish curseforge'
                }
            }
        }
    }
    post {
        always {
            archive 'build/libs/**.jar'
        }
    }
}
