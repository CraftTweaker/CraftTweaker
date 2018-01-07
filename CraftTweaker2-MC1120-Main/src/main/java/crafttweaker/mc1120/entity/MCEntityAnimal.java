package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityAnimal;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;

public class MCEntityAnimal extends MCEntityAgeable implements IEntityAnimal {
    
    private final EntityAnimal entityAnimal;
    public MCEntityAnimal(EntityAnimal entity) {
        super(entity);
        entityAnimal = entity;
    }
    
    @Override
    public boolean isBreedingItem(IItemStack itemStack) {
        return entityAnimal.isBreedingItem((ItemStack) itemStack.getInternal());
    }
    
    @Override
    public void setInLove(IPlayer player) {
        entityAnimal.setInLove(player == null ? null : (EntityPlayer) player.getInternal());
    }
    
    @Override
    public IPlayer getLoveCause() {
        EntityPlayerMP ret = entityAnimal.getLoveCause();
        return ret == null ? null : new MCPlayer(ret);
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
        return entityAnimal.canMateWith((EntityAnimal) other.getInternal());
    }
}
