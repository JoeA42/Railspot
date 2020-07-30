package com.btp.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * this class is in charge of taking a string and encrypting it using MD5 hashing
 */
public class HashPassword {

    /**
     * this is the method in charge of MD5 encryption
     * @param password string representing the password that wants to be hashed
     * @return a string that represents the encrypted password
     * @throws NoSuchAlgorithmException exception needs to be thrown
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuilder sb = new StringBuilder();
        for(byte b1 : b){
            sb.append(Integer.toHexString(b1 & 0xff));
        }
        return sb.toString();
    }
}
