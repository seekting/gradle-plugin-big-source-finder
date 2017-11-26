package com.seekting.bigsourcefinder;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;


public class Md5Utils {
    public static String getMd5(File file) {
        if (!file.exists()) {
            return null;
        }
        InputStream is = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            is = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            return ByteConvertor.bytesToHexString(md5sum);
        } catch (Exception e) {
            return null;
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    // nothing to do
                }
            }
        }
    }
}
