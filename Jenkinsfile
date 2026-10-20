/*  Jenkins will fetch the Jenkinsfile from your existing GitHub repository and run your Spring Boot + React build.
    A Jenkinsfile is a text file, checked into your repo, that defines your CI/CD pipeline as code. Jenkins reads it and runs the steps for you.
    There are two syntaxes:
    1) Declarative is structured and opinionated, and is what beginners should use. pipeline { } syntax (use this first)
    2) Scripted is plain Groovy with more flexibility but less structure. node { } syntax
*/

/*  @Library('my-shared-lib') tells Jenkins to load a shared library named my-shared-lib. You can use the library’s functions in your Jenkinsfile.
    Shared libraries are useful for code that you want to reuse across multiple pipelines, such as utility functions, custom steps, or common configurations.
    You can define a shared library in Jenkins under Manage Jenkins > Configure System > Global Pipeline Libraries. You can also define it in your repo using a vars/ directory and a src/ directory.
*/
// @Library('my-shared-lib')

/*  This is a simple function that takes a name as input and returns a welcome message. You can call this function from your pipeline stages or steps.
    Functions are useful for encapsulating logic that you want to reuse or test separately. You can define functions in your Jenkinsfile or in a shared library.
*/
def welcomeMessage(name)
{
    return "Welcome to the Jenkins Pipeline, ${name}!"
}

def notify(msg)
{
    echo "NOTIFY: ${msg}"
}

