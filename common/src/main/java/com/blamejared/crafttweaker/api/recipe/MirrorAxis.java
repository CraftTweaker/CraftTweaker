package com.blamejared.crafttweaker.api.recipe;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Defines how a recipe should be mirrored.
 */
@ZenRegister
@Document("vanilla/api/recipe/MirrorAxis")
@ZenCodeType.Name("crafttweaker.api.recipe.MirrorAxis")
@BracketEnum("minecraft:mirroraxis")
public enum MirrorAxis implements StringRepresentable {
    
    /**
     * Mirror the recipe on all axes.
     */
    @ZenCodeType.Field ALL("all", true, true, true),
    /**
     * Mirror the recipe diagonally.
     */
    @ZenCodeType.Field DIAGONAL("diagonal", false, false, true),
    /**
     * Mirror the recipe horizontally.
     */
    @ZenCodeType.Field HORIZONTAL("horizontal", true, false, false),
    /**
     * Do not mirror the recipe.
     */
    @ZenCodeType.Field NONE("none", false, false, false),
    /**
     * Mirror the recipe vertically.
     */
    @ZenCodeType.Field VERTICAL("vertical", false, true, false);
    
    public static final StringRepresentable.EnumCodec<MirrorAxis> CODEC = StringRepresentable.fromEnum(MirrorAxis::values);
    
    private final String name;
    private final boolean horizontal;
    private final boolean vertical;
    private final boolean diagonal;
    
    MirrorAxis(String name, boolean horizontal, boolean vertical, boolean diagonal) {
        
        this.name = name;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.diagonal = diagonal;
    }
    
    public boolean isMirrored() {
        
        return horizontal || vertical || diagonal;
    }
    
    public boolean isHorizontal() {
        
        return horizontal;
    }
    
    public boolean isVertical() {
        
        return vertical;
    }
    
    public boolean isDiagonal() {
        
        return diagonal;
    }
    
    @Override
    public @NotNull String getSerializedName() {
        
        return name;
    }
}
