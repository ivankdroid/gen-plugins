package io.kit.gen.yml_strings

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File
import java.io.OutputStream

val Project.android: BaseExtension
    get() =
        project.extensions.findByType(BaseExtension::class.java)
            ?: throw GradleException("Project $name is not an Android project")

val BaseExtension.variants: DomainObjectSet<out BaseVariant>
    get() = when (this) {
        is AppExtension -> applicationVariants
        is LibraryExtension -> libraryVariants
        else -> throw GradleException("Unsupported BaseExtension type!")
    }

private fun OutputStream.writeLine(string: String) {
    write(string.toByteArray())
    write("\n".toByteArray())
}

fun File.writeXml(block: ResourceWriter.() -> Unit) {
    outputStream().use { out ->
        out.writeLine("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        out.writeLine("<resources>")
        ResourceWriter(out).block()
        out.writeLine("</resources>")
    }
}

class ResourceWriter(private val out: OutputStream) {

    fun comment(comment: String) {
        out.writeLine("\t<!--\t${comment}\t-->")
    }

    fun string(name: String, value: String) {
        out.writeLine("\t<string name=\"${name}\">${value}</string>")
    }

    fun plural(name: String, zero: String?, one: String?, two: String?, few: String?, many: String?, other: String?) {
        out.writeLine("\t<plurals name=\"${name}\">")
        zero?.let { out.writeLine("\t\t<item quantity=\"zero\">${zero}</item>") }
        one?.let { out.writeLine("\t\t<item quantity=\"one\">${one}</item>") }
        two?.let { out.writeLine("\t\t<item quantity=\"two\">${two}</item>") }
        few?.let { out.writeLine("\t\t<item quantity=\"few\">${few}</item>") }
        many?.let { out.writeLine("\t\t<item quantity=\"many\">${many}</item>") }
        other?.let { out.writeLine("\t\t<item quantity=\"other\">${other}</item>") }
        out.writeLine("\t</plurals>")
    }

    fun array(name: String, values: List<String>) {
        out.writeLine("\t<string-array name=\"${name}\">")
        values.forEach { value -> out.writeLine("\t\t<item>${value}</item>") }
        out.writeLine("\t</string-array>")
    }
}

