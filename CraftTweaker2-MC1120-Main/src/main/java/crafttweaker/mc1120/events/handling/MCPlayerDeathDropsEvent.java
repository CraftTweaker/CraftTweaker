package crafttweaker.mc1120.events.handling;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.PlayerDeathDropsEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCPlayerDeathDropsEvent implements PlayerDeathDropsEvent {

    private final PlayerDropsEvent event;

    public MCPlayerDeathDropsEvent(PlayerDropsEvent event) {
        this.event = event;
    }

    @Override
    public List<IEntityItem> getItems() {
        return event.getDrops().stream().map(CraftTweakerMC::getIEntityItem).collect(Collectors.toList());
    }

    @Override
    public void setItems(List<IEntityItem> items) {
        List<EntityItem> drops = event.getDrops();
        drops.clear();
        items.stream().map(CraftTweakerMC::getEntityItem).filter(Objects::nonNull).forEach(drops::add);

    }

    @Override
    public void addItem(IItemStack item) {
        BlockPos pos = event.getEntityPlayer().getPosition();
        event.getDrops().add(new EntityItem(event.getEntityPlayer().getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), CraftTweakerMC.getItemStack(item)));
    }

    @Override
    public void addItem(IEntityItem entityItem) {
        EntityItem entity = CraftTweakerMC.getEntityItem(entityItem);
        if (entity != null)
            event.getDrops().add(entity);
    }

    @Override
    public IDamageSource getDamageSource() {
        return CraftTweakerMC.getIDamageSource(event.getSource());
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
