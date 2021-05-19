package com.handsone.restAPI.infra;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
    private String fileName;

    public MD5Generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest mdMD5 = MessageDigest.getInstance("MD5");
        mdMD5.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] md5Hash = mdMD5.digest();
        StringBuilder hexMD5Hash = new StringBuilder();
        for (byte b : md5Hash) {
            String hexString = String.format("%02x", b);
            hexMD5Hash.append(hexString);
        }
        fileName = hexMD5Hash.toString();
    }

    public String toString() {
        return fileName;
    }
}
