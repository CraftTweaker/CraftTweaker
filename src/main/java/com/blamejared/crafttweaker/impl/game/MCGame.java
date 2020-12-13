package com.blamejared.crafttweaker.impl.game;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl.util.MCDirectionAxis;
import com.blamejared.crafttweaker.impl.util.text.MCTextFormatting;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.EntityClassification;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds general game information.
 * Can be accessed using the `game` global keyword
 *
 * @docParam this game
 */

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.game.MCGame")
@Document("vanilla/api/game/MCGame")
public class MCGame {
    
    @ZenCodeType.Getter("directionAxises")
    public Collection<MCDirectionAxis> getMCDirectionAxis() {
        return Arrays.stream(Direction.Axis.values())
                .map(MCDirectionAxis::new)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("effects")
    public Collection<Effect> getMCEffects() {
        return ForgeRegistries.POTIONS.getValues();
    }
    
    @ZenCodeType.Getter("entityTypes")
    public Collection<MCEntityType> getMCEntityTypes() {
        return ForgeRegistries.ENTITIES.getValues()
                .stream()
                .map(MCEntityType::new)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("fluids")
    public Collection<Fluid> getMCFluids() {
        return ForgeRegistries.FLUIDS.getValues();
    }
    
    @ZenCodeType.Getter("entityClassifications")
    public Collection<EntityClassification> getMCEntityClassification() {
        return Arrays.asList(EntityClassification.values());
    }
    
    @ZenCodeType.Getter("formattings")
    public Collection<MCTextFormatting> getMCTextFormatting() {
        return Arrays.stream(TextFormatting.values())
                .map(MCTextFormatting::new)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("items")
    public Collection<IItemStack> getMCItemStacks() {
        return ForgeRegistries.ITEMS.getValues()
                .stream()
                .map(ItemStack::new)
                .map(MCItemStack::new)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("potions")
    public Collection<Potion> getMCPotions() {
        return ForgeRegistries.POTION_TYPES.getValues();
    }
    
    @ZenCodeType.Getter("recipeTypes")
    public Collection<IRecipeManager> getRecipeTypes() {
        return Registry.RECIPE_TYPE.getEntries()
                .stream()
                .map(Map.Entry::getValue)
                .filter((iRecipeType -> !iRecipeType.toString().equals("crafttweaker:scripts")))
                .map(RecipeManagerWrapper::new)
                .collect(Collectors.toList());
    }
    
    /**
     * @return a localized String
     */
    @ZenCodeType.Method
    public String localize(String translationKey) {
        return LanguageMap.getInstance().func_230503_a_(translationKey);
    }
}
