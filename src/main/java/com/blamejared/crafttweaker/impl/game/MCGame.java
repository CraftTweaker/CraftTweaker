package com.blamejared.crafttweaker.impl.game;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.entity.MCEntityClassification;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.fluid.MCFluid;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl.potion.MCEffect;
import com.blamejared.crafttweaker.impl.potion.MCPotion;
import com.blamejared.crafttweaker.impl.util.MCDirectionAxis;
import com.blamejared.crafttweaker.impl.util.text.MCTextFormatting;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
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
    public Collection<MCEffect> getMCEffects() {
        return ForgeRegistries.POTIONS.getValues()
                .stream()
                .map(MCEffect::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("entityTypes")
    public Collection<MCEntityType> getMCEntityTypes() {
        return ForgeRegistries.ENTITIES.getValues()
                .stream()
                .map(MCEntityType::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("fluids")
    public Collection<MCFluid> getMCFluids() {
        return ForgeRegistries.FLUIDS.getValues()
                .stream()
                .map(MCFluid::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("entityClassifications")
    public Collection<MCEntityClassification> getMCEntityClassification() {
        return Arrays.stream(EntityClassification.values())
                .map(MCEntityClassification::new)
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
    public Collection<MCPotion> getMCPotions() {
        return ForgeRegistries.POTION_TYPES.getValues()
                .stream()
                .map(MCPotion::new)
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
    /*
    @ZenCodeType.Getter("itemTags")
    public Collection<MCItemTag> getMCItemTags() {
        return ItemTags.getCollection()
                .getIDTagMap()
                .keySet()
                .stream()
                .map(MCItemTag::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("blockTags")
    public Collection<MCBlockTag> getMCBlockTags() {
        return BlockTags.getCollection()
                .getIDTagMap()
                .keySet()
                .stream()
                .map(MCBlockTag::new)
                .collect(Collectors.toList());
    }

    @ZenCodeType.Getter("entityTags")
    public Collection<MCEntityTag> getMCEntityTags() {
        return EntityTypeTags.getCollection()
                .getIDTagMap()
                .keySet()
                .stream()
                .map(MCEntityTag::new)
                .collect(Collectors.toList());
    }
    */

    /**
     * @return a localized String
     */
    @ZenCodeType.Method
    public String localize(String translationKey) {
        return LanguageMap.getInstance().func_230503_a_(translationKey);
    }
}
