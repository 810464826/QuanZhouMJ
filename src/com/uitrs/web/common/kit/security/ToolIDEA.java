package com.uitrs.web.common.kit.security;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;

/**
 * IDEA安全编码组件
 * 
 * 国际数据加密标准---IDEA：完全是新突破，几乎同时和AES出现
 */
public class ToolIDEA {

	private static final String CONFIG_SECURITY_KEY_KEY = "securityKey";

	private static Log log = Log.getLog(ToolIDEA.class);

	/**
	 * 密钥算法
	 */
	public static final String KEY_ALGORITHM = "IDEA";

	/**
	 * 加密/解密算法 / 工作模式 / 填充方式
	 */
	public static final String CIPHER_ALGORITHM = "IDEA/ECB/PKCS5Padding";

	private static final String ENCODING = "UTF-8";

	static {
		// 加入BouncyCastleProvider支持
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		// 生成秘密密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);

		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);

		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 生成密钥 <br>
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		// 实例化
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

		// 初始化
		kg.init(128);

		// 生成秘密密钥
		SecretKey secretKey = kg.generateKey();

		// 获得密钥的二进制编码形式
		return secretKey.getEncoded();
	}

	/**
	 * 解密加密字符串
	 * 
	 * @param content
	 *            待加密的字符串
	 * @return 说明：增加Base64编码
	 */
	public static String decrypt(String content) {
		// 1. Base64解码cookie令牌
		try {
			content = decode(content);
		} catch (Exception e) {
			log.error("Base64解码异常：content = " + content);
			return null;
		}

		// 2. 解密cookie令牌
		byte[] securityByte = Base64.decodeBase64(content);

		String securityKey = PropKit.get(CONFIG_SECURITY_KEY_KEY);
		byte[] keyByte = Base64.decodeBase64(securityKey);

		byte[] dataByte = null;
		try {
			dataByte = decrypt(securityByte, keyByte);
		} catch (Exception e) {
			log.error("解密数据异常：content = " + content + "，securityKey = " + securityKey);
			return null;
		}
		String data = new String(dataByte);

		return data;
	}

	/**
	 * 生成加密字符串
	 * 
	 * @param content
	 *            待加密的字符串
	 * @return 说明：增加Base64编码
	 */
	public static String encrypt(String content) {
		byte[] authTokenByte = null;
		try {
			authTokenByte = content.getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			log.error("字符串数据转byte异常：content = " + content);
			return null;
		}
		String securityKey = PropKit.get(CONFIG_SECURITY_KEY_KEY);
		byte[] keyByte = Base64.decodeBase64(securityKey);

		// 认证cookie加密
		byte[] securityByte = null;
		try {
			securityByte = encrypt(authTokenByte, keyByte);
		} catch (Exception e) {
			log.error("加密数据异常：content = " + content + "，securityKey = " + securityKey);
			return null;
		}
		String securityCookie = Base64.encodeBase64String(securityByte);

		// 认证cookie Base64编码
		try {
			securityCookie = encode(securityCookie);
		} catch (Exception e) {
			log.error("数据Base64编码异常：content = " + content + "，securityKey = " + securityKey);
			return null;
		}

		return securityCookie;
	}

	// public static void main(String[] args) throws Exception {
	// String inputStr = "admin";
	// byte[] inputData = inputStr.getBytes();
	// System.err.println("原文:\t" + inputStr);
	//
	// // 初始化密钥
	//// byte[] key = initKey();
	// byte[] key = Base64.decodeBase64("tHLpY6C5TJcK7cFWuQT7oA==");
	// System.err.println("密钥:\t" + Base64.encodeBase64String(key));
	//
	// // 加密
	// inputData = encrypt(inputData, key);
	// System.err.println("加密后:\t" + Base64.encodeBase64String(inputData));
	//
	// // 解密
	// byte[] outputData = decrypt(inputData, key);
	//
	// String outputStr = new String(outputData);
	// System.err.println("解密后:\t" + outputStr);
	//// 46cbf59d3f34c643d4659bdf22c0ad71772a32f3846a6643956b8bbb
	// System.err.println("解密后2:\t" + LoginValidator.entryptPassword(inputStr));
	//// ZuviuwRJ+xY=
	//
	// }
	/**
	 * Url Base64编码
	 * 
	 * @param data
	 *            待编码数据
	 * @return String 编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {
		// 执行编码
		byte[] b = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}

	/**
	 * Url Base64解码
	 * 
	 * @param data
	 *            待解码数据
	 * @return String 解码数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {
		// 执行解码
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
		return new String(b, ENCODING);
	}
}
