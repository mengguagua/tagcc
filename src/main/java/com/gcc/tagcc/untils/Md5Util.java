package com.gcc.tagcc.untils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    /**
     * 生成32位md5码
     * @param password
     * @return
     */
    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * MD5加密字符串（32位大写）
     * @param string 需要进行MD5加密的字符串
     * @return 加密后的字符串（大写）
     */
    public static String md5Encrypt32Upper(String string) {
        byte[] hash;
        try {
            //创建一个MD5算法对象，并获得MD5字节数组,16*8=128位
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        //转换为十六进制字符串
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toUpperCase();
    }

    /**
     * MD5加密字符串（32位小写）
     * @param string 需要进行MD5加密的字符串
     * @return 加密后的字符串（小写）
     */
    public static String md5Encrypt32Lower(String string) {
        //直接上面的方法转换成小写就可以了
        return md5Encrypt32Upper(string).toLowerCase();
    }

    public static void main(String[] args) {
        String password = Md5Util.md5Encrypt32Lower("123456");
        String finalPwd = Md5Util.md5Password(password);
        System.out.println(finalPwd);
    }
}
