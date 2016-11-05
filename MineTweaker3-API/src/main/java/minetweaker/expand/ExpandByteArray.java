package minetweaker.expand;

import minetweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * Makes byte arrays convertable to IData.
 *
 * @author Jared
 */
@ZenExpansion("byte[]")
public class ExpandByteArray {
	
	@ZenCaster
	public static IData asData(byte[] values) {
		return new DataByteArray(values, true);
	}
}
