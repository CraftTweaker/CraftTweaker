/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import cpw.mods.fml.common.Mod;
import minetweaker.MineTweakerAPI;
import minetweaker.mc172.data.NBTConverter;
import minetweaker.mc172.oredict.OreDict;
import minetweaker.mc172.recipes.MTRecipeManager;

/**
 *
 * @author Stanneke
 */
@Mod(modid = MineTweakerMod.MODID, version = MineTweakerMod.MCVERSION + "-3.0.0")
public class MineTweakerMod {
	public static final String MODID = "MineTweaker";
	public static final String MCVERSION = "1.7.2";
	
	@Mod.Instance(MODID)
	public static MineTweakerMod instance;
	
	public MineTweakerMod() {
		MineTweakerAPI.oreDict = new OreDict();
		MineTweakerAPI.recipes = new MTRecipeManager();
	}
	
	public void reloadScripts() {
		// TODO: implements
	}
}
