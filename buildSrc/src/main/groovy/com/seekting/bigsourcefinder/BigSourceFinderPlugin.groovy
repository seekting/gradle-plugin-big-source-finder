package com.seekting.bigsourcefinder

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

class BigSourceFinderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        FindLogger.mProject = project
        if (project.plugins.hasPlugin(AppPlugin)) {
            //android-app

            FindLogger.info("这是android app工程!")

            def variants = project.android.applicationVariants as DomainObjectCollection<BaseVariant>
            applyAndroid(variants, project)

        } else if (project.plugins.hasPlugin(LibraryPlugin)) {
            //android-lib
            FindLogger.info("这是android lib工程!")
            def variants = project.android.libraryVariants as DomainObjectCollection<BaseVariant>
            applyAndroid(variants, project)
        } else {
            throw new IllegalArgumentException("只支持android项目或andriod lib工程")
        }


    }

    static void applyAndroid(DomainObjectCollection<BaseVariant> variants, Project project) {
        Config config = project.extensions.create("findbigsource", Config)
        variants.all { variant ->
            List<File> imgDirectories = []
            variant.sourceSets.each { sourceSet ->
                sourceSet.resDirectories.each { res ->
                    if (res.exists()) {
                        res.eachDir { dir ->
                            if (dir.directory && (dir.name.startsWith("drawable") || dir.name.startsWith("mipmap"))) {
                                FindLogger.info("find:$dir")
                                imgDirectories << dir
                            }

                        }
                    }
                }

            }

            if (!imgDirectories.isEmpty()) {
                String name = FindTask.TASK_NAME.concat(project.name.capitalize()).concat(variant.buildType.name.capitalize())
                FindLogger.info("taskName=$name")
                project.tasks.create(name, FindTask) { FindTask task ->
                    task.group = "find-big-source"
                    task.description = "find ${variant.buildType.name} big source"
                    task.imgDirs = imgDirectories

                    File f = variant.generateBuildConfig.getSourceOutputDir().getParentFile().getParentFile().getParentFile().getParentFile()

                    String dir = "${f.absolutePath}${File.separator}finder${File.separator}${variant.name}"
                    task.output = new File(dir, "${name}.txt")
                    if (config == null) {
                        config = Config.defaultConfig()
                    }
                    task.mConfig = config
                    FindLogger.info("findbigsource:${config}")

//                    task.doFirst {
//
//                    }

                }

            }
        }
    }
}

