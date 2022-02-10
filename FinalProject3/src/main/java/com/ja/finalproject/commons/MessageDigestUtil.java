package com.ja.finalproject.commons;

import java.security.MessageDigest;

public class MessageDigestUtil {

	public static String getPasswordHashCode(String password) {
		
		password = password + "!@ja";
		
		String hashCode = null;
		
		// password를 암호화 한다..!! 해쉬브라운(맥도날드) 잘게 쪼개진 감자
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.reset();
			messageDigest.update(password.getBytes("UTF-8"));
			byte [] chars = messageDigest.digest(); // 해쉬값 생성...
			
			StringBuilder sb = new StringBuilder();
			
			for(int i=0; i<chars.length; i++) {
				String tmp = Integer.toHexString(chars[i] & 0xff);
				
				if(tmp.length() == 1) {
					sb.append("0");
				}
				
				sb.append(tmp);
				
				hashCode = sb.toString();
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return hashCode;
	}
	
}
