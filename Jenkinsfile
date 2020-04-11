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
        stage('Setup') {
            withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                steps {
                    echo 'Setting up Workspace'
                    sh './gradlew setupDecompWorkspaceAll'
                }
            }
        }
        stage('Build') {
            withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                steps {
                    echo 'Building'
                    sh './gradlew assembleAll publish'
                }
            }
        }
        stage('Git Changelog') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    sh './gradlew genGitChangelog'
                }
            }
        }
        stage('Publish') {
            steps {
                withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
                    echo 'Deploying to Maven'
                    sh './gradlew publish'

                    echo 'Deploying to CurseForge'
                    sh './gradlew curseforge'
                }
            }
        }
    }
    post {
        always {
            archive 'build/libs/**.jar'
            archive 'changelog.md'
        }
    }
}
