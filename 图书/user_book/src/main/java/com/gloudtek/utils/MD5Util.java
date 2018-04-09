package com.gloudtek.utils;

import java.security.MessageDigest;

public class MD5Util {
	 /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String EncryptionMD5(String password){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = password.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        //去掉toUpperCase()就是小写加密
        return hexValue.toString().toUpperCase();  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
    // 测试主函数  
//    public static void main(String args[]) {  
//        String s = "123456";  
//        System.out.println("原始：" + s);  
//        System.out.println("MD5后：" + EncryptionMD5(s));  
//        System.out.println("加密的：" + convertMD5(s));  
//    }  
}  
