package com.blamejared.crafttweaker.impl_native.tileentity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.tileentity.TileEntity;

/**
 * Represents a block entity.
 */
@ZenRegister
@Document("vanilla/api/tileentity/MCTileEntity")
@NativeTypeRegistration(value = TileEntity.class, zenCodeName = "crafttweaker.api.tileentity.MCTileEntity")
public class ExpandTileEntity {}
