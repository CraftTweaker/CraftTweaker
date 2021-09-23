package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * An ItemStack with a chance, usually used for recipe outputs.
 * <p>
 * Careful, if the stack that was used to create the WeightedStack was mutable, then the size setter will mutate the original stack as well!
 *
 * @docParam this <item:minecraft:bedrock>.weight(0.5D)
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.MCWeightedItemStack")
@Document("vanilla/api/items/MCWeightedItemStack")
public class MCWeightedItemStack implements CommandStringDisplayable {
    
    private final IItemStack itemStack;
    private final double weight;
    
    /**
     * Manually creates the weightedItemStack.
     * Usually you can use the operator or `.weight(weight)` method of IItemStack, though
     *
     * @param itemStack The Stack
     * @param weight    The chance, between 0 (0%) and 1 (100%)
     *
     * @docParam itemStack <item:minecraft:bedrock>
     * @docParam weight 0.5D
     */
    @ZenCodeType.Constructor
    public MCWeightedItemStack(IItemStack itemStack, double weight) {
        
        this.itemStack = itemStack;
        this.weight = weight;
    }
    
    /**
     * Gets the original (unweighted) stack
     */
    @ZenCodeType.Getter("stack")
    public IItemStack getItemStack() {
        
        return itemStack;
    }
    
    /**
     * Gets the weight (usually between 0 and 1)
     */
    @ZenCodeType.Getter("weight")
    public double getWeight() {
        
        return weight;
    }
    
    /**
     * Creates a new Weighted Stack with the given percentage
     *
     * @param newWeight The percentage
     *
     * @return A new WeightedItemStack
     *
     * @docParam newWeight 75
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MOD)
    public MCWeightedItemStack percent(int newWeight) {
        
        return weight(newWeight / 100.0D);
    }
    
    /**
     * Sets the itemStack's amount.
     * <p>
     * If the original Stack was mutable, also mutates the original stack's size.
     *
     * @param newAmount The new stack size
     *
     * @return A new WeightedItemStack
     *
     * @docParam newAmount 5
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public MCWeightedItemStack setAmount(int newAmount) {
        
        return new MCWeightedItemStack(itemStack.setAmount(newAmount), weight);
    }
    
    /**
     * Creates a new Weighted Stack with the given weight
     *
     * @param newWeight The percentage
     *
     * @return A new WeightedItemStack
     *
     * @docParam newWeight 0.75D
     */
    @ZenCodeType.Method
    public MCWeightedItemStack weight(double newWeight) {
        
        return new MCWeightedItemStack(itemStack, newWeight);
    }
    
    @Override
    public String getCommandString() {
        
        return String.format("%s.weight(%sD)", itemStack.getCommandString(), weight);
    }
    
    @Override
    public String toString() {
        
        return getCommandString();
    }
    
    @Override
    public boolean equals(Object o) {
    
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MCWeightedItemStack that = (MCWeightedItemStack) o;
    
        if(Double.compare(that.weight, weight) != 0) {
            return false;
        }
        return itemStack.equals(that.itemStack);
    }
    
    @Override
    public int hashCode() {
        
        int result;
        long temp;
        result = itemStack.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    
}
