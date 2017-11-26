package com.seekting.bigsourcefinder

class Config {
    int maxSize = 1 * 1024
    boolean ifHasAboveMaxSizeCrash = true
    boolean checkDuplicate = true
    boolean ifHasDuplicateCrash = true

    public static Config defaultConfig() {
        Config config = new Config()
        return config
    }


}
