package com.borya.util.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @(#)PassportCoder.java
 * 
 * 
 * @author
 * @version 1.00 2011/8/25
 */
public class PassportCoder {

    // Crypt Key
    private byte[] m_aDesKey;

    public PassportCoder(String desKey) {
        this.m_aDesKey = desKey.getBytes();
    }

    /**
     * DES Encoder
     * 
     * @param plainText
     * @return
     * @throws Exception
     */
    public byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = this.m_aDesKey;

        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);

        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

    /**
     * DES Decoder
     * 
     * @param encryptText
     * @return
     * @throws Exception
     */
    public byte[] desDecrypt(byte[] encryptText) throws Exception {

        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = this.m_aDesKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DES");

        cipher.init(Cipher.DECRYPT_MODE, key, sr);

        byte encryptedData[] = encryptText;
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }

    /**
     * Cookie Encoder
     * 
     * @param input
     * @return
     * @throws Exception
     */
    public String encrypt(String input) {
        if (null == input || input.length() < 1)
            return input;
        try {
            char[] result = mBase64.encode(desEncrypt(input.getBytes()));
            return new String(result);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Cookie Decoder
     * 
     * @param input
     * @return
     * @throws Exception
     */
    public String decrypt(String input) {
        if (null == input || input.length() < 1)
            return input;

        try {
            byte[] result = mBase64.decode(input);

            return new String(desDecrypt(result));
        } catch (Throwable t) {
            // t.printStackTrace();
        }
        return null;
    }

    private static class mBase64 {
        // Mapping table from 6-bit nibbles to Base64 characters.

        private static char[] map1 = new char[64];

        static {
            int i = 0;
            for (char c = 'A'; c <= 'Z'; c++) {
                map1[i++] = c;
            }
            for (char c = 'a'; c <= 'z'; c++) {
                map1[i++] = c;
            }
            for (char c = '0'; c <= '9'; c++) {
                map1[i++] = c;
            }
            map1[i++] = '-';
            map1[i++] = '_';
        }

        // Mapping table from Base64 characters to 6-bit nibbles.
        private static byte[] map2 = new byte[128];

        static {
            for (int i = 0; i < map2.length; i++) {
                map2[i] = -1;
            }
            for (int i = 0; i < 64; i++) {
                map2[map1[i]] = (byte) i;
            }
        }

        /**
         * Encodes a byte array into Base64 format. No blanks or line breaks are
         * inserted.
         * 
         * @param in
         *            an array containing the data bytes to be encoded.
         * @return A character array with the Base64 encoded data.
         */
        public static char[] encode(byte[] in) {
            return encode(in, in.length);
        }

        /**
         * Encodes a byte array into Base64 format. No blanks or line breaks are
         * inserted.
         * 
         * @param in
         *            an array containing the data bytes to be encoded.
         * @param iLen
         *            number of bytes to process in <code>in</code>.
         * @return A character array with the Base64 encoded data.
         */
        public static char[] encode(byte[] in, int iLen) {
            int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
            int oLen = ((iLen + 2) / 3) * 4; // output length including padding
            char[] out = new char[oLen];
            int ip = 0;
            int op = 0;
            while (ip < iLen) {
                int i0 = in[ip++] & 0xff;
                int i1 = ip < iLen ? in[ip++] & 0xff : 0;
                int i2 = ip < iLen ? in[ip++] & 0xff : 0;
                int o0 = i0 >>> 2;
                int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
                int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
                int o3 = i2 & 0x3F;
                out[op++] = map1[o0];
                out[op++] = map1[o1];
                out[op] = op < oDataLen ? map1[o2] : '*';
                op++;
                out[op] = op < oDataLen ? map1[o3] : '*';
                op++;
            }
            return out;
        }

        /**
         * Decodes a byte array from Base64 format.
         * 
         * @param s
         *            a Base64 String to be decoded.
         * @return An array containing the decoded data bytes.
         * @throws IllegalArgumentException
         *             if the input is not valid Base64 encoded data.
         */
        public static byte[] decode(String s) {
            return decode(s.toCharArray());
        }

        /**
         * Decodes a byte array from Base64 format. No blanks or line breaks are
         * allowed within the Base64 encoded data.
         * 
         * @param in
         *            a character array containing the Base64 encoded data.
         * @return An array containing the decoded data bytes.
         * @throws IllegalArgumentException
         *             if the input is not valid Base64 encoded data.
         */
        public static byte[] decode(char[] in) {
            int iLen = in.length;
            if (iLen % 4 != 0) {
                throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
            }
            while (iLen > 0 && in[iLen - 1] == '*') {
                iLen--;
            }
            int oLen = (iLen * 3) / 4;
            byte[] out = new byte[oLen];
            int ip = 0;
            int op = 0;
            while (ip < iLen) {
                int i0 = in[ip++];
                int i1 = in[ip++];
                int i2 = ip < iLen ? in[ip++] : 'A';
                int i3 = ip < iLen ? in[ip++] : 'A';
                if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                int b0 = map2[i0];
                int b1 = map2[i1];
                int b2 = map2[i2];
                int b3 = map2[i3];
                if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0) {
                    throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
                }
                int o0 = (b0 << 2) | (b1 >>> 4);
                int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
                int o2 = ((b2 & 3) << 6) | b3;
                out[op++] = (byte) o0;
                if (op < oLen) {
                    out[op++] = (byte) o1;
                }
                if (op < oLen) {
                    out[op++] = (byte) o2;
                }
            }
            return out;
        }
    }

    public static void main(String[] args) throws Exception {
        String key = "$#$#";
        String token = "by1,123";

      /*  System.out.println("" + Integer.MAX_VALUE + "=" + Integer.toString(Integer.MAX_VALUE, Character.MAX_RADIX)
                + ",radix:" + Character.MAX_RADIX);
        System.out.println("" + Integer.MAX_VALUE + "="
                + Integer.parseInt(Integer.toString(Integer.MAX_VALUE, Character.MAX_RADIX), Character.MAX_RADIX)
                + ",radix:" + Character.MAX_RADIX);*/
        PassportCoder crypt = new PassportCoder(key);

      /*  long now = System.currentTimeMillis();
        crypt.encrypt("xyz");
        System.out.println("ts: " + (System.currentTimeMillis() - now));
        now = System.currentTimeMillis();
*/
        System.out.println("Encode:" + crypt.encrypt(token));
        //System.out.println("ts: " + (System.currentTimeMillis() - now));
        //now = System.currentTimeMillis();
        System.out.println("Decode:" + crypt.decrypt(crypt.encrypt(token)));
        //System.out.println("ts: " + (System.currentTimeMillis() - now));
    }
}