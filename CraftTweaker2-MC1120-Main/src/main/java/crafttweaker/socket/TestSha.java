package crafttweaker.socket;

import java.security.*;
import java.util.Base64;

public class TestSha {
    
    public static void main(String[] args) {
        
        String key = "dGhlIHNhbXBsZSBub25jZQ==";
        String secAccept = sha1base64(key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
    
        System.out.println("secAccept = " + secAccept);
    }
    
    private static String sha1base64(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(md.digest(str.getBytes()));
    }
}
