def dockerImage = 'build-tools/android-build-box:latest'

node('d3-build-agent') {
    properties(
	    [
		    disableConcurrentBuilds()
		]
    )
    timestamps {
        try {
            stage('Git pull'){
                def scmVars = checkout scm
                env.GIT_BRANCH = scmVars.GIT_LOCAL_BRANCH
                env.GIT_COMMIT = scmVars.GIT_COMMIT
            }
            withCredentials([
                [$class: 'FileBinding', credentialsId: 'firebase_sora_app_distribution_key', variable: 'GOOGLE_APPLICATION_CREDENTIALS'],
                [$class: 'FileBinding', credentialsId: 'android_keystore_debug', variable: 'CI_KEYSTORE_PATH'],
                [$class: 'StringBinding', credentialsId: 'android_keystore_storepass_debug', variable: 'CI_KEYSTORE_PASS'],
                [$class: 'StringBinding', credentialsId: 'android_keyalias_debug', variable: 'CI_KEYSTORE_KEY_ALIAS'],
                [$class: 'StringBinding', credentialsId: 'android_keypass_debug', variable: 'CI_KEYSTORE_KEY_PASS']])
            {
                docker.withRegistry('https://docker.soramitsu.co.jp', 'nexus-build-tools-ro') {
                    docker.image("${dockerImage}").inside() {
                        //stage('Test') {
                        //    sh "./gradlew clean testDebugUnitTest"
                        //}
                        stage('Check code style') {
                            sh "./gradlew ktlint"
                        }
                        stage('Run tests') {
                            sh "./gradlew clean runModuleTests"
                        }
                        stage('Build and deploy') {
                            def git_commit_message = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%s'").trim()
                            def git_commit_author = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%an'").trim()
                            env.CI_FIREBASE_RELEASENOTES = "Commit: ${env.GIT_COMMIT} Message: ${git_commit_message} Author: ${git_commit_author}"
                            if (env.GIT_BRANCH == 'master') {
                                env.CI_FIREBASE_GROUP = 'sora-test'
                                sh "./gradlew clean assembleTstCifirebasedebug appDistributionUploadTstCifirebasedebug"
                            }
                            else if (env.GIT_BRANCH == 'develop') {
                                env.CI_FIREBASE_GROUP = 'sora-dev'
                                sh "./gradlew clean assembleDevelopCifirebasedebug appDistributionUploadDevelopCifirebasedebug"
                            } else {
                                sh "./gradlew clean assembleDevelopCidebug"
                            }
                        }
                    }
                }
            }
        } catch (e) {
            currentBuild.result = 'FAILURE'
        } finally {
            archiveArtifacts allowEmptyArchive: true, artifacts: 'build/reports/checkstyle/*.html, build-logs/*.gz'
            cleanWs()
        }
    }
}