/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import minetweaker.minecraft.data.DataString;
import minetweaker.minecraft.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("string")
public class ExpandString {
	@ZenCaster
	public IData asData(String value) {
		return new DataString(value);
	}
}
