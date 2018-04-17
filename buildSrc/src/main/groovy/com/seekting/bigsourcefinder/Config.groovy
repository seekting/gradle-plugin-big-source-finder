package com.seekting.bigsourcefinder
class Config implements java.io.Serializable{
    private static final long serialVersionUID = -1L;
    int maxSize = 1 * 1024
    boolean ifHasAboveMaxSizeCrash = true
    boolean checkDuplicate = true
    boolean ifHasDuplicateCrash = true

    public static Config defaultConfig() {
        Config config = new Config()
        return config
    }


    @Override
    public String toString() {
        return "Config{" +
                "maxSize=" + maxSize +
                ", ifHasAboveMaxSizeCrash=" + ifHasAboveMaxSizeCrash +
                ", checkDuplicate=" + checkDuplicate +
                ", ifHasDuplicateCrash=" + ifHasDuplicateCrash +
                '}';
    }
}
