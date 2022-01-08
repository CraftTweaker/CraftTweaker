package com.blamejared.crafttweaker.mixin.common.access.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface AccessBlockStateBase {
    
    @Accessor
    int getLightEmission();
    
    @Mutable
    @Accessor
    void setLightEmission(int lightEmission);
    
    @Accessor
    boolean isUseShapeForLightOcclusion();
    
    @Mutable
    @Accessor
    void setUseShapeForLightOcclusion(boolean useShapeForLightOcclusion);
    
    @Accessor
    boolean isIsAir();
    
    @Mutable
    @Accessor
    void setIsAir(boolean isAir);
    
    @Accessor
    Material getMaterial();
    
    @Mutable
    @Accessor
    void setMaterial(Material material);
    
    @Accessor
    MaterialColor getMaterialColor();
    
    @Mutable
    @Accessor
    void setMaterialColor(MaterialColor materialColor);
    
    @Accessor
    float getDestroySpeed();
    
    @Mutable
    @Accessor
    void setDestroySpeed(float destroySpeed);
    
    @Accessor
    boolean isRequiresCorrectToolForDrops();
    
    @Mutable
    @Accessor
    void setRequiresCorrectToolForDrops(boolean requiresCorrectToolForDrops);
    
    @Accessor
    boolean isCanOcclude();
    
    @Mutable
    @Accessor
    void setCanOcclude(boolean canOcclude);
    
}
