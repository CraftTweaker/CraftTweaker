package com.blamejared.crafttweaker.api.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.items.IItemHandler;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/ItemHandlerCaps")
@ZenCodeType.Name("crafttweaker.api.capability.ItemHandlerCaps")
public class ItemHandlerCaps {
    
    @ZenCodeType.Field
    public static final BlockCapability<IItemHandler, @ZenCodeType.Nullable Direction> BLOCK = Capabilities.ItemHandler.BLOCK;
    @ZenCodeType.Field
    public static final EntityCapability<IItemHandler, Void> ENTITY = Capabilities.ItemHandler.ENTITY;
    @ZenCodeType.Field
    public static final EntityCapability<IItemHandler, @ZenCodeType.Nullable Direction> ENTITY_AUTOMATION = Capabilities.ItemHandler.ENTITY_AUTOMATION;
    @ZenCodeType.Field
    public static final ItemCapability<IItemHandler, Void> ITEM = Capabilities.ItemHandler.ITEM;
    
}
