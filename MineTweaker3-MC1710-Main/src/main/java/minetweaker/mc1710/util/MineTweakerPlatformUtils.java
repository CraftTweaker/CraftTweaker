/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.util;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityLivingBase;

/**
 *
 * @author Stan
 */
public class MineTweakerPlatformUtils {
	private MineTweakerPlatformUtils() {
	}

	public static Class<? extends EntityLivingBase> getLivingEntityClass(String entityClassName) {
		try {
			Class<?> entityClass = Class.forName(entityClassName);
			if (!EntityLivingBase.class.isAssignableFrom(entityClass)) {
				throw new RuntimeException("Not an entity class: " + entityClassName);
			}
			return (Class<? extends EntityLivingBase>) entityClass;
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("entity class not found: " + entityClassName);
		}
	}

	public static String getLanguage() {
		return FMLCommonHandler.instance().getSide() == Side.SERVER ? null : FMLClientHandler.instance().getCurrentLanguage();
	}

	public static boolean isLanguageActive(String lang) {
		String current = getLanguage();
		return current != null && current.equals(lang);
	}

	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide() == Side.CLIENT;
	}
}
