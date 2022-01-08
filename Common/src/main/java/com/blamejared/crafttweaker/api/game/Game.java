package com.blamejared.crafttweaker.api.game;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.locale.Language;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.game.Game")
@Document("vanilla/api/game/Game")
public class Game {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effects")
    public Collection<MobEffect> getMobEffects() {
        
        return Services.REGISTRY.mobEffects().stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantments")
    public Collection<Enchantment> getEnchantments() {
        
        return Services.REGISTRY.enchantments().stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entityTypes")
    public Collection<EntityType> getEntityTypes() {
        
        return (Collection) Services.REGISTRY.entityTypes()
                .stream()
                .toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluids")
    public Collection<Fluid> getFluids() {
        
        return Services.REGISTRY.fluids().stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("items")
    public Collection<IItemStack> getItemStacks() {
        
        return Services.REGISTRY.items()
                .stream()
                .map(Item::getDefaultInstance)
                .map(Services.PLATFORM::createMCItemStack)
                .filter(stack -> !stack.isEmpty())
                .toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("potions")
    public Collection<Potion> getPotions() {
        
        return Services.REGISTRY.potions().stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("recipeTypes")
    public Collection<IRecipeManager> getRecipeTypes() {
        
        return Services.REGISTRY.recipeTypes()
                .stream()
                .map(RecipeTypeBracketHandler::getOrDefault)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blocks")
    public Collection<Block> getBlocks() {
        
        return Services.REGISTRY.blocks().stream().toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockStates")
    public Collection<BlockState> getBlockStates() {
        
        return Services.REGISTRY.blocks()
                .stream()
                .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("villagerProfessions")
    public Collection<VillagerProfession> getProfessions() {
        
        return Services.REGISTRY.villagerProfessions().stream().toList();
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
