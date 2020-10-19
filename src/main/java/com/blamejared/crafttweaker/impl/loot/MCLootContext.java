package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.blocks.MCBlockState;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.MCLootContext")
@Document("vanilla/api/loot/MCLootContext")
@ZenWrapper(wrappedClass = "net.minecraft.loot.LootContext")
@SuppressWarnings("unused")
public class MCLootContext {
    private final LootContext nativeContext;

    public MCLootContext(final LootContext nativeContext) {
        this.nativeContext = nativeContext;
    }

    //@ZenCodeType.Getter("thisEntity")
    //@ZenCodeType.Nullable
    //MCEntity getThisEntity();

    //@ZenCodeType.Getter("lastDamagePlayer")
    //@ZenCodeType.Nullable
    //MCPlayer getLastDamagePlayer();

    //@ZenCodeType.Getter("damageSource")
    //@ZenCodeType.Nullable
    //MCDamageSource getDamageSource();

    //@ZenCodeType.Getter("killerEntity")
    //@ZenCodeType.Nullable
    //MCEntity getKillerEntity();

    //@ZenCodeType.Getter("directKillerEntity")
    //@ZenCodeType.Nullable
    //MCEntity getDirectKillerEntity();

    //@ZenCodeType.Getter("origin")
    //@ZenCodeType.Nullable
    //MCVector3d getOrigin();

    @ZenCodeType.Getter("blockState")
    @ZenCodeType.Nullable
    public MCBlockState getBlockState() {
        final BlockState nativeState = this.nativeContext.get(LootParameters.BLOCK_STATE);
        return nativeState == null? null : new MCBlockState(nativeState);
    }

    //@ZenCodeType.Getter("tileEntity")
    //@ZenCodeType.Nullable
    //MCTileEntity getTileEntity();

    @ZenCodeType.Getter("tool")
    public IItemStack getTool() {
        final ItemStack nativeTool = this.nativeContext.get(LootParameters.TOOL);
        return nativeTool == null || nativeTool.isEmpty()? MCItemStack.EMPTY.get() : new MCItemStack(nativeTool);
    }

    @ZenCodeType.Getter("explosionRadius")
    public float getExplosionRadius() {
        final Float nativeRadius = this.nativeContext.get(LootParameters.EXPLOSION_RADIUS);
        return nativeRadius == null? 0.0F : nativeRadius;
    }

    @ZenCodeType.Getter("lootingModifier")
    public int getLootingModifier() {
        return this.nativeContext.getLootingModifier();
    }

    //@ZenCodeType.Getter("world")
    //@ZenCodeType.Nullable
    //MCWorld getWorld();

    @ZenCodeType.Getter("luck")
    public float getLuck() {
        return this.nativeContext.getLuck();
    }

    public LootContext getInternal() {
        return this.nativeContext;
    }
}
