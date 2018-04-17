package com.seekting.bigsourcefinder;

public class BuildGenerate {
    public File file
    public StringBuilder sb = new StringBuilder()

    public BuildGenerate(File file) {
        this.file = file
    }

    public void info(String msg) {
        sb.append("$msg\n")
    }

    public void warn(String msg) {
        sb.append("warning:$msg\n")
    }

    public void debug(String msg) {
        sb.append("$msg\n")
    }


    public void error(String msg) {
        sb.append("error:$msg\n")
    }

    public void save() {

        file.write(sb.toString())

    }
}
