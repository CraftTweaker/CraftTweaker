package crafttweaker.mc1120.potions;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.List;
import java.util.stream.Collectors;

public class MCPotionEffect implements IPotionEffect {
    
    private final PotionEffect potionEffect;
    
    public MCPotionEffect(PotionEffect potionEffect) {
        this.potionEffect = potionEffect;
    }
    
    @Override
    public int compareTo(IPotion other) {
        return potionEffect.compareTo((PotionEffect) other.getInternal());
    }
    
    @Override
    public int getDuration() {
        return potionEffect.getDuration();
    }
    
    @Override
    public IPotion getPotion() {
        return new MCPotion(potionEffect.getPotion());
    }
    
    @Override
    public boolean doesShowParticles() {
        return potionEffect.doesShowParticles();
    }
    
    @Override
    public int getAmplifier() {
        return potionEffect.getAmplifier();
    }
    
    @Override
    public List<IItemStack> getCurativeItems() {
        return potionEffect.getCurativeItems().stream().map(MCItemStack::new).collect(Collectors.toList());
    }
    
    @Override
    public String getEffectName() {
        return potionEffect.getEffectName();
    }
    
    @Override
    public boolean isAmbient() {
        return potionEffect.getIsAmbient();
    }
    
    @Override
    public boolean isPotionDurationMax() {
        return potionEffect.getIsPotionDurationMax();
    }
    
    @Override
    public boolean isCurativeItem(IItemStack item) {
        return potionEffect.isCurativeItem((ItemStack) item.getInternal());
    }
    
    @Override
    public void setIsPotionDurationMax(boolean potionDurationMax) {
        potionEffect.setPotionDurationMax(potionDurationMax);
    }

    @Override
    public void performEffect(IEntity entity) {
        if(entity.getInternal() instanceof EntityLivingBase)
            potionEffect.performEffect((EntityLivingBase) entity.getInternal());
    }

    @Override
    public void combine(IPotionEffect other) {
        potionEffect.combine(CraftTweakerMC.getPotionEffect(other));
    }

    @Override
    public Object getInternal() {
        return potionEffect;
    }
}