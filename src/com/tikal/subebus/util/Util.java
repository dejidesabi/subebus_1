/**
 * 
 */
package com.tikal.subebus.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;



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
	
	
	public static String encripta(String cadena){
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

	
	public static byte[] generate(String code) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			String aux= code.replace(":", "%3A");
			aux=aux.replace("/", "%2F");
			aux= aux.replace("?", "%3F");
			aux= aux.replace("&", "%26");
			aux= aux.replace("=", "%3D");
			String url= "https://chart.googleapis.com/chart?chs=500x500&cht=qr&chl="+aux+"%2F&choe=UTF-8";
			Image imgLogo;
	//		if (imagen != null) {
			
			imgLogo = Image.getInstance(new URL(url));
//			imgLogo.setScaleToFitHeight(false);
//			imgLogo.scaleToFit(125F, 37.25F);
			
			byte[] bytes= imgLogo.getRawData();
			bytes= url.getBytes();
//			BarcodeQRCode barcodeQRCode = new BarcodeQRCode(url, 1000, 1000, null);
//			Image codeQrImage = barcodeQRCode.getImage();
//			return codeQrImage.getRawData();
			return bytes;
			} catch (BadElementException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
//		} // else {
		return null;
	}
	
}