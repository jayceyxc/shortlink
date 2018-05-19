package com.bcdata.shortlink.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * generate the short url
 *
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 May 18 16:39
 */
public class ShortUrlGenerator {

    private static final Random random = new Random();

    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer ();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append ("0");
            }

            hexStr.append (Integer.toHexString (num));
        }

        return hexStr.toString ();
    }

    public static String MD5(String key) {
        String md5String = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance ("MD5");
            md5.reset ();
            byte[] result = md5.digest (key.getBytes (Charset.forName ("UTF-8")));
            md5String = bytesToHex (result);
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace ();
        }

        return md5String;
    }

    /**
     * 1)将长网址md5生成32位签名串,分为4段, 每段8个字节;
     * 2)对这四段循环处理, 取8个字节, 将他看成16进制串与0x3fffffff(30位1)与操作, 即超过30位的忽略处理;
     * 3)这30位分成6段, 每5位的数字作为字母表的索引取得特定字符, 依次进行获得6位字符串;
     * 4)总的md5串可以获得4个6位串; 取里面的任意一个就可作为这个长url的短url地址;
     * 这种算法,虽然会生成4个,但是仍然存在重复几率
     * @param url 长链接网址
     * @return 短链接网址
     */
    public static String shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
//        String key = "mengdelong" ;
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"

        };
        // 对传入网址进行 MD5 加密
        // http://www.baeldung.com/java-md5
        String hex = DigestUtils.md5Hex (url);

        String[] resUrl = new String[4];
        for ( int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            StringBuilder outChars = new StringBuilder ();
            for ( int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars.append (chars[(int) index]);
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars.toString ();
        }
        return resUrl[random.nextInt (resUrl.length)];
    }

    public static void main (String[] args) {
        String test = "hello world";
        System.out.println (MD5(test));
        System.out.println (shortUrl (test));
    }
}
