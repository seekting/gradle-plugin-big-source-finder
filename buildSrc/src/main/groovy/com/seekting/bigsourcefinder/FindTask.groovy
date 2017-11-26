package com.seekting.bigsourcefinder

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FindTask extends DefaultTask {
    public static String TASK_NAME = "find"
    public List<File> imgDirs
    public Map<String, File> md5Map = new HashMap()
    Config mConfig

    @TaskAction
    def find() {
        md5Map.clear()

        System.out.println("find task begin!")
        int bigCount = 0
        int sameCount = 0
        imgDirs.forEach { dir ->
            if (dir.directory) {
                dir.eachFile { file ->
                    if (mConfig.maxSize <= file.length()) {
                        if (mConfig.ifHasAboveMaxSizeCrash) {
                            FindLogger.error("${file.absolutePath} is too big")
                        } else {
                            FindLogger.warn("${file.absolutePath} is too big")
                        }
                        bigCount++
                    }
                    if (mConfig.checkDuplicate) {
                        String md5 = Md5Utils.getMd5(file)
                        if (md5Map.containsKey(md5)) {
                            File theFile = md5Map.get(md5)

                            String from = "${file.getParentFile().name}${File.separator}${file.name}"
                            String to = "${theFile.getParentFile().name}${File.separator}${theFile.name}"
                            if (mConfig.ifHasDuplicateCrash) {
                                FindLogger.error("find same file:[${from},${to}]")
                            } else {
                                FindLogger.warn("find same file:[${from},${to}]")
                            }
                            sameCount++
                        } else {
                            md5Map.put(md5, file)
                        }
                    }
                }
            }


        }
        System.out.println("find task end!")
        if (bigCount > 0 && mConfig.ifHasAboveMaxSizeCrash) {
            throw new IllegalArgumentException("$bigCount source files is too big!")
        }
        if (sameCount > 0 && mConfig.ifHasDuplicateCrash) {
            throw new IllegalArgumentException("$sameCount source files duplicate!!")
        }
        md5Map.clear()
    }
}
