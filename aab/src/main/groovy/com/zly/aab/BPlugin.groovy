package com.zly.aab

import org.gradle.api.Plugin
import org.gradle.api.Project

class BPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("==========> BPlugin")
    }
}