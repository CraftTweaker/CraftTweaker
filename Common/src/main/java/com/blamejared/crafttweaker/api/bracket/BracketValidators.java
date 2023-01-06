package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.BracketValidator;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketValidators")
@Document("vanilla/api/BracketValidators")
public class BracketValidators {
    
    private static final Logger LOGGER = CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME + "-ZenCode");
    
    private BracketValidators() {
    
    }
    
    @ZenCodeType.Method
    @BracketValidator("block")
    public static boolean validateBlockBracket(String tokens) {
        
        return validateBracket("block", tokens, BracketHandlers::getBlock);
    }
    
    @ZenCodeType.Method
    @BracketValidator("material")
    public static boolean validateBlockMaterialBracket(String tokens) {
        
        return validateBracket("material", tokens, BracketHandlers::getMaterial);
    }
    
    @ZenCodeType.Method
    @BracketValidator("blockstate")
    public static boolean validateBlockStateMaterialBracket(String tokens) {
        
        final String[] split = tokens.split(":");
        if(split.length > 4 || split.length < 2) {
            LOGGER.error("Invalid BEP Syntax: <blockstate:{}>! Correct syntax is <blockstate:modid:block_name:properties> or <blockstate:modid:block_name>!", tokens);
            return false;
        }
        
        final String resourceLocation = split[0] + ":" + split[1];
        if(ResourceLocation.tryParse(resourceLocation) == null) {
            LOGGER.error("Invalid Block name for Blockstate BEP. '{}' does not appear to be a valid resource location!", resourceLocation);
            return false;
        }
        
        final String properties = split.length == 3 ? split[2] : "";
        final BlockState blockState = BracketHandlers.getBlockState(resourceLocation, properties);
        return blockState != null;
    }
    
    
    @ZenCodeType.Method
    @BracketValidator("mobeffect")
    public static boolean validateEffectBracket(String tokens) {
        
        if(tokens.split(":").length != 2) {
            LOGGER.error("Invalid Bracket Syntax: <effect:" + tokens + ">! Syntax is <effect:modid:potionname>");
            return false;
        }
        
        return validateBracket("effect", tokens, BracketHandlers::getMobEffect);
    }
    
    @ZenCodeType.Method
    @BracketValidator("enchantment")
    public static boolean validateEnchantment(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            LOGGER.warn("Enchantment BEP <enchantment:{}> does not seem to be lower-case!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            LOGGER.error("Could not get enchantment '{}': not a valid bracket handler, syntax is <enchantment:modid:name>", tokens);
            return false;
        }
        
        final ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!Registry.ENCHANTMENT.containsKey(key)) {
            LOGGER.error("Could not get enchantment '{}': the enchantment isn't registered", tokens);
            return false;
        }
        
        return true;
    }
    
    @ZenCodeType.Method
    @BracketValidator("entitytype")
    public static boolean validateEntityType(String tokens) {
        
        if(ResourceLocation.tryParse(tokens) == null) {
            LOGGER.error("Invalid Bracket Syntax: <entitytype:" + tokens + ">! Syntax is <entitytype:modid:entity_type_name>");
            return false;
        }
        
        return validateBracket("entitytype", tokens, BracketHandlers::getEntityType);
    }
    
    @ZenCodeType.Method
    @BracketValidator("item")
    public static boolean validateItemBracket(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            LOGGER.warn("Item BEP <item:{}> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            LOGGER.error("Could not get item with name: <item:" + tokens + ">! Syntax is <item:modid:itemname>");
            return false;
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        
        if(!Registry.ITEM.containsKey(key)) {
            LOGGER.error("Could not get item with name: <item:" + tokens + ">! Item does not appear to exist!");
            return false;
        }
        
        return true;
    }
    
    @ZenCodeType.Method
    @BracketValidator("profession")
    public static boolean validateProfessionBracket(String tokens) {
        
        if(tokens.split(":").length != 2) {
            LOGGER.error("Invalid Bracket Syntax: <profession:" + tokens + ">! Syntax is <profession:modid:profession_name>");
            return false;
        }
        
        return validateBracket("profession", tokens, BracketHandlers::getProfession);
    }
    
    @ZenCodeType.Method
    @BracketValidator("resource")
    public static boolean validateResourceBracket(String tokens) {
        
        return ResourceLocation.tryParse(tokens) != null;
    }
    
    @ZenCodeType.Method
    @BracketValidator("creativemodetab")
    public static boolean validateCreativeModeTabBracket(String tokens) {
        
        return Arrays.stream(CreativeModeTab.TABS).anyMatch(group -> group.getRecipeFolderName().equals(tokens));
    }
    
    public static boolean validateBracket(String bracketName, String tokens, Function<String, ?> bracketMethod, boolean logError) {
        
        try {
            return bracketMethod.apply(tokens) != null;
        } catch(Exception e) {
            if(logError) {
                LOGGER.error("Error validating BEP <{}:{}>", bracketName, tokens, e);
            }
            return false;
        }
    }
    
    public static boolean validateBracket(String bracketName, String tokens, Function<String, ?> bracketMethod) {
        
        return validateBracket(bracketName, tokens, bracketMethod, true);
    }
    
    @ZenCodeType.Method
    @BracketValidator("soundevent")
    public static boolean validateSoundEvent(String tokens) {
        
        if(tokens.split(":").length != 2) {
            LOGGER.error("Invalid Bracket Syntax: <soundevent:" + tokens + ">! Syntax is <soundevent:modid:name>");
            return false;
        }
        
        return validateBracket("soundevent", tokens, BracketHandlers::getSoundEvent);
    }
    
    @ZenCodeType.Method
    @BracketValidator("targetingstrategy")
    public static boolean validateTargetingStrategy(final String tokens) {
        
        if(tokens.split(":").length != 2) {
            LOGGER.error("Invalid Bracket Syntax <targetingstrategy:" + tokens + ">! Syntax is <targetingstrategy:modid:name>");
            return false;
        }
        
        return validateBracket("targetingstrategy", tokens, BracketHandlers::getTargetingStrategy);
    }
    
}
