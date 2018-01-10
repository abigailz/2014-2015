package com.borya.util.encrypt;

import java.security.MessageDigest;
import org.apache.log4j.Logger;

import com.borya.util.prop.SystemConfig;


/**
 *
 * @author ellison
 */
public class EncryptUtil {

	private static Logger log = Logger.getLogger(EncryptUtil.class);
    private static String key = "A3LL91HH";
    
    static{
    	key = SystemConfig.getInstance().getProperty("encrypte.key");
    }
    
    public static byte[] MD5(byte[] data) {
        byte[] ret = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // This replaces the existing hash, it does not add to it
            digest.reset();
            ret = digest.digest(data);
        } catch (Exception ex) {
            log.error("Exception doing md5", ex);
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
            log.error("Exception doing sha-1", ex);
        }
	return null;
    }

    public static byte[] getUTF8Bytes(String s) {
        if(null == s) {
            log.error("str is null!");
            return new byte[0];
        }
        try{
            byte[] ret = s.getBytes("UTF-8");
            return ret;
        }catch(Exception ex) {
            log.error("Exception, ", ex);
        }
        log.error("System not support UTF-8, using default encoding to get bytes");
        return s.getBytes();
    }
    
    public static String getStrOrNull(String str) {
        if(null == str || str.length() < 1) {
            return null;
        }
        return str;
    }
    
    public static String decrypt(String value){
    	return decrypt(key,value);
    }
    
    public static String decrypt(String key,String value){
    	PassportCoder crypt = new PassportCoder(key);
    	return crypt.decrypt(value);
    }
    
    
    public static String encrypt(String value){
    	return encrypt(key,value);
    }
    
    public static String encrypt(String key,String value){
    	PassportCoder crypt = new PassportCoder(key);
    	return crypt.encrypt(value);
    }
    
}