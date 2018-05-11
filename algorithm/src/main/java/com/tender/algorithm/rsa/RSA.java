package com.tender.algorithm.rsa;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.Cipher;

/**
 * Created by boyu on 2018/5/10.
 * RSA加密算法
 * 公开密钥密码体制：使用不同的加密密钥和解密密钥，是一种“由已知加密密钥推导出解密密钥在计算上是不可行的”密码体制。
 * 公钥PK、加密算法E和解密算法D是公开信息，私钥SK是保密的。RSA密钥推荐长度为1024位。
 * RSA算法涉及的3个参数：n、e1、e2。n是最大质数q、p的乘积；n是密钥的长度，e1、e2是一对相关值，e1可以任意取；要求e1与(q-1)*(p-1)互质；再选择e2，要求(e2*e1)mod((q-1)*(p-1))=1。其中(n,e1),(n,e2)就是密钥对。
 * 加密算法和解密算法是一致的，设A为明文，B为密文，则：A=B^e2 mod n；B=A^e1 mod n。
 * e1和e2可以互相使用，即：A=B^e1 mod n；B=A^e2 mod n。
 */

public class RSA {
    private static final String RSA_ALGORITHM = "RSA";
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 创建密钥对
     * @param keySize
     * @return
     */
    public static Map<String, String> createKeys(int keySize) {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
        generator.initialize(keySize);//初始化KeyPairGenerator对象,密钥长度
        KeyPair pair = generator.generateKeyPair();//生成密匙对
        Key publicKey = pair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        Key privateKey = pair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("publicKey", publicKeyStr);
        keyMap.put("privateKey", privateKeyStr);
        return keyMap;
    }

    /**
     * 得到公钥
     * 通过X509编码的Key指令获得公钥对象
     * @param publicKeyStr 经过Base64编码的公钥字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey getPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        return (RSAPublicKey)factory.generatePublic(spec);
    }

    /**
     * 得到私钥
     * 通过PKCS#8编码的Key指令获得私钥对象
     * @param privateKey 经过Base64编码的私钥字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey getPrivateKey(String privateKey)  throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(
                    cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET_UTF8), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时出现异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时发生异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(
                    cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET_UTF8), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时出现异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时发生异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opMode, byte[] data, int keySize) {
        int maxBlock = 0;
        if (opMode == Cipher.ENCRYPT_MODE) {
            maxBlock = keySize / 8 - 11;
        } else {
            maxBlock = keySize / 8;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buffer;
        int i = 0;
        try {
            while (data.length > offSet) {
                if (data.length - offSet > maxBlock) {
                    buffer = cipher.doFinal(data, offSet, maxBlock);
                } else {
                    buffer = cipher.doFinal(data, offSet, data.length - offSet);
                }
                outputStream.write(buffer, 0, buffer.length);
                i ++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] result = outputStream.toByteArray();
        IOUtils.closeQuietly(outputStream);
        return result;
    }

    public static void main(String[] args) throws Exception {
       Map<String, String> keyMap = RSA.createKeys(1024);
       String publicKey = keyMap.get("publicKey");
       String privateKey = keyMap.get("privateKey");
       System.out.println("【RSA加密】");
       System.out.println("公钥：" + publicKey);
       System.out.println("私钥：" + privateKey);
       System.out.print("请输入明文：");
       String originalData;
       Scanner scanner = new Scanner(System.in);
       originalData = scanner.nextLine();
       String publicEncryptData = RSA.publicEncrypt(originalData, RSA.getPublicKey(publicKey));
       System.out.println("明文用公钥加密后得到密文为：" + publicEncryptData);
       String privateDecodeData = RSA.privateDecrypt(publicEncryptData, RSA.getPrivateKey(privateKey));
       System.out.println("密文用私钥解密后得到明文为：" + privateDecodeData);
       String privateEncryptData = RSA.publicEncrypt(privateDecodeData, RSA.getPublicKey(publicKey));
       System.out.println("明文用私钥加密后得到密文为：" + privateEncryptData);
       String publicDecodeData = RSA.privateDecrypt(privateEncryptData, RSA.getPrivateKey(privateKey));
       System.out.println("密文用公钥钥解密后得到明文为：" + publicDecodeData);
    }
}
