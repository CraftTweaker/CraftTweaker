package crafttweaker.util;

import java.io.*;

/**
 * @author Stan
 */
public class FileUtil {
    
    public static byte[] read(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[65536];
        while(input.available() > 0) {
            int bytesRead = input.read(buffer);
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}
