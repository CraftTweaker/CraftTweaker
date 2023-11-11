package com.blamejared.crafttweaker.mixin.common.transform.loot;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootDataManager.class)
public abstract class MixinLootDataManager {
    
    @Shadow
    private Map<LootDataId<?>, ?> elements;
    
    @Inject(method = "apply", at = @At("TAIL"))
    private void crafttweaker$apply$populateLootTableIdField(final Map<LootDataType<?>, Map<ResourceLocation, ?>> $$0, final CallbackInfo ci) {
        // ****************** IMPORTANT *********************
        // Code copied in CraftTweakerFabric because Fabric API decided to just fuck up this part of the code
        // Any changes made here should be made there too
        this.elements.entrySet()
                .stream()
                .filter(it -> LootDataType.TABLE.equals(it.getKey().type()))
                .map(it -> Map.entry(it.getKey().location(), GenericUtil.<LootTable>uncheck(it.getValue())))
                .forEach(entry -> GenericUtil.<ILootTableIdHolder.Mutable>uncheck(entry.getValue()).crafttweaker$tableId(entry.getKey()));
    }
}
