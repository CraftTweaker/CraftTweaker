package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityAnimal;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.passive.*;

public class MCEntityAnimal extends MCEntityAgeable implements IEntityAnimal {
    
    private final EntityAnimal entityAnimal;
    public MCEntityAnimal(EntityAnimal entity) {
        super(entity);
        entityAnimal = entity;
    }
    
    @Override
    public boolean isBreedingItem(IItemStack itemStack) {
        return entityAnimal.isBreedingItem(CraftTweakerMC.getItemStack(itemStack));
    }
    
    @Override
    public void setInLove(IPlayer player) {
        entityAnimal.setInLove(CraftTweakerMC.getPlayer(player));
    }
    
    @Override
    public IPlayer getLoveCause() {
        return CraftTweakerMC.getIPlayer(entityAnimal.getLoveCause());
    }
    
    @Override
    public boolean isInLove() {
        return entityAnimal.isInLove();
    }
    
    @Override
    public void resetInLove() {
        entityAnimal.resetInLove();
    }
    
    @Override
    public boolean canMateWith(IEntityAnimal other) {
        return entityAnimal.canMateWith(CraftTweakerMC.getEntityAnimal(other));
    }
}
