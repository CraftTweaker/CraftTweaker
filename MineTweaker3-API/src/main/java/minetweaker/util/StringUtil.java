/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Stan
 */
public class StringUtil
{
	private static final Charset UTF8 = Charset.forName("UTF-8");

	private StringUtil() {
	}

	public static String decodeUTF8(byte[] data)
	{
		return UTF8.decode(ByteBuffer.wrap(data)).toString().trim();
	}

	public static byte[] encodeUTF8(String data)
	{
		return UTF8.encode(data).array();
	}
}
