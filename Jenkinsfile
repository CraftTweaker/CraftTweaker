#!/usr/bin/env groovy

def docsOutDir = 'docsOut'
def docsRepositoryUrl = 'git@github.com:CraftTweaker/CraftTweaker-Documentation.git'
def gitSshCredentialsId = 'crt_git_ssh_key'
def botUsername = 'crafttweakerbot'
def botEmail = 'crafttweakerbot@gmail.com'

def documentationDir = 'CrafttweakerDocumentation'
def exportDirInRepo = 'docs_exported/1.16/crafttweaker'


def branchName = "1.16";

pipeline {
    agent any
    tools {
        jdk "jdk8u292-b10"
    }

    environment {
        ORG_GRADLE_PROJECT_secretFile = credentials('mod_build_secrets')
    }


    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }

        stage('Build') {
            steps {
                echo 'Building'
                sh './gradlew build'
            }
        }

        stage('Test') {
            steps {
                echo 'Checking'
                sh './gradlew check'
            }
        }

        stage('Git Changelog') {
            steps {
                sh './gradlew genGitChangelog'
            }
        }

        stage('Publish') {
            stages {
                stage('Updating Version') {
                    when {
                        branch '1.16'
                    }
                    steps {
                        script {
                            if (sh(script: "git log -1 --pretty=%B | fgrep -i -e '[skip deploy]' -e '[skip-deploy]'", returnStatus: true) == 0) {
                                echo 'Skipping Update Version due to [skip deploy]'
                            } else {
                                echo 'Updating Version'
                                sh './gradlew updateVersionTracker'
                            }
                        }

                    }
                }

                stage('Deploying to Maven') {
                    when {
                        branch branchName
                    }
                    steps {
                        echo 'Deploying to Maven'
                        sh './gradlew publish'
                    }
                }

                stage('Deploying to CurseForge') {
                    when {
                        branch branchName
                    }
                    steps {
                        script {
                            if (sh(script: "git log -1 --pretty=%B | fgrep -i -e '[skip deploy]' -e '[skip-deploy]'", returnStatus: true) == 0) {
                                echo 'Skipping CurseForge due to [skip deploy]'
                            } else {
                                echo 'Deploying to CurseForge'
                                sh './gradlew curseforge'
                            }
                        }

                    }
                }

                stage('Exporting Documentation') {
                    when {
                        branch branchName
                    }
                    steps {
                        echo "Cloning Repository at Branch main"

                        dir(documentationDir) {
                            git credentialsId: gitSshCredentialsId, url: docsRepositoryUrl, branch: "main", changelog: false
                        }


                        echo "Clearing existing Documentation export"
                        dir(documentationDir) {
                            sh "rm --recursive --force ./$exportDirInRepo"
                        }


                        echo "Moving Generated Documentation to Local Clone"
                        sh "mkdir --parents ./$documentationDir/$exportDirInRepo"
                        sh "mv ./$docsOutDir/* ./$documentationDir/$exportDirInRepo/"


                        echo "Committing and Pushing to the repository"
                        dir(documentationDir) {
                            sshagent([gitSshCredentialsId]) {
                                sh "git config user.name $botUsername"
                                sh "git config user.email $botEmail"
                                sh 'git add -A'
                                //Either nothing to commit, or we create a commit
                                sh "git diff-index --quiet HEAD || git commit -m 'CI Doc export for build ${env.BRANCH_NAME}-${env.BUILD_NUMBER}\n\nMatches git commit ${env.GIT_COMMIT}'"
                                sh "git push origin main"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts 'build/libs/**.jar'
            archiveArtifacts 'changelog.md'
            junit 'build/test-results/**/*.xml'
        }
    }
    options {
        disableConcurrentBuilds()
    }
}
