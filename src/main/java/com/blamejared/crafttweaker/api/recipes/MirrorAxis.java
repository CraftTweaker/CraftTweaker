package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Defines how a recipe should be mirrored.
 */
@ZenRegister
@Document("vanilla/api/recipe/MirrorAxis")
@ZenCodeType.Name("crafttweaker.api.recipe.MirrorAxis")
public enum MirrorAxis {
    /**
     * Mirror the recipe on all axes.
     */
    @ZenCodeType.Field ALL(true, true, true, new Pair[][][] {MirrorAxisTransformations.NONE, MirrorAxisTransformations.VERTICAL, MirrorAxisTransformations.HORIZONTAL, MirrorAxisTransformations.DIAGONAL}),
    /**
     * Mirror the recipe diagonally.
     */
    @ZenCodeType.Field DIAGONAL(false, false, true, new Pair[][][] {MirrorAxisTransformations.DIAGONAL}),
    /**
     * Mirror the recipe horizontally.
     */
    @ZenCodeType.Field HORIZONTAL(true, false, false, new Pair[][][] {MirrorAxisTransformations.HORIZONTAL}),
    /**
     * Do not mirror the recipe.
     */
    @ZenCodeType.Field NONE(false, false, false, new Pair[][][] {MirrorAxisTransformations.NONE}),
    /**
     * Mirror the recipe vertically.
     */
    @ZenCodeType.Field VERTICAL(false, true, false, new Pair[][][] {MirrorAxisTransformations.VERTICAL});
    
    private final boolean horizontal;
    private final boolean vertical;
    private final boolean diagonal;
    private final Pair<Integer, Integer>[][][] transformations;
    
    MirrorAxis(boolean horizontal, boolean vertical, boolean diagonal, Pair<Integer, Integer>[][][] transformations) {
        
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.diagonal = diagonal;
        this.transformations = transformations;
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
    
    public Pair<Integer, Integer>[][][] getTransformations() {
        
        return transformations;
    }
    
    // Need somewhere to store them
    private static class MirrorAxisTransformations {
        
        public static final Pair<Integer, Integer>[][] NONE = new Pair[][] {
                {Pair.of(0, 0), Pair.of(0, 1), Pair.of(0, 2)},
                {Pair.of(1, 0), Pair.of(1, 1), Pair.of(1, 2)},
                {Pair.of(2, 0), Pair.of(2, 1), Pair.of(2, 2)}
        };
        public static final Pair<Integer, Integer>[][] HORIZONTAL = new Pair[][] {
                {Pair.of(0, 2), Pair.of(0, 1), Pair.of(0, 0)},
                {Pair.of(1, 2), Pair.of(1, 1), Pair.of(1, 0)},
                {Pair.of(2, 2), Pair.of(2, 1), Pair.of(2, 0)}
        };
        public static final Pair<Integer, Integer>[][] VERTICAL = new Pair[][] {
                {Pair.of(2, 0), Pair.of(2, 1), Pair.of(2, 2)},
                {Pair.of(1, 0), Pair.of(1, 1), Pair.of(1, 2)},
                {Pair.of(0, 0), Pair.of(0, 1), Pair.of(0, 2)}
        };
        public static final Pair<Integer, Integer>[][] DIAGONAL = new Pair[][] {
                {Pair.of(2, 2), Pair.of(2, 1), Pair.of(2, 0)},
                {Pair.of(1, 2), Pair.of(1, 1), Pair.of(1, 0)},
                {Pair.of(0, 2), Pair.of(0, 1), Pair.of(0, 0)}
        };
        
    }
}
