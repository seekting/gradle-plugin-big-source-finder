package com.seekting.bigsourcefinder

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class FindTask extends DefaultTask {
    public static String TASK_NAME = "find"
    public List<File> imgDirs
    public Map<String, File> md5Map = new HashMap()
    Config mConfig
    public File output
    BuildGenerate buildGenerate;

    @Input
    public String getConfigStr() {
        return mConfig.toString();
    }

    @OutputFile
    public File getTargetFile() {
        return output
    }

    @InputFiles
    public List<File> getImgDirs() {
        return imgDirs
    }

    @TaskAction
    def find() {
        buildGenerate = new BuildGenerate(output)
        md5Map.clear()
        FindLogger.info("outputbuildGenerate:$output")


        def time = System.currentTimeMillis() + ""
        buildGenerate.info("time=$time")
        System.out.println("find task begin!!!")
        int bigCount = 0
        int sameCount = 0
        imgDirs.forEach { dir ->
            if (dir.directory) {
                dir.eachFile { file ->
                    if (mConfig.maxSize <= file.length()) {
                        if (mConfig.ifHasAboveMaxSizeCrash) {
                            FindLogger.error("${file.absolutePath} is too big")
                            buildGenerate.error("${file.absolutePath} is too big")
                        } else {
                            FindLogger.warn("${file.absolutePath} is too big")
                            buildGenerate.warn("${file.absolutePath} is too big")
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
                                buildGenerate.error("find same file:[${from},${to}]")

                            } else {
                                FindLogger.warn("find same file:[${from},${to}]")
                                buildGenerate.warn("find same file:[${from},${to}]")
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
            buildGenerate.error("$bigCount source files is too big!")
            buildGenerate.save()
            throw new IllegalArgumentException("$bigCount source files is too big!")
        }
        if (sameCount > 0 && mConfig.ifHasDuplicateCrash) {
            buildGenerate.error("$sameCount source files duplicate!!")
            buildGenerate.save()
            throw new IllegalArgumentException("$sameCount source files duplicate!!")
        }
        buildGenerate.save()
        md5Map.clear()
    }
}
