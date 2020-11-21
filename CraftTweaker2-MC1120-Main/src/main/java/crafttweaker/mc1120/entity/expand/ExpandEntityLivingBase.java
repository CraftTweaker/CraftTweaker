package crafttweaker.mc1120.entity.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import net.minecraft.util.EnumHand;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenExpansion("crafttweaker.entity.IEntityLivingBase")
@ZenRegister
public class ExpandEntityLivingBase {

    @ZenMethod
    @ZenGetter
    public boolean isElytraFlying(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isElytraFlying();
    }

    @ZenMethod
    @ZenGetter("activeItemStack")
    public IItemStack getActiveItemStack(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getIItemStack(CraftTweakerMC.getEntityLivingBase(entityLivingBase).getActiveItemStack());
    }

    @ZenMethod
    @ZenGetter
    public boolean isActiveItemStackBlocking(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isActiveItemStackBlocking();
    }

    @ZenMethod
    @ZenGetter("activeHand")
    public IEntityEquipmentSlot getActiveHand(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getIEntityEquipmentSlot(CraftTweakerMC.getEntityLivingBase(entityLivingBase).getActiveHand());
    }

    @ZenSetter("activeHand")
    @ZenMethod
    public void setActiveHand(IEntityLivingBase entityLivingBase, IEntityEquipmentSlot hand) {
        EnumHand enumHand = CraftTweakerMC.getHand(hand);
        if (enumHand != null) CraftTweakerMC.getEntityLivingBase(entityLivingBase).setActiveHand(enumHand);
    }

    @ZenMethod
    @ZenGetter("isHandActive")
    public boolean isHandActive(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isHandActive();
    }

    @ZenMethod
    public void resetActiveHand(IEntityLivingBase entityLivingBase) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).resetActiveHand();
    }

    @ZenMethod
    public void stopActiveHand(IEntityLivingBase entityLivingBase) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).stopActiveHand();
    }

    @ZenMethod
    @ZenGetter
    public boolean isSwingInProgress(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isSwingInProgress;
    }

    @ZenMethod
    @ZenGetter("swingProgress")
    public int getSwingProgress(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).swingProgressInt;
    }

    @ZenMethod
    @ZenSetter("swingProgress")
    public void setSwingProgress(IEntityLivingBase entityLivingBase, int swingProgress) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).swingProgressInt = swingProgress;
    }

    @ZenMethod
    public boolean attemptTeleport(IEntityLivingBase entityLivingBase, double x, double y, double z) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).attemptTeleport(x, y, z);
    }

    @ZenGetter("creatureAttribute")
    public String getCreatureAttribute(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).getCreatureAttribute().name();
    }

    @ZenMethod
    @ZenGetter
    public void removePotionEffect(IEntityLivingBase entityLivingBase, IPotion potion) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).removePotionEffect(CraftTweakerMC.getPotion(potion));
    }
}