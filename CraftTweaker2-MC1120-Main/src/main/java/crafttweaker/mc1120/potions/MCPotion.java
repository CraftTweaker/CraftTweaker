package crafttweaker.mc1120.potions;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.*;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.potion.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public int getLiquidColour() {
        return potion.getLiquidColor();
    }
    
    @Override
    public IPotionEffect makePotionEffect(int duration, int amplifier) {
        return new MCPotionEffect(new PotionEffect(potion, duration, amplifier));
    }
    
    @Override
    public IPotionEffect makePotionEffect(int duration, int amplifier, boolean ambient, boolean showParticles) {
        return new MCPotionEffect((new PotionEffect(potion, duration, amplifier, ambient, showParticles)));
    }
    
    @Override
    public List<IItemStack> getCurativeItems() {
        return potion.getCurativeItems().stream().map(MCItemStack::new).collect(Collectors.toList());
    }
    
    @Override
    public boolean hasStatusIcon() {
        return potion.hasStatusIcon();
    }
    
    @Override
    public boolean isBeneficial() {
        return potion.isBeneficial();
    }

    @Override
    public void setBeneficial() {
        potion.setBeneficial();
    }

    @Override
    public boolean isInstant() {
        return potion.isInstant();
    }
    
    @Override
    public Potion getInternal() {
        return potion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCPotion mcPotion = (MCPotion) o;
        return Objects.equals(potion, mcPotion.potion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(potion);
    }
}