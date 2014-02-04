package test.General;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Security {
	//REFERENCED METHOD
	//Credit to: http://nuin.blogspot.co.uk/2011/03/quick-tip-sha1-or-md5-checksum-strings.html
	public static String getEncodedSha1Sum( String key ) {
	    try {
	        MessageDigest md = MessageDigest.getInstance( "SHA1" );
	        if(md == null){System.out.println("SHA1 is null");}
	        if(key == null) { System.out.println("key is null");}
	        md.update( key.getBytes() );
	        return new BigInteger( 1, md.digest() ).toString(16);
	    }
	    catch (NoSuchAlgorithmException e) {
	        return "";
	    }
	}
}
