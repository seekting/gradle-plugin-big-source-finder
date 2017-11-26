package com.seekting.bigsourcefinder

import org.gradle.api.Project

class FindLogger {

    public static Project mProject

    public static void info(String msg) {
        mProject.logger.info(msg)
    }

    public static void warn(String msg) {
        mProject.logger.warn("warning:$msg")
    }

    public static void debug(String msg) {
        mProject.logger.debug(msg)
    }


    public static void error(String msg) {
        mProject.logger.error("error:$msg")
    }
}
