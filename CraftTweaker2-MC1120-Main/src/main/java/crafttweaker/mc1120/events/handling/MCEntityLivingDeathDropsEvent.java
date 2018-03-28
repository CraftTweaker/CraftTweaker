package crafttweaker.mc1120.events.handling;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.*;
import crafttweaker.api.event.EntityLivingDeathDropsEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.List;
import java.util.stream.Collectors;

public class MCEntityLivingDeathDropsEvent implements EntityLivingDeathDropsEvent {
    
    private final LivingDropsEvent event;
    
    public MCEntityLivingDeathDropsEvent(LivingDropsEvent event) {
        this.event = event;
    }
    
    @Override
    public IDamageSource getDamageSource() {
        return CraftTweakerMC.getIDamageSource(event.getSource());
    }
    
    @Override
    public void addItem(IItemStack item) {
        IEntity entity = getEntity();
        addItem(item.createEntityItem(entity.getWorld(), entity.getPosition()));
    }
    
    @Override
    public void addItem(IEntityItem entityItem) {
        EntityItem item = CraftTweakerMC.getEntityItem(entityItem);
        if(item != null)
            event.getDrops().add(item);
    }
    
    @Override
    public int getLootingLevel() {
        return event.getLootingLevel();
    }
    
    @Override
    public boolean getIsRecentlyHit() {
        return event.isRecentlyHit();
    }
    
    @Override
    public List<IEntityItem> getDrops() {
        return event.getDrops().stream().map(CraftTweakerMC::getIEntityItem).collect(Collectors.toList());
    }
    
    @Override
    public void setDrops(List<IEntityItem> drops) {
        event.getDrops().clear();
        drops.forEach(this::addItem);
    }
    
    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }
    
    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
