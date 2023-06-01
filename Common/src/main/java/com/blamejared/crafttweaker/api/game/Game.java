package com.blamejared.crafttweaker.api.game;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.game.Game")
@Document("vanilla/api/game/Game")
public class Game {
    
    @ZenCodeGlobals.Global("game")
    public static final Game INSTANCE = new Game();
    
    private Game() {}
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effects")
    public Collection<MobEffect> getMobEffects() {
        
        return BuiltInRegistries.MOB_EFFECT.stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantments")
    public Collection<Enchantment> getEnchantments() {
        
        return BuiltInRegistries.ENCHANTMENT.stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityTypes")
    public Collection<EntityType> getEntityTypes() {
        
        return (Collection) BuiltInRegistries.ENTITY_TYPE
                .stream()
                .toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluids")
    public Collection<Fluid> getFluids() {
        
        return BuiltInRegistries.FLUID.stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("items")
    public Collection<IItemStack> getItemStacks() {
        
        return BuiltInRegistries.ITEM.stream()
                .map(Item::getDefaultInstance)
                .filter(Predicate.not(ItemStack::isEmpty))
                .map(IItemStack::of)
                .toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("potions")
    public Collection<Potion> getPotions() {
        
        return BuiltInRegistries.POTION.stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeTypes")
    public Collection<IRecipeManager> getRecipeTypes() {
        
        return BuiltInRegistries.RECIPE_TYPE
                .stream()
                .map(RecipeTypeBracketHandler::getOrDefault)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blocks")
    public Collection<Block> getBlocks() {
        
        return BuiltInRegistries.BLOCK.stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockStates")
    public Collection<BlockState> getBlockStates() {
        
        return BuiltInRegistries.BLOCK
                .stream()
                .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("villagerProfessions")
    public Collection<VillagerProfession> getVillagerProfessions() {
        
        return BuiltInRegistries.VILLAGER_PROFESSION.stream().toList();
    }
    
    /**
     * @return a localized String
     *
     * @docParam translationKey "gui.up"
     */
    @ZenCodeType.Method
    public String localize(String translationKey) {
        
        return Language.getInstance().getOrDefault(translationKey);
    }
    
}
