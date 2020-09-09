package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import net.minecraft.util.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketValidators")
public class BracketValidators {
    
    private BracketValidators() {
    }
    
    @ZenCodeType.Method
    @BracketValidator("blockmaterial")
    public static boolean validateBlockMaterialBracket(String tokens) {
        return validateBracket("blockmaterial", tokens, BracketHandlers::getBlockMaterial);
    }
    
    @ZenCodeType.Method
    @BracketValidator("blockstate")
    public static boolean validateBlockStateMaterialBracket(String tokens) {
        final String[] split = tokens.split(":");
        if(split.length > 4 || split.length < 2) {
            CraftTweakerAPI.logError("Invalid BEP Syntax: <blockstate:%s>! Correct syntax is <blockstate:modid:block_name:properties> or <blockstate:modid:block_name>!", tokens);
            return false;
        }
        
        final String resourceLocation = split[0] + ":" + split[1];
        if(ResourceLocation.tryCreate(resourceLocation) == null) {
            CraftTweakerAPI.logError("Invalid Block name for Blockstate BEP. '%s' does not appear to be a valid resource location!", resourceLocation);
            return false;
        }
        
        final String properties = split.length == 3 ? split[2] : "";
        final MCBlockState blockState = BracketHandlers.getBlockState(resourceLocation, properties);
        return blockState != null || isRegistryUnlocked(ForgeRegistries.BLOCKS);
    }
    
    @ZenCodeType.Method
    @BracketValidator("directionaxis")
    public static boolean validateDirectionAxisBracket(String tokens) {
        return validateBracket("directionaxis", tokens, BracketHandlers::getDirectionAxis);
    }
    
    @ZenCodeType.Method
    @BracketValidator("effect")
    public static boolean validateEffectBracket(String tokens) {
        if(isRegistryUnlocked(ForgeRegistries.POTIONS) && tokens.split(":").length != 2) {
            CraftTweakerAPI.logError("Invalid Bracket Syntax: <effect:" + tokens + ">! Syntax is <effect:modid:potionname>");
            return false;
        }
        
        return validateBracket("effect", tokens, BracketHandlers::getEffect);
    }
    
    @ZenCodeType.Method
    @BracketValidator("entityclassification")
    public static boolean validateEntityClassification(String tokens) {
        return validateBracket("entityclassification", tokens, BracketHandlers::getEntityClassification);
    }
    
    @ZenCodeType.Method
    @BracketValidator("entitytype")
    public static boolean validateEntityType(String tokens) {
        if(isRegistryUnlocked(ForgeRegistries.ENTITIES) && ResourceLocation.tryCreate(tokens) == null) {
            CraftTweakerAPI.logError("Invalid Bracket Syntax: <entitytype:" + tokens + ">! Syntax is <entitytype:modid:potionname>");
            return false;
        }
        
        return validateBracket("entitytype", tokens, BracketHandlers::getEntityType);
    }
    
    @ZenCodeType.Method
    @BracketValidator("fluid")
    public static boolean validateFluidStack(String tokens) {
        final ResourceLocation resourceLocation = ResourceLocation.tryCreate(tokens);
        if(resourceLocation == null) {
            CraftTweakerAPI.logError("Could not get BEP <fluid:" + tokens + ">. Syntax is <fluid:modid:fluidname>");
            return false;
        }
        
        if(isRegistryUnlocked(ForgeRegistries.FLUIDS) || ForgeRegistries.FLUIDS.containsKey(resourceLocation)) {
            return true;
        }
        
        CraftTweakerAPI.logError("Could not get fluid for <fluid:" + tokens + ">. Fluid does not appear to exist!");
        return false;
        
    }
    
    @ZenCodeType.Method
    @BracketValidator("item")
    public static boolean validateItemBracket(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.logWarning("Item BEP <item:%s> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            CraftTweakerAPI.logError("Could not get item with name: <item:" + tokens + ">! Syntax is <item:modid:itemname>");
            return false;
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(((ForgeRegistry<?>) ForgeRegistries.ITEMS).isLocked() && !ForgeRegistries.ITEMS.containsKey(key)) {
            CraftTweakerAPI.logError("Could not get item with name: <item:" + tokens + ">! Item does not appear to exist!");
            return false;
        }
        
        return true;
    }
    
    @ZenCodeType.Method
    @BracketValidator("recipemanager")
    public static boolean validateRecipeManagerBracket(String tokens) {
        return validateBracket("recipemanager", tokens, BracketHandlers::getRecipeManager);
    }
    
    @ZenCodeType.Method
    @BracketValidator("resource")
    public static boolean validateResourceBracket(String tokens) {
        return ResourceLocation.tryCreate(tokens) != null;
    }
    
    @ZenCodeType.Method
    @BracketValidator("tag")
    public static boolean validateTagBracket(String tokens) {
        return validateBracket("tag", tokens, BracketHandlers::getTag);
    }
    
    public static boolean validateBracket(String bracketName, String tokens, Function<String, ?> bracketMethod, boolean logError) {
        try {
            return bracketMethod.apply(tokens) != null;
        } catch(Exception e) {
            if(logError) {
                CraftTweakerAPI.logThrowing("Error validating BEP <%s:%s>", e, bracketName, tokens);
            }
            return false;
        }
    }
    
    public static boolean validateBracket(String bracketName, String tokens, Function<String, ?> bracketMethod) {
        return validateBracket(bracketName, tokens, bracketMethod, true);
    }
    
    private static boolean isRegistryUnlocked(IForgeRegistry<?> registryEntries) {
        if(!(registryEntries instanceof ForgeRegistry))
            return false;
        return !((ForgeRegistry<?>) registryEntries).isLocked();
    }
}
