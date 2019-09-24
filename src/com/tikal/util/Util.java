/**
 * 
 */
package com.tikal.util;

import java.security.MessageDigest;
import java.security.SecureRandom;



/**
 * @author Tikal
 *
 */
public class Util {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	public static String randomString( int len ){
		   StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
	}
	
	
	static String encripta(String cadena){
		String password = cadena;
		String algorithm = "SHA";
		
		byte[] plainText = password.getBytes();
		
		MessageDigest md = null;
		
		try {		
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		md.reset();		
		md.update(plainText);
		byte[] encodedPassword = md.digest();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				sb.append("0");
			}
			
			sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

//		System.out.println("Plain    : " + password);
//		System.out.println("Encrypted: " + sb.toString());
		return sb.toString();
	}

}