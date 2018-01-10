package com.tsv.util.encrypt;

import java.security.MessageDigest;


/**
 *
 * @author ellison
 */
public class EncryptUtil {

//    private static String tokenKey="A3LL91HH";
//    private static String pwdKey="^#&#(*#&&#";
    
    public static byte[] MD5(byte[] data) {
        byte[] ret = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // This replaces the existing hash, it does not add to it
            digest.reset();
            ret = digest.digest(data);
        } catch (Exception ex) {
            System.out.println("Exception doing md5"+ ex);
            ret = null;
        }

        return ret;
    }
    
    public static byte[] SHA1(byte[] data) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] ret = digest.digest(data);
            return ret;
        } catch(Exception ex) {
            System.out.println("Exception doing sha-1"+ ex);
        }
        return null;
    }

    public static byte[] getUTF8Bytes(String s) {
        if(null == s) {
            System.out.println("str is null!");
            return new byte[0];
        }
        try{
            byte[] ret = s.getBytes("UTF-8");
            return ret;
        }catch(Exception ex) {
        	 System.out.println("Exception, "+ ex);
        }
        System.out.println("System not support UTF-8, using default encoding to get bytes");
        return s.getBytes();
    }
    
    public static String getStrOrNull(String str) {
        if(null == str || str.length() < 1) {
            return null;
        }
        return str;
    }
    
    
//    public static String decrypt(String token){
//    	PassportCoder crypt = new PassportCoder(tokenKey);
//    	return crypt.decrypt(token);
//    }
    
//    public static String decryptPassword(String password) throws Exception{
//    	TransEncrypt crypt = new TransEncrypt(pwdKey);
//    	return crypt.decrypt(password);
//    	
//    }
    
//    public static String encryptToken(String username,String password){
//    	PassportCoder crypt = new PassportCoder(tokenKey);
//    	return crypt.encrypt(username+","+password);
//    }
    
//    public static String encryptToken(String password){
//    	PassportCoder crypt = new PassportCoder(tokenKey);
//    	return crypt.encrypt(password);
//    }
    
    
    public static String decrypt(String key,String value){
    	PassportCoder crypt = new PassportCoder(key);
    	return crypt.decrypt(value);
    }
    
    public static String encrypt(String key,String value){
    	PassportCoder crypt = new PassportCoder(key);
    	return crypt.encrypt(value);
    }
    
    public  static void main(String args[]){
//    	String str=EncryptUtil.decrypt("GWv4To05Mn6r1AgDDMNmmKYFLJynlK-A");
//    	System.out.println(str);
//    	LoginCertificate lo=(LoginCertificate)JSONUtils.JSONToBean(str, LoginCertificate.class);
//    	System.out.println(lo.getUserName());
    }
    
}