/*  This declares a Jenkins Declarative Pipeline. Everything belonging to this workflow goes inside its braces.
    This is the main pipeline block that defines the CI/CD workflow. It contains stages, steps, and post actions that run in order.
*/
pipeline
{
    /* Every pipeline has to run somewhere: on a real computer with CPU, disk, and tools like Maven or Git. Jenkins doesn’t run commands “in the air.”
       So when you click Build Now, Jenkins asks: “Which machine should run these steps?”
       The agent line in your Jenkinsfile is your answer to that question.
       Example
       pipeline { agent any }               --> You’re telling Jenkins: “Run this on any machine you have that’s free.”
       pipeline { agent { label 'linux' } } --> You’re telling Jenkins: “Run this only on a machine labeled linux.”
       pipeline { agent { label 'linux' } } --> Meaning: “Don’t pick a machine for the whole pipeline. I’ll choose one inside each stage.” Why use it: different stages may need different environments. Build on Linux, test in a Node container, and so on. Also useful for stages that need no machine at all, such as a manual approval step.
       agent { docker { image 'maven:3.9-eclipse-temurin-17' } } } --> Meaning: “Run this pipeline inside a Docker container with Maven and Java 17 installed.” Why use it: you don’t have to install Maven or Java on your Jenkins machine. You can run your build in a clean environment every time.
       agent { dockerfile true } Meaning: “Look for a file named Dockerfile in my repo, build an image from it, and run my steps inside that image.”
       Use this when no ready-made image fits and you need a custom environment (specific tools, libraries, versions).
       If Jenkins is installed only on your laptop, there’s just one machine, so agent any picks your laptop. Nothing else to choose from. In a company: it runs on one of the agent machines.
       Step by step
       1) You click Build Now.
       2) Jenkins reads your Jenkinsfile and sees the agent line.
       3) Jenkins looks at its list of available machines.
       4) It picks one that matches what agent says.
       5) It sends your steps (sh 'mvn package', etc.) to that machine.
       6) That machine runs them and sends the results back.
    */
    agent any

    /*  Which build tools does this pipeline need?
        tools tells Jenkins which build tools (Java, Maven, Node.js) this pipeline needs. Jenkins looks up each tool by name in its own settings, sets it up on the agent, and adds it to the PATH so java, mvn and npm work in your sh steps.
    */
    tools
    {
        jdk    'JDK-25'              // Java 25 for the Spring Boot backend (also sets JAVA_HOME)
        maven  'MAVEN-3.10'          // Maven 3.10.0 for `mvn clean package`
        nodejs 'NODEJS-22'           // Node.js 22 (and npm) for the React frontend
    }

    /*  What inputs can the user give when starting it?
        Parameters are useful for customizing the pipeline behavior without changing the Jenkinsfile. You can define parameters of different types, such as choice, boolean, string, etc.
        When you define parameters, Jenkins shows a “Build with Parameters” button on the job page. The user can click it and fill in the values before starting the build.
        You can access the parameter values in your pipeline using params.PARAM_NAME syntax.
    */
    parameters
    {
       // Reminders from earlier: the first build uses the defaults and the “Build with Parameters” button appears from the second build, and automatic triggers (pollSCM, webhooks) always use the defaults.
       // choice: name: 'ENV'	The variable name. You read it as params.ENV, choices: [...]	The allowed values. The first one (prod) is the default description	Help text shown under the field
       // When to use it: when only a fixed set of values is valid, such as environments, regions or build types. Why: the user can’t make a typo. They can only pick a listed value, so prod can never become porod.
       choice(name: 'ENV', choices: ['prod', 'qa', 'dev'], description: 'Target environment')

       // booleanParam: When to use it: for yes/no switches, such as run tests, push the image, send a notification, or do a clean build. Why: it lets the user turn a stage on or off without editing the Jenkinsfile. For example, skip tests for a quick hotfix, or deploy only when asked.
       booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Untick to skip tests')
       booleanParam(name: 'PUSH_IMAGE', defaultValue: false, description: 'Push the image to Docker Hub')
       booleanParam(name: 'DEPLOY',     defaultValue: false, description: 'Enable deployment after Docker Push, Run the container after the build')

       // string: When to use it: for text inputs, such as a version number or a URL. Why: it allows the user to provide custom values.
       string(name: 'VERSION', defaultValue: '1.0', description: 'Release version to deploy')
       string(name: 'EMAIL', defaultValue: 'swasthikbhatt18@gmail.com', description: 'Who to notify when the build ends')
    }

    /*  Use environment when the value should be the same every time and parameters when it should be chosen each time.
        Block order in the file doesn’t matter. You can write environment above parameters, or below it. Jenkins accepts both (agent and stages are the required sections, and the rest can come in any order).
    */
    environment
    {
       IMAGE  = 'swasthik/myapp'                  // fixed
       TAG    = "${env.BUILD_NUMBER}"             // changes each build
       TARGET = "${params.ENV}"                  // copied from the user's choice, we are taking the value from the ENV parameter defined in the parameters block
       FULL_IMAGE_NAME   = "${IMAGE}:${TAG}"
       PATH = "/usr/local/bin:${env.PATH}" // /usr/local/bin' — the directory where your Mac's docker command is located. PATH+DOCKER — Jenkins syntax for adding an extra directory to the existing PATH. DOCKER is just a label; you can use another label.
    }

    options // How should this pipeline behave?
    {
        timestamps() // Add timestamps to the console output. Useful for debugging and performance analysis.
        timeout(time: 30, unit: 'MINUTES') // If the whole pipeline runs longer than 30 minutes, Jenkins aborts it. Stops hung builds from blocking agents forever.
        buildDiscarder(logRotator(
                    daysToKeepStr: '7', // Keep build logs for 7 days. After that, delete them.
                    numToKeepStr: '10', // Keep only the last 10 build logs.
                    artifactDaysToKeepStr: '7', // Keep artifacts for 7 days. After that, delete them.
                    artifactNumToKeepStr: '10' // Keep only the last 10 artifacts.
                ))
        disableConcurrentBuilds() // Same option as in UI: do not allow concurrent builds. Only one build of this job runs at a time. If a second is triggered, it waits in the queue. Prevents two deployments colliding. When the pipeline runs, Jenkins reads the options { }, parameters { } and triggers { } from the Jenkinsfile and applies them to the job. If the UI had a different value, it gets overwritten.
        retry(2) // Re-runs the whole pipeline up to 2 more times if it fails. So with retry(2), if the pipeline fails, Jenkins runs it one more time, and if that also fails, the build is marked FAILED.
        quietPeriod(30) // It makes Jenkins wait 30 seconds after a build is triggered before actually starting it. If more triggers arrive during that wait, they are merged into one build. Imagine a developer pushes 3 commits quickly, one after another. Default (a short wait of about 5 seconds): the builds may start separately, and you can end up with several builds for what was really one piece of work. With quietPeriod(30): You get one build instead of three, which saves agent time and keeps the build history clean.
        ansiColor('xterm') // Shows colored console output. Needs the AnsiColor plugin.
        durabilityHint('PERFORMANCE_OPTIMIZED')	// Faster builds, but less safe if Jenkins crashes. Values: MAX_SURVIVABILITY, SURVIVABLE_NONATOMIC, PERFORMANCE_OPTIMIZED.
        skipStagesAfterUnstable() // A build marked unstable never reaches push or deploy. If a stage makes the build UNSTABLE (for example, test failures), the later stages are skipped.

        // Normally, when you use Pipeline script from SCM, Jenkins automatically checks out your repository at the beginning of the Pipeline.
        // This option tells Jenkins Don't automatically check out the repository at the beginning. I'll decide when to check it out.
        // Tells Jenkins: “Don’t download the code automatically. I’ll do it myself, or not at all. Checkout = download your code from Git onto the Jenkins machine. Jenkins can’t build code it doesn’t have. What Jenkins does by default When your build starts, Jenkins automatically downloads your code first. You don’t write anything for it. stages { stage('Build') { steps { sh 'mvn package' } } works, because Jenkins already downloaded the code, What skipDefaultCheckout() does: It tells Jenkins: “Don’t download the code automatically. I’ll do it myself, or not at all. When would you do it yourself? Only when you need something special, such as downloading a different repo or branch.
        skipDefaultCheckout()
    }

    triggers // When should this pipeline run automatically?
    {
        githubPush()	// Runs instantly when GitHub sends a push webhook. Needs the GitHub plugin.
        // gitlab(triggerOnPush: true, triggerOnMergeRequest: true, branchFilterType: 'All')	// Runs on GitLab push or merge request events. Needs the GitLab plugin.
        // bitbucketPush()	// Runs on a Bitbucket push webhook. Needs the Bitbucket plugin.
        // issueCommentTrigger('.*test this please.*')	// Runs when someone writes a matching comment on a pull request. Needs the GitHub Pull Request Comment Trigger plugin.
        pollSCM('H/5 * * * *') // Poll the Git repository every 5 minutes for changes. If there are changes, run the pipeline.
        // cron('@daily'), cron('@hourly'), cron('@midnight') Between midnight and 3 AM, @yearly / @annually
    }

    stages
    {
        stage('Checkout')
        {
            steps
            {
                checkout scm // A Jenkins Pipeline step that retrieves source code from a configured SCM. Refers to the job's configured source-control settings, including your Git repository and branch.

                // env.GITHUB_URL = ... and env.COMMIT_ID = ... are Groovy assignments, Put them inside the script{} block.
                script
                {
                    env.GITHUB_URL = sh(script: 'git config --get remote.origin.url', returnStdout: true).trim()
                    env.COMMIT_ID = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    echo "GitHub URL: ${env.GITHUB_URL}"
                    echo "Commit ID: ${env.COMMIT_ID}"
                }
            }
        }

        stage('Versions')
        {
            steps
            {
                sh 'java -version'
                sh 'mvn -version'
                sh 'node --version'
                sh 'npm -version'
                sh 'git --version'
                sh 'docker --version'
            }
        }

        /*  It is used to check out a second GitHub repository into a folder named config inside your Jenkins workspace. The config directory receives a checkout of the repository. Because the URL in your example points to your existing project1 repository.
            For your project1-pipeline, Your earlier checkout scm step already checks out the Spring Boot + React repository. you're actually cloning the same project again into a subdirectory named config, not a separate configuration repository
        */
        stage('Get config repo')
        {
            steps
            {
                dir('config')
                {
                    git url: 'https://github.com/swasthikbhatt/project1.git', branch: 'main'
                }
            }
        }

        stage("init")
        {
            steps
            {
                script
                {
                    gv = load "script.groovy"
                }
            }
        }

        /*  Jenkins already runs sh scripts with -e (and -x, which prints each command), so -e is already on and -u is the useful addition.
            -e	Stop the script as soon as any command fails
            -u	Treat an unset variable as an error instead of an empty value
            ls -lh target/*.jar Lists the jar with a readable size, which proves it was created

            stash saves files from one stage. unstash restores them in another stage, even on a different agent. Use them when stages run on different machines or in different containers, because each one has its own workspace.
        */
        stage('Build Backend')
        {
            tools
            {
                jdk   'JDK-25'
                maven 'MAVEN-3.10'
            }
            options { timeout(time: 10, unit: 'MINUTES') }
            steps
            {
                script
                {
                    gv.buildApp('Spring Boot')
                }
                retry(2)
                {
                    sh '''
                        set -eu
                        mvn "-Ddeveloper.name=swasthik bhat" "-Ddeveloper.father.name=suresh bhat" "-Ddeveloper.mother.name=shantha bhat" "-Ddeveloper.sister.name=swathi bhat" clean package -DskipTests
                        echo "===== Backend JAR ====="
                        ls -lh target/*.jar
                    '''

                    // Jenkins bundles the matching files and stores them temporarily under the name backend-jar, for use later in the same pipeline run. stash does not create the JAR. Maven creates it. stash saves a copy of the file for later use.
                    // Where does Jenkins store the stashed file? You don't need to specify a folder or path for the stash itself. Conceptually, think of Jenkins as keeping a temporary package.
                    stash name: 'backend-jar', includes: 'target/*.jar'   // save the jar
                    stash name: 'frontend-files', includes: 'frontend/**'
                    stash name: 'docker-files', includes: 'Dockerfile'
                }
            }
        }

        stage('Build Frontend')
        {
            steps
            {
                dir('frontend')
                {
                    script
                    {
                        gv.buildApp('React')
                    }
                    sh '''
                        npm ci
                        npm run build
                    '''
                }
            }
        }

        stage('Test')
        {
            when { expression { params.RUN_TESTS } }
            options { timeout(time: 10, unit: 'MINUTES') }
            steps
            {
                script
                {
                    gv.testApp()
                }
                sh '''
                            set -eu
                            mvn "-Ddeveloper.name=swasthik bhat" "-Ddeveloper.father.name=suresh bhat" "-Ddeveloper.mother.name=shantha bhat" "-Ddeveloper.sister.name=swathi bhat" test
                   '''
            }
            post
            {
                // // Runs every time, whether the stage passed, failed, was unstable or was aborted
                always
                {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml' // after the stage finishes, whatever the outcome, Jenkins reads the Maven test report files and publishes them as a test report, and it doesn’t raise an error if there are no report files.
                }
            }
        }

        stage('Archive')
        {
            steps
            {
                archiveArtifacts artifacts: 'target/*.jar, frontend/dist/**', fingerprint: true, allowEmptyArchive: true // Saves the jar and frontend/dist in Jenkins, so you can download them. Vite normally builds to frontend/dist, so check that folder name in your project.
            }
        }

        stage('Check Docker')
        {
            steps
            {
                sh '''
                    echo "$PATH"
                    which docker
                    docker --version
                    docker info
                '''
            }
        }

        stage('Docker Build')
        {
            agent any    // could be a different machine, e.g. agent { label 'docker' }
            steps
            {
                // Restore the Spring Boot JAR from the Build Backend stage
                unstash 'backend-jar'
                unstash 'frontend-files'
                unstash 'docker-files'

                // If the JAR is already in the project folder, you don't need to restore it again — provided both stages use the same workspace and the file is still there.
                // Different agents or workspaces Suppose Jenkins builds your backend on Machine A and builds the Docker image on Machine B. Without unstash, Machine B might not have the JAR, so the Docker build could fail.
                // It runs in the current working directory of the Jenkins agent. Verify the JAR exists
                sh 'ls -lh target/*.jar'


                /*  This creates one image with two tags:
                    - swasthik/myapp:25
                    - swasthik/myapp:latest
                    Both tags point to the same image ID. How to prove it?
                    docker images
                    You might see:
                    REPOSITORY       TAG       IMAGE ID
                    swasthik/myapp   25        abc123def456
                    swasthik/myapp   latest    abc123def456
                */
                sh 'docker build -t $FULL_IMAGE_NAME -t $IMAGE:latest .'

                sh 'docker save -o project1-image.tar $FULL_IMAGE_NAME'
                sh 'docker save -o project1-latest-image.tar $IMAGE:latest'

                // Save the TAR file under this Jenkins build's Artifacts
                archiveArtifacts artifacts: 'project1-image.tar', fingerprint: true
            }
        }

        /*
            Your Jenkins pipeline needs to upload your Docker images to Docker Hub.
            Docker Hub needs to know who is uploading the image. Jenkins therefore needs credentials to log in.
            Instead of writing your password or access token directly in your Jenkinsfile, you save it securely in Jenkins. Your pipeline retrieves it when needed. Jenkins credentials in Jenkins UI Stores your username and access token.
            Later Docker Hub Accepts the authenticated image push
            Steps:
            Jenkins → Manage Jenkins → Credentials
            Click Add Credentials
            Fill in the credential form
            Docker Hub username --> swasthik18
            Id --> dockerhub-creds

            usernameVariable: 'DH_USER_PROJECT1'	Jenkins temporarily stores your Docker Hub username in the environment variable DH_USER.
            passwordVariable: 'DH_PASS_PROJECT1'	Jenkins temporarily stores your Docker Hub access token in DH_PASS.
            --password-stdin tells Docker to read the password or access token from standard input (stdin) instead of asking you to type it interactively or passing it as a command-line argument.
        */
        stage('Docker Push')
        {
            when { expression { params.PUSH_IMAGE } }
            steps
            {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DH_USER_PROJECT1', passwordVariable: 'DH_PASS_PROJECT1')])
                {
                    sh '''
                        echo "$DH_PASS_PROJECT1"
                        echo "$DH_PASS_PROJECT1" | docker login -u "$DH_USER_PROJECT1" --password-stdin
                        docker push "$FULL_IMAGE_NAME"
                        docker push $IMAGE:latest
                    '''
                }
           }
           post
           {
                /*  The || means OR, execute the next command if the previous command fails. true is a Linux command that always succeeds (exit code 0).
                    For example:
                    Situation	            What happens
                    docker logout succeeds	true is not executed.
                    docker logout fails	    true executes and succeeds.
                */
                always { sh 'docker logout || true' }
           }
        }

        /*  You need the Approval stage only if you want manual approval before deploying to production.
            Since your Deploy stage already runs deploy.sh, move the approval step before deployment
        */
        stage('Approval')
        {
            when
            {
                allOf
                {
                    branch 'main' // branch — Jenkins condition that checks the branch being built.
                    expression { params.DEPLOY }
                    expression { params.ENV == 'prod' }
                }
            }
            steps
            {
                timeout(time: 5, unit: 'MINUTES')
                {
                    input message: 'Deploy to production?'
                }
            }
        }

        /*  Deployment means taking your Docker image and creating container from it and running the container on a machine where people can access your application.

            How Jenkins decides when conditions here?
            Branch	DEPLOY	Deploy stage
            main	true	Runs
            main	false	Skipped
            develop	true	Skipped
            develop	false	Skipped
        */
        stage('Deploy')
        {
            options { timeout(time: 10, unit: 'MINUTES') }

            when
            {
                allOf
                {
                    branch 'main' // branch — Jenkins condition that checks the branch being built.
                    expression { params.DEPLOY }
                }
            }
            steps
            {
                echo "Deploying to ${params.ENV}"
                echo "Deploying ${IMAGE}:${TAG} to ${TARGET}"

                // docker run -d --name project1_container -p 9090:8081 $IMAGE:$TAG Docker starts a container and executes: java -jar app.jar
                sh '''
                    # Remove the previous container.
                    # If it does not exist, continue without failing.
                    docker rm -f project1_container || true

                    # Create and start a container in the background.
                    # --name sets the container name.
                    # -p maps host port 8081 to container port 8081.
                    # $IMAGE:$TAG specifies the Docker image and tag.
                    docker run -d --name project1_container -p 9090:8081 $IMAGE:$TAG
                '''


                sh '''
                    set -eu

                    # Apply database changes first.
                    psql "postgresql://swasthikbhatt:Puttu@123@localhost:5432/project1" -v ON_ERROR_STOP=1 -f deploy.sql
                   '''
            }
        }
    }
    post
    {
        success
        {
            echo "Build ${env.BUILD_NUMBER} succeeded"
        }
        failure
        {
            echo "Build ${env.BUILD_NUMBER} failed"
        }
        always
        {
            // Basic E-mail Notification
            mail(
                    to: params.EMAIL,
                    subject: "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                    body: """Pipeline execution finished.

            BUILD DETAILS
            -------------
            Job Name     : ${env.JOB_NAME}
            Build Number : #${env.BUILD_NUMBER}
            Status       : ${currentBuild.currentResult}
            Environment  : ${params.ENV}

            BUILD LINKS
            -----------
            Build URL    : ${env.BUILD_URL}
            Console Log  : ${env.BUILD_URL}console
            Changes      : ${env.BUILD_URL}changes

            SOURCE CODE
            -----------
            GitHub URL   : ${env.GITHUB_URL}
            Commit ID    : ${env.COMMIT_ID}

            Please check Jenkins for details.""")

            // Editable Email Notification
            emailext(
                        to: params.EMAIL,
                        subject: "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                        body: """Hello,

            Your Jenkins pipeline execution has finished.

            BUILD DETAILS
            -------------
            Job Name     : ${env.JOB_NAME}
            Build Number : #${env.BUILD_NUMBER}
            Status       : ${currentBuild.currentResult}
            Environment  : ${params.ENV}

            BUILD LINKS
            -----------
            Build URL    : ${env.BUILD_URL}
            Console Log  : ${env.BUILD_URL}console
            Changes      : ${env.BUILD_URL}changes

            SOURCE CODE
            -----------
            GitHub URL   : ${env.GITHUB_URL}
            Commit ID    : ${env.COMMIT_ID}

            Please check Jenkins for details.""")

            // Record completion time
            sh '''
                    echo "BUILD_FINISHED_AT=$(date '+%Y-%m-%d %H:%M:%S %Z')" >> build-times.properties
               '''

            // Save artifacts in Jenkins
            archiveArtifacts(artifacts: 'target/*.jar,frontend/dist/**,build-times.properties', allowEmptyArchive: true,fingerprint: true)

            // remove old files and keep the workspace clean for the next build.
            // cleanWs()
        }
    }
}