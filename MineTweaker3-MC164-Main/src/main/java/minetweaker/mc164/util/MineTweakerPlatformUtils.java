/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.util;

import net.minecraft.entity.EntityLivingBase;

/**
 *
 * @author Stan
 */
public class MineTweakerPlatformUtils {
	private MineTweakerPlatformUtils() {}
	
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
}
