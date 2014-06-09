/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import minetweaker.minecraft.data.DataLong;
import minetweaker.minecraft.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("long")
public class ExpandLong {
	@ZenCaster
	public IData asData(long value) {
		return new DataLong(value);
	}
}
