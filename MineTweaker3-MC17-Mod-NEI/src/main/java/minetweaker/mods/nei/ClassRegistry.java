/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.nei;

import cpw.mods.fml.common.Loader;
import minetweaker.MineTweakerAPI;

/**
 *
 * @author Stan
 */
public class ClassRegistry {
	public static void register() {
		if (Loader.isModLoaded("NotEnoughItems")) {
			MineTweakerAPI.logger.logInfo("Loading NEI support");
			
			MineTweakerAPI.registerClass(NEI.class);
		}
	}
}
