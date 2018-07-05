package com.bridgeit.fundoonotes.user.tokengenerator;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenDecoder 
{
	private static String token = "ABCFGHC";
	public static int parseJWT(String jwt) {
		
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(token))
	       .parseClaimsJws(jwt).getBody();
	    System.out.println("ID: " + claims.getId());
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Issuer: " + claims.getIssuer());
	    System.out.println("Expiration: " + claims.getExpiration());
		return (int) Long.parseLong(claims.getId());
	}

	
}
