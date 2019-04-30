package com.gcc.tagcc.untils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密工具
 * @author gaocc
 *
 */
public class CryptographyUtil {

	
	/**
	 * Md5加密
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
	
	public static void main(String[] args) {
		String password="1234";
		
		System.out.println("Md5加密："+CryptographyUtil.md5(password, "gcc"));
	}
}
