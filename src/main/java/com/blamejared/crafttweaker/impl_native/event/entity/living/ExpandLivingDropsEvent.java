package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @docEvent canceled the entity does not drop anything.
 */
@ZenRegister
@Document("vanilla/api/event/living/MCLivingDropsEvent")
@NativeTypeRegistration(value = LivingDropsEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingDropsEvent")
public class ExpandLivingDropsEvent {
    
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingDropsEvent internal) {
        
        return internal.getSource();
    }

    /**
     * Returns the list of items will be dropped. The list is read-only, modifying this list does not change the drops.
     *
     * You should use the `drops` setter, `addDrop` or `removeDrop` method to change the drops.
     */
    @ZenCodeType.Getter("drops")
    public static List<IItemStack> getDrops(LivingDropsEvent internal) {
        
        return internal.getDrops().stream().map(ItemEntity::getItem).map(MCItemStack::new).collect(Collectors.toList());
    }

    /**
     * Sets items will be dropped.
     */
    @ZenCodeType.Setter("drops")
    public static void setDrops(LivingDropsEvent internal, List<IItemStack> drops) {

        internal.getDrops().clear();
        drops.forEach(drop -> addDrop(internal, drop));
    }
    
    @ZenCodeType.Getter("lootingLevel")
    public static int getLootingLevel(LivingDropsEvent internal) {
        
        return internal.getLootingLevel();
    }

    /**
     * Whether the Entity doing the drop has recently been damaged.
     */
    @ZenCodeType.Getter("recentlyHit")
    public static boolean isRecentlyHit(LivingDropsEvent internal) {
        
        return internal.isRecentlyHit();
    }

    /**
     * Adds an item to the drops.
     */
    @ZenCodeType.Method
    public static void addDrop(LivingDropsEvent internal, IItemStack stack) {

        LivingEntity entity = internal.getEntityLiving();
        internal.getDrops().add(new ItemEntity(entity.world, entity.getPosX() + 0.5, entity.getPosY() + 0.5, entity.getPosZ() + 0.5, stack.getImmutableInternal()));
    }

    /**
     * Removes items that match the given ingredient from drops.
     */
    @ZenCodeType.Method
    public static void removeDrop(LivingDropsEvent internal, IIngredient ingredient) {

        internal.getDrops().removeIf(itemEntity -> ingredient.matches(new MCItemStackMutable(itemEntity.getItem())));
    }
    
}
