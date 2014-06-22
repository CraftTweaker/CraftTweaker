/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.nei;

import minetweaker.MineTweakerAPI;

/**
 *
 * @author Stan
 */
public class ClassRegistry {
	public static void register() {
		MineTweakerAPI.registerClass(NEI.class);
	}
}
