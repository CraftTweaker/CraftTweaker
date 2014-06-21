/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Stan
 */
public class FileUtil {
	public static final byte[] read(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[65536];
		while (input.available() > 0) {
			int bytesRead = input.read(buffer);
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}
}
