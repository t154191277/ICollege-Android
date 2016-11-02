package wmlove.icollege.factory;

import android.util.Log;

import java.io.FileInputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Des加密
 * Created by wmlove on 2016/10/16.
 */
public class DESFactory {

    /**
     * 私钥，必须为8的倍数
     */

    private static String PRIVATE_KEY = "wmlove00";

    /**
     * 加密字符串
     * @param data 要加密的字符串
     * @throws Exception
     */

    public static String encrypt(String data) throws Exception{
        data = toUpper(data);
        DESKeySpec desKeySpec = new DESKeySpec(PRIVATE_KEY.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(PRIVATE_KEY.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] b = cipher.doFinal(data.getBytes("UTF-8"));
        return toHexString(b);
    }
    /**
     * 对已加密的文件进行解密
     * @param data 已加密的文件
     */
    public static String decrypt(String data) throws Exception{
        byte[] bytesrc = convertHexString(data);
        DESKeySpec desKeySpec = new DESKeySpec(PRIVATE_KEY.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(PRIVATE_KEY.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return coverUpper(new String(retByte));
    }


    private static String toUpper(String str)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < str.length(); i++)
        {
            char chr= str.charAt(i);
//            if(Character.isUpperCase(chr))
//            {
//                sb.append("u");
//            }
//            else if(Character.isLowerCase(chr))
//            {
//                sb.append("l");
//            }
            sb.append(chr);
        }
        Log.i("toUpper",sb.toString());
        return sb.toString();
    }

    private static String coverUpper(String str)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < str.length(); i++)
        {
            Character chr= str.charAt(i);
            if(chr.equals("u"))
            {
                i++;
                chr= str.charAt(i);
            }
            else if(chr.equals("l"))
            {
                i++;
                chr= str.charAt(i);
//                chr = (char)(chr-32);
            }
            sb.append(chr);
        }
        Log.i("coverUpper",sb.toString());
        return sb.toString();
    }


    /**
     * 解析16进制数据
     * @param ss 16进制数据
     * @return
     */
    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    /**
     * 将byte数组转换为16进制
     * @param b 要转换的byte数组
     * @return
     */
    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }

        return hexString.toString();
    }
}
