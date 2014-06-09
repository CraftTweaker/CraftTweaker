/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import minetweaker.minecraft.data.DataShort;
import minetweaker.minecraft.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("short")
public class ExpandShort {
	@ZenCaster
	public IData asNBT(short value) {
		return new DataShort(value);
	}
}
