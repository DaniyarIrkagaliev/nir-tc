import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.csharpScript

object Build : BuildType({
    name = "Build"
    // ...
    steps {
        csharpScript {
            id = "csharpScript"
            content = """Console.WriteLine("Running a CSharp script...");"""
            tool = "%\teamcity.tool.TeamCity.csi.DEFAULT%"
        }
    }
    // ...
})
