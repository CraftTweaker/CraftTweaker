package crafttweaker.mc1120.potions;

import crafttweaker.api.potions.IPotion;
import net.minecraft.potion.Potion;

public class MCPotion implements IPotion {
    
    private final Potion potion;
    
    public MCPotion(Potion potion) {
        this.potion = potion;
    }
    
    @Override
    public String name() {
        return potion.getName();
    }
    
    @Override
    public boolean isBadEffect() {
        return potion.isBadEffect();
    }
    
    @Override
    public int getLiquidColor() {
        return potion.getLiquidColor();
    }
    
    @Override
    public Object getInternal() {
        return potion;
    }
}