package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @docEvent canceled the entity does not drop anything.
 */
@ZenRegister
@Document("vanilla/api/event/living/LivingDropsEvent")
@NativeTypeRegistration(value = LivingDropsEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingDropsEvent")
public class ExpandLivingDropsEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingDropsEvent internal) {
        
        return internal.getSource();
    }
    
    /**
     * Returns the list of items will be dropped. The list is read-only, modifying this list does not change the drops.
     *
     * You should use the `drops` setter, `addDrop` or `removeDrop` method to change the drops.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("drops")
    public static List<IItemStack> getDrops(LivingDropsEvent internal) {
        
        return internal.getDrops()
                .stream()
                .map(ItemEntity::getItem)
                .map(Services.PLATFORM::createMCItemStack)
                .collect(Collectors.toList());
    }
    
    /**
     * Sets which items will be dropped.
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("drops")
    public static void setDrops(LivingDropsEvent internal, List<IItemStack> drops) {
        
        internal.getDrops().clear();
        drops.forEach(drop -> addDrop(internal, drop));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lootingLevel")
    public static int getLootingLevel(LivingDropsEvent internal) {
        
        return internal.getLootingLevel();
    }
    
    /**
     * Whether the Entity doing the drop has recently been damaged.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isRecentlyHit")
    public static boolean isRecentlyHit(LivingDropsEvent internal) {
        
        return internal.isRecentlyHit();
    }
    
    /**
     * Adds an item to the drops.
     */
    @ZenCodeType.Method
    public static void addDrop(LivingDropsEvent internal, IItemStack stack) {
        
        LivingEntity entity = internal.getEntityLiving();
        internal.getDrops()
                .add(new ItemEntity(entity.level, entity.getX() + 0.5, entity.getY() + 0.5, entity.getZ() + 0.5, stack.getImmutableInternal()));
    }
    
    /**
     * Removes items that match the given ingredient from drops.
     */
    @ZenCodeType.Method
    public static void removeDrop(LivingDropsEvent internal, IIngredient ingredient) {
        
        internal.getDrops()
                .removeIf(itemEntity -> ingredient.matches(Services.PLATFORM.createMCItemStackMutable(itemEntity.getItem())));
    }
    
}
