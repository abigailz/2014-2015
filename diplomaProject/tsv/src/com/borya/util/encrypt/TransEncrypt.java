package com.borya.util.encrypt;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

import com.borya.util.Base64;

/**
 * Provide Encrypt algorithm between GUM Server and Client.
 * Using 3DES
 * @author ellison
 */
public class TransEncrypt {
	
	private static Logger log = Logger.getLogger(TransEncrypt.class);
    
    private static final String ALGORITHM = "DESede";
    /**
     * Encrypt key, used to generated m_bKey
     */
    private String m_strKey = "";

    /**
     * 3DES using 124bit－－192bit encrypt, key length is 16 - 24bytes
     * key is 16x8 = 128bit -- generated by SHA-1(m_strKey) -> will generate 20x8, get first 16x8
     * iv is 8x8 = 64bit -- generated by MD5(m_strKey) -> will gererate 16x8, get first 8x8
     * m_bKey[key,iv]
     */
    private byte[] m_bKey = new byte[24];
    SecretKey m_desKey = null;
    
    
    /**
     * 
     * @param key 密钥
     * @throws java.lang.Exception
     */
    public TransEncrypt(String key) throws Exception {
        if(null == key || key.length() < 1) {
            throw new IllegalArgumentException("key is Invalid(should not be null)! key="+key);
        }
        m_strKey = key;
        initKey();
    }

    /**
     * Init iv and key
     */
    private void initKey() throws Exception {
        byte[] bts = EncryptUtil.getUTF8Bytes(m_strKey);
        byte[] sha1 = EncryptUtil.SHA1(bts);
        byte[] md5 = EncryptUtil.MD5(bts);
        System.arraycopy(sha1, 0, m_bKey, 0, 16);
        System.arraycopy(md5, 0, m_bKey, 16, 8);
        m_desKey = new SecretKeySpec(m_bKey, ALGORITHM);
        log.debug("TransEncrypt created -- strKey: "+m_strKey +", bts: "+Arrays.toString(bts)+", sha1: "+Arrays.toString(sha1)+", md5: "+Arrays.toString(md5)+", key:"+Arrays.toString(m_bKey)+", desKey: "+m_desKey);
    }

    /**
     * 加密
     * Encrypt
     * @param strData
     * @return Base64Encoded Encoded string
     * null if failed
     */
    public String encrypt(String strData) {
        if(null == strData || strData.length() < 1) {
            log.error("strData is empty!");
            return null;
        }
        byte[] data = EncryptUtil.getUTF8Bytes(strData);
        try{
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, m_desKey);
            byte[] ret = c1.doFinal(data);
            
            return new String(Base64.encode(ret));
        } catch(Exception ex) {
            log.error("Exception", ex);
        }
        return null;
    }
    
    /**
     * 解密
     * @param strEncData Base64 encoded encrypted data
     * @return original string,
     * null if failed
     */
    public String decrypt(String strEncData) {
        if(null == strEncData || strEncData.length() < 1) {
            log.error("strEncData is empty!");
            return null;
        }
        
        byte[] data = Base64.decode(strEncData);
        
        try{
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, m_desKey);
            byte[] ret = c1.doFinal(data);
            return new String(ret, "UTF-8");
        }catch(Exception ex) {
            log.error("Exception", ex);
        }
        
        return null;
    }
    
    public static final void main(String[] argv) {
        try{
            TransEncrypt te = new TransEncrypt("123456");
            String enc = te.encrypt("95258");
            System.out.println("enc:"+enc);
            String dec = te.decrypt(enc);
            
            System.out.println("dec:"+dec);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
