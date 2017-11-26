package com.seekting.bigsourcefinder

import org.gradle.api.Plugin
import org.gradle.api.Project

class BigSourceFinderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        project.afterEvaluate {

            println("hello!!!")


        }
        project.tasks.create("find"){task->
            task.group="find-big-source"
            task.doLast {
                println("hahahaha!")
            }

        }

    }
}
