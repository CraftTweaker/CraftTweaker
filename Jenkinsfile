#!/usr/bin/env groovy

def docsOutDir = 'docsOut'
//TODO Change to CrT repo later
def docsRepositoryUrl = 'git@github.com:kindlich/CraftTweaker-Documentation.git'
def docsRepositoryBranch = env.BRANCH_NAME
def gitSshCredentialsId = 'crt_git_ssh_key'
def botUsername = 'crtBot'
def botEmail = 'crtbot@gmail.com'

def documentationDir = 'CrafttweakerDocumentation'
def exportDirInRepo = 'docs_exported/crafttweaker'

pipeline {
    agent any

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

        stage('Git Changelog') {
            steps {
                sh './gradlew genGitChangelog'
            }
        }

        stage('Publish') {
            stages {
                stage('Updating Version') {
                    steps {
                        echo 'Updating Version'
                        //sh './gradlew updateVersionTracker'
                    }
                }

                stage('Deploying to Maven') {
                    steps {
                        echo 'Deploying to Maven'
                        //sh './gradlew publish'
                    }
                }

                stage('Deploying to CurseForge') {
                    steps {
                        echo 'Deploying to CurseForge'
                        //sh './gradlew curseforge'
                    }
                }

                stage('Exporting Documentation') {
                    steps {
                        echo "Cloning Repository at Branch $docsRepositoryBranch"
                        dir(documentationDir) {
                            git credentialsId: gitSshCredentialsId, url: docsRepositoryUrl, branch: docsRepositoryBranch, changelog: false
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
                                sh "git push origin $docsRepositoryBranch"
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
        }
    }
}