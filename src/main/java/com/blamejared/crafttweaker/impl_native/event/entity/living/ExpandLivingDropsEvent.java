package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/event/living/MCLivingDropsEvent")
@EventCancelable(canceledDescription = "the entity does not drop anything")
@NativeTypeRegistration(value = LivingDropsEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingDropsEvent")
public class ExpandLivingDropsEvent {
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingDropsEvent internal) {
        return internal.getSource();
    }
    
    @ZenCodeType.Getter("drops")
    public static Collection<IItemStack> getDrops(LivingDropsEvent internal) {
        return internal.getDrops().stream().map(ItemEntity::getItem).map(MCItemStack::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("lootingLevel")
    public static int getLootingLevel(LivingDropsEvent internal) {
        return internal.getLootingLevel();
    }
    
    @ZenCodeType.Getter("recentlyHit")
    public static boolean isRecentlyHit(LivingDropsEvent internal) {
        return internal.isRecentlyHit();
    }
}
