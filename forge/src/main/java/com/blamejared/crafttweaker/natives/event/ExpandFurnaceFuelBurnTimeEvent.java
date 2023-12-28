package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/FurnaceFuelBurnTimeEvent")
@NativeTypeRegistration(value = FurnaceFuelBurnTimeEvent.class, zenCodeName = "crafttweaker.forge.api.event.FurnaceFuelBurnTimeEvent")
public class ExpandFurnaceFuelBurnTimeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<FurnaceFuelBurnTimeEvent> BUS = IEventBus.cancelable(
            FurnaceFuelBurnTimeEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("itemStack")
    public static IItemStack getItemStack(FurnaceFuelBurnTimeEvent internal) {
        
        return IItemStack.of(internal.getItemStack());
    }
    
    @ZenCodeType.Getter("recipeType")
    public static IRecipeManager<Recipe<Container>> getRecipeType(FurnaceFuelBurnTimeEvent internal) {
        
        return GenericUtil.uncheck(RecipeTypeBracketHandler.getOrDefault(internal.getRecipeType()));
    }
    
    @ZenCodeType.Setter("burnTime")
    public static void setBurnTime(FurnaceFuelBurnTimeEvent internal, int burnTime) {
        
        internal.setBurnTime(burnTime);
    }
    
    @ZenCodeType.Getter("burnTime")
    public static int getBurnTime(FurnaceFuelBurnTimeEvent internal) {
        
        return internal.getBurnTime();
    }
    
}
