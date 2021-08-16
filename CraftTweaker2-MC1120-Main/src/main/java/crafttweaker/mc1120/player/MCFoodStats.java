package crafttweaker.mc1120.player;

import crafttweaker.api.data.IData;
import crafttweaker.api.player.*;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;

import java.util.Objects;

public class MCFoodStats implements IFoodStats {
    
    private final FoodStats foodStats;
    
    public MCFoodStats(FoodStats foodStats) {
        this.foodStats = foodStats;
    }
    
    
    @Override
    public void addStats(int foodValue, float saturationLevel) {
        foodStats.addStats(foodValue, saturationLevel);
    }
    
    @Override
    public void onUpdate(IPlayer player) {
        foodStats.onUpdate((EntityPlayer) player.getInternal());
    }
    
    @Override
    public IData asNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        foodStats.writeNBT(tag);
        return NBTConverter.from(tag, true);
    }
    
    @Override
    public int getFoodLevel() {
        return foodStats.getFoodLevel();
    }
    
    @Override
    public void setFootLevel(int foodLevel) {
        foodStats.setFoodLevel(foodLevel);
    }
    
    @Override
    public boolean needFood() {
        return foodStats.needFood();
    }
    
    @Override
    public void addExhaustion(float exhaustion) {
        foodStats.addExhaustion(exhaustion);
    }
    
    @Override
    public float getSaturationLevel() {
        return foodStats.getSaturationLevel();
    }
    
    @Override
    public void setSaturationLevel(float saturationLevel) {
        foodStats.addExhaustion(saturationLevel - getSaturationLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCFoodStats that = (MCFoodStats) o;
        return Objects.equals(foodStats, that.foodStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodStats);
    }
}
