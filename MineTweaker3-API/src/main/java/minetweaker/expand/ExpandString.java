/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.expand;

import minetweaker.MineTweakerAPI;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.DataString;
import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 *
 * @author Stanneke
 */
@ZenExpansion("string")
public class ExpandString {
	@ZenCaster
	public static IData asData(String value) {
		return new DataString(value);
	}
	
	@ZenCaster
	public static IChatMessage asChatMessage(String value) {
		return MineTweakerAPI.platform.getMessage(value);
	}
}
