package com.blamejared.crafttweaker.impl.game;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl_native.entity.ExpandEntityClassification;
import com.blamejared.crafttweaker.impl_native.entity.ExpandEntityType;
import com.blamejared.crafttweaker.impl_native.fluid.ExpandFluid;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl_native.potion.ExpandEffect;
import com.blamejared.crafttweaker.impl_native.potion.ExpandPotion;
import com.blamejared.crafttweaker.impl.util.MCDirectionAxis;
import com.blamejared.crafttweaker.impl.util.text.MCTextFormatting;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.ItemStack;
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
    public Collection<ExpandEffect> getMCEffects() {
        return ForgeRegistries.POTIONS.getValues()
                .stream()
                .map(ExpandEffect::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("entityTypes")
    public Collection<ExpandEntityType> getMCEntityTypes() {
        return ForgeRegistries.ENTITIES.getValues()
                .stream()
                .map(ExpandEntityType::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("fluids")
    public Collection<ExpandFluid> getMCFluids() {
        return ForgeRegistries.FLUIDS.getValues()
                .stream()
                .map(ExpandFluid::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("entityClassifications")
    public Collection<ExpandEntityClassification> getMCEntityClassification() {
        return Arrays.stream(EntityClassification.values())
                .map(ExpandEntityClassification::new)
                .collect(Collectors.toList());
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
    public Collection<ExpandPotion> getMCPotions() {
        return ForgeRegistries.POTION_TYPES.getValues()
                .stream()
                .map(ExpandPotion::new)
                .collect(Collectors.toList());
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
