package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCItemStackMutable")
@Document("fabric/api/item/MCItemStackMutable")
public class MCItemStackMutable implements FabricItemStack {
    
    public static Supplier<MCItemStackMutable> EMPTY = () -> new MCItemStackMutable(ItemStack.EMPTY);
    private final ItemStack internal;
    
    public MCItemStackMutable(ItemStack internal) {
        
        this.internal = internal;
    }
    
    @Override
    public String getCommandString() {
        //TODO move this to an actual registry or something
        
        final StringBuilder sb = new StringBuilder("<item:");
        sb.append(Registry.ITEM.getKey(getInternal().getItem()));
        sb.append(">");
        
        if(getInternal().getTag() != null) {
            MapData data = (MapData) TagToDataConverter.convert(getInternal().getTag())
                    .copyInternal();
            //Damage is special case, if we have more special cases we can handle them here.
            if(getInternal().isDamageableItem()) {
                data.remove("Damage");
            }
            if(!data.isEmpty()) {
                sb.append(".withTag(");
                sb.append(data.asString());
                sb.append(")");
            }
        }
        
        if(getInternal().getDamageValue() > 0) {
            sb.append(".withDamage(")
                    .append(getInternal().getDamageValue())
                    .append(")");
        }
        
        if(!isEmpty()) {
            if(getAmount() != 1) {
                sb.append(" * ").append(getAmount());
            }
        }
        return sb.toString();
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return new IItemStack[] {this};
    }
    
    @Override
    public IItemStack copy() {
        
        return new MCItemStackMutable(getInternal().copy());
    }
    
    @Override
    public IItemStack asMutable() {
        
        return this;
    }
    
    @Override
    public IItemStack asImmutable() {
        
        return new MCItemStack(getInternal().copy());
    }
    
    @Override
    public boolean isImmutable() {
        
        return false;
    }
    
    @Override
    public ItemStack getInternal() {
        
        return internal;
    }
    
    @Override
    public IItemStack modify(Consumer<ItemStack> stackModifier) {
        
        stackModifier.accept(getInternal());
        return this;
    }
    
    @Override
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        //Implemented manually instead of using ItemStack.areItemStacksEqual to ensure it
        // stays the same as hashCode even if MC's impl would change
        final ItemStack thatStack = ((MCItemStack) o).getInternal();
        final ItemStack thisStack = getInternal();
        
        if(thisStack.isEmpty()) {
            return thatStack.isEmpty();
        }
        
        if(thisStack.getCount() != thatStack.getCount()) {
            return false;
        }
        
        if(!Objects.equals(thisStack.getItem(), thatStack.getItem())) {
            return false;
        }
        
        return Objects.equals(thisStack.getTag(), thatStack.getTag());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal().getCount(), getInternal().getItem(), getInternal().getTag());
    }
    
}
