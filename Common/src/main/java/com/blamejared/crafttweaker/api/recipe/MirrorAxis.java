package com.blamejared.crafttweaker.api.recipe;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Defines how a recipe should be mirrored.
 */
@ZenRegister
@Document("vanilla/api/recipe/MirrorAxis")
@ZenCodeType.Name("crafttweaker.api.recipe.MirrorAxis")
@BracketEnum("minecraft:mirroraxis")
public enum MirrorAxis {
    /**
     * Mirror the recipe on all axes.
     */
    @ZenCodeType.Field ALL(true, true, true),
    /**
     * Mirror the recipe diagonally.
     */
    @ZenCodeType.Field DIAGONAL(false, false, true),
    /**
     * Mirror the recipe horizontally.
     */
    @ZenCodeType.Field HORIZONTAL(true, false, false),
    /**
     * Do not mirror the recipe.
     */
    @ZenCodeType.Field NONE(false, false, false),
    /**
     * Mirror the recipe vertically.
     */
    @ZenCodeType.Field VERTICAL(false, true, false);
    
    private final boolean horizontal;
    private final boolean vertical;
    private final boolean diagonal;
    
    MirrorAxis(boolean horizontal, boolean vertical, boolean diagonal) {
        
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
}
