package me.him188.kotlin.dynamic.delegation

import example.kotlin.compiler.plugin.template.compiler.PluginCommandLineProcessor
import me.him188.kotlin.dynamic.delegation.build.BuildConfig
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption


internal fun MyExtension.toSubpluginOptionList(): List<SubpluginOption> {
    return listOf(
//        SubpluginOption(PluginCompilerConfig3urationKeys.EXAMPLE.name, ),
    )
}

private val pluginArtifact = SubpluginArtifact(
    groupId = BuildConfig.GROUP_ID,
    artifactId = BuildConfig.ARTIFACT_ID_EMBEDDABLE,
    version = BuildConfig.VERSION
)

@Suppress("unused") // used in build.gradle.kts
open class CompilerGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun apply(target: Project) {
//        target.extensions.create("myExtension", MyExtension::class.java)
    }

    override fun getCompilerPluginId(): String = PluginCommandLineProcessor.COMPILER_PLUGIN_ID

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        val ext: MyExtension? =
            project.extensions.findByType(MyExtension::class.java)
        return project.provider {
            mutableListOf<SubpluginOption>()
            ext?.toSubpluginOptionList() ?: emptyList()
        }
    }

    override fun getPluginArtifact(): SubpluginArtifact = pluginArtifact

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        TODO()

//        return kotlinCompilation.target.project.plugins.hasPlugin(CompilerGradlePlugin::class.java)
//                && when (kotlinCompilation.platformType) {
//            KotlinPlatformType.jvm,
//            KotlinPlatformType.androidJvm,
//            KotlinPlatformType.common,
//            -> true
//            else -> false
//        }
    }
}