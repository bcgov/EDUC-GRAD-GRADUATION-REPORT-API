pipeline{
    agent {
        label 'maven'
    }
    parameters{
        string(defaultValue: '1.0.0', description: 'Semantic version of application. Valid versions are like 1.5.9', name: 'buildVersion')
    }
    environment{
        OCP_PROJECT = '77c02f-dev'
        IMAGE_PROJECT = '77c02f-tools'
        IMAGE_TAG = 'latest'
        APP_SUBDOMAIN_SUFFIX = '77c02f-test'
        APP_DOMAIN = 'apps.silver.devops.gov.bc.ca'
        //TAG = 'test'
        REPO_NAME = 'educ-grad-graduation-report-api'
        ORG = 'bcgov'
        BRANCH = 'main'
        SOURCE_REPO_URL = 'https://github.com/${ORG}/${REPO_NAME}'
        SOURCE_REPO_URL_RAW = 'https://raw.githubusercontent.com/${ORG}/${REPO_NAME}'
        VERSION = '${buildVersion}'
    }
    stages{
        stage('Build') {
            steps {
                script {
                    openshift.withCluster() {
                        def bcTemplate =
                        openshift.apply(
                                openshift.process("-f", "${SOURCE_REPO_URL_RAW}/${BRANCH}/tools/openshift/api.bc.yaml",
                                        "REPO_NAME=${REPO_NAME}", "VERSION=${VERSION}")
                        )
                        def buildSelector = openshift.selector("bc", "${REPO_NAME}-bc").startBuild()
                        sleep(20)
                        buildSelector.logs('-f')
                    }
                }
            }
            post {
                success {
                    echo 'Build Success'
                }
                failure {
                    echo 'Build stage Failed!'
                }
            }
        }
        stage('Deploy to TEST') {
            steps{
                script {
                    openshift.withCluster() {
                        openshift.withProject(OCP_PROJECT) {
                            openshift.apply(
                                    openshift.process("-f", "${SOURCE_REPO_URL_RAW}/${BRANCH}/tools/openshift/api.dc.yaml",
                                            "REPO_NAME=${REPO_NAME}", "HOST_ROUTE=${REPO_NAME}-${APP_SUBDOMAIN_SUFFIX}.${APP_DOMAIN}")
                            )
                            openshift.selector("dc", "${REPO_NAME}-dc").rollout().latest()
                            timeout (time: 10, unit: 'MINUTES') {
                                openshift.selector("dc", "${REPO_NAME}-dc").rollout().status()
                            }
                        }
                    }
                }
            }
            post{
                success {
                    echo "${REPO_NAME} successfully deployed to TEST"
                    /*script {
                        openshift.withCluster() {
                            openshift.withProject(IMAGE_PROJECT) {
                                echo "Tagging image"
                                openshift.tag("${IMAGE_PROJECT}/${REPO_NAME}:latest", "${REPO_NAME}:${TAG}")
                            }
                        }
                    }*/
                }
                failure {
                    echo "${REPO_NAME} deployment to TEST Failed!"
                }
            }
        }
    }
}
