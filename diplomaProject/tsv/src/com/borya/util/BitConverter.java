package com.borya.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BitConverter {
	
	/*
	 * 把短整型转换成字节数组
	 */
	public static byte[] shortToByteArray(short s) {
        byte[] shortBuf = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (shortBuf.length - 1 - i) * 8;
            shortBuf[i] = (byte) ((s >>> offset) & 0xff);
        }
        return shortBuf;
    }
	
	/*
	 * 把字节数组转换成短整型
	 */
    public static final int byteArrayToShort(byte[] b) {
        return (b[0] << 8)
                + (b[1] & 0xFF);
    }

    public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }

    public static final int byteArrayToInt(byte[] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
    }
    
    public static String getTlvString(byte[] b) throws IOException{
		String str=new String(b,"utf-8");
		if(str!=null)
			str=str.trim();
		return str;
	}
    
    /**
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
     int len = (hex.length() / 2);
     byte[] result = new byte[len];
     char[] achar = hex.toCharArray();
     for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
     }
     return result;
    }

    public static byte toByte(char c) {
	     byte b = (byte) "0123456789ABCDEF".indexOf(c);
	     if(b==-1)
	    	b = (byte) "0123456789abcdef".indexOf(c);
	     return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
	     StringBuffer sb = new StringBuffer(bArray.length);
	     String sTemp;
	     for (int i = 0; i < bArray.length; i++) {
	      sTemp = Integer.toHexString(0xFF & bArray[i]);
	      if (sTemp.length() < 2)
	       sb.append(0);
	      sb.append(sTemp.toUpperCase());
	     }
	     return sb.toString();
    }

    /**
     * 把字节数组转换为对象
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
     ByteArrayInputStream in = new ByteArrayInputStream(bytes);
     ObjectInputStream oi = new ObjectInputStream(in);
     Object o = oi.readObject();
     oi.close();
     return o;
    }

    /**
     * 把可序列化对象转换成字节数组
     * @param s
     * @return
     * @throws IOException
     */
    public static final byte[] objectToBytes(Serializable s) throws IOException {
     ByteArrayOutputStream out = new ByteArrayOutputStream();
     ObjectOutputStream ot = new ObjectOutputStream(out);
     ot.writeObject(s);
     ot.flush();
     ot.close();
     return out.toByteArray();
    }
    
    public static final String objectToHexString(Serializable s) throws IOException{
     return bytesToHexString(objectToBytes(s));
    }
    
    public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException{
     return bytesToObject(hexStringToByte(hex));
    }
  
}
