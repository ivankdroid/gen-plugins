package io.kit.gen.yml_strings

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.Yaml
import java.io.File

open class YmlStringsTask : DefaultTask() {

    @get:InputFile
    lateinit var inputFile: File

    @get:OutputFile
    lateinit var outputFile: File

    @TaskAction
    fun generate() {
        println("Input File: ${inputFile.absolutePath}")
        println("Output File: ${outputFile.absolutePath}")
        val yml = Yaml().loadAs(inputFile.inputStream(), Localisation::class.java)
        outputFile.writeXml {
            yml.screens.forEach { (screen, res) ->
                comment("Generated resources for $screen")
                res.strings.forEach { (name, value) ->
                    string("${screen}_${name}", value)
                }
                res.plurals.forEach { (name, value) ->
                    plural("${screen}_${name}", value.zero, value.one, value.two, value.few, value.many, value.other)
                }
                res.arrays.forEach { (name, values) ->
                    array("${screen}_${name}", values)
                }
            }
        }
    }
}