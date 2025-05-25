import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.triggers.vcs

version = "2024.03"

project {
    buildType {
        id = AbsoluteId("DeployToProduction")
        name = "Deploy to Production"
        
        vcs {
            root(DslContext.settingsRoot)
            cleanCheckout = true
        }
        
        features {
            sshAgent {
                teamcitySshKey = "production-deploy-key"
            }
        }
        
        steps {
            script {
                name = "Build"
                scriptContent = """
                    npm install
                    npm run build
                    tar -czf build.tar.gz ./dist
                """
            }
            
            script {
                name = "Deploy"
                scriptContent = """
                    scp -o StrictHostKeyChecking=no build.tar.gz %env.SSH_USER%@147.45.184.71:/tmp/
                    
                    ssh -o StrictHostKeyChecking=no %env.SSH_USER%@147.45.184.71 "
                        sudo mkdir -p /var/www/html
                        sudo tar -xzf /tmp/build.tar.gz -C /var/www/html
                        sudo chown -R www-data:www-data /var/www/html
                        rm -f /tmp/build.tar.gz
                    "
                """
            }
        }
        
        triggers {
            vcs {
                branchFilter = "+:*"
            }
        }
        
        params {
            password("env.SSH_USER", "deploy-user", display = ParameterDisplay.HIDDEN)
            
            text("env.DEPLOY_PATH", "/var/www/html", display = ParameterDisplay.HIDDEN)
        }
    }
}
