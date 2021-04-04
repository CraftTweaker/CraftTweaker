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
    public static boolean isElytraFlying(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isElytraFlying();
    }

    @ZenMethod
    @ZenGetter("activeItemStack")
    public static IItemStack getActiveItemStack(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getIItemStack(CraftTweakerMC.getEntityLivingBase(entityLivingBase).getActiveItemStack());
    }

    @ZenMethod
    @ZenGetter
    public static boolean isActiveItemStackBlocking(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isActiveItemStackBlocking();
    }

    @ZenMethod
    @ZenGetter("activeHand")
    public static IEntityEquipmentSlot getActiveHand(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getIEntityEquipmentSlot(CraftTweakerMC.getEntityLivingBase(entityLivingBase).getActiveHand());
    }

    @ZenSetter("activeHand")
    @ZenMethod
    public static void setActiveHand(IEntityLivingBase entityLivingBase, IEntityEquipmentSlot hand) {
        EnumHand enumHand = CraftTweakerMC.getHand(hand);
        if (enumHand != null) CraftTweakerMC.getEntityLivingBase(entityLivingBase).setActiveHand(enumHand);
    }

    @ZenMethod
    @ZenGetter("isHandActive")
    public static boolean isHandActive(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isHandActive();
    }

    @ZenMethod
    public static void resetActiveHand(IEntityLivingBase entityLivingBase) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).resetActiveHand();
    }

    @ZenMethod
    public static void stopActiveHand(IEntityLivingBase entityLivingBase) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).stopActiveHand();
    }

    @ZenMethod
    @ZenGetter
    public static boolean isSwingInProgress(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).isSwingInProgress;
    }

    @ZenMethod
    @ZenGetter("swingProgress")
    public static int getSwingProgress(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).swingProgressInt;
    }

    @ZenMethod
    @ZenSetter("swingProgress")
    public static void setSwingProgress(IEntityLivingBase entityLivingBase, int swingProgress) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).swingProgressInt = swingProgress;
    }

    @ZenMethod
    public static boolean attemptTeleport(IEntityLivingBase entityLivingBase, double x, double y, double z) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).attemptTeleport(x, y, z);
    }

    @ZenGetter("creatureAttribute")
    @ZenMethod
    public static String getCreatureAttribute(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).getCreatureAttribute().name();
    }

    @ZenMethod
    public static void removePotionEffect(IEntityLivingBase entityLivingBase, IPotion potion) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).removePotionEffect(CraftTweakerMC.getPotion(potion));
    }

    @ZenGetter("moveForward")
    @ZenMethod
    public static float getMoveForward(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).moveForward;
    }

    @ZenSetter("moveForward")
    @ZenMethod
    public static void setMoveForward(IEntityLivingBase entityLivingBase, float moveForward) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).moveForward = moveForward;
    }

    @ZenGetter("moveStrafing")
    @ZenMethod
    public static float getMoveStrafing(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).moveStrafing;
    }

    @ZenSetter("moveStrafing")
    @ZenMethod
    public static void setMoveStrafing(IEntityLivingBase entityLivingBase, float moveStrafing) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).moveStrafing = moveStrafing;
    }

    @ZenGetter("moveVertical")
    @ZenMethod
    public static float getMoveVertical(IEntityLivingBase entityLivingBase) {
        return CraftTweakerMC.getEntityLivingBase(entityLivingBase).moveVertical;
    }

    @ZenSetter("moveVertical")
    @ZenMethod
    public static void setMoveVertical(IEntityLivingBase entityLivingBase, float moveVertical) {
        CraftTweakerMC.getEntityLivingBase(entityLivingBase).moveVertical = moveVertical;
    }
}