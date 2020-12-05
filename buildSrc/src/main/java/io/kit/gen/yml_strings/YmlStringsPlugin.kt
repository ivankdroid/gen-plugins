package io.kit.gen.yml_strings

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.accessors.runtime.extensionOf
import java.io.File

class YmlStringsPlugin : Plugin<Project> {

    @Suppress("DefaultLocale")
    override fun apply(target: Project) {
        target.extensions.create("ymlStrings", YmlStringsExtension::class.java)
        target.android.variants.all {
            val ext = target.extensions.findByName("ymlStrings") as YmlStringsExtension
            val taskName = "generateStrings${name.capitalize()}"
            val inputYmlFile = "${target.rootDir}/${ext.ymlFile}"
            val outputPath = "${target.buildDir}/generated/res"
            target.tasks.register(taskName, YmlStringsTask::class.java) {
                group = "AutoGen"
                val outputDir = File("$outputPath/${dirName}").apply { mkdir() }
                inputFile = File(inputYmlFile).apply { if (!exists()) throw GradleException("Localisation File $absolutePath not exists") }
                outputFile = File(outputDir, "values/gen_strings.xml")
                registerGeneratedResFolders(target.files(outputDir).builtBy(this))
            }
            target.afterEvaluate {
                tasks.named("preBuild").configure {
                    dependsOn(taskName)
                }
            }
        }
    }
}

