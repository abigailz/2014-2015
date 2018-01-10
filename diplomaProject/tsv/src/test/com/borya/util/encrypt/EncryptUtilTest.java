package test.com.borya.util.encrypt;

import com.borya.util.encrypt.EncryptUtil;

public class EncryptUtilTest {

	public void test_Encrypt(){
		
    	String str = EncryptUtil.decrypt("test");
    	System.out.println(str);
    	
    	System.out.println(EncryptUtil.decrypt(str));
    }
}
