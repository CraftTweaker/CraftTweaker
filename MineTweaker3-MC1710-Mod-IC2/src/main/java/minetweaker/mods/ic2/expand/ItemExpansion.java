package minetweaker.mods.ic2.expand;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import minetweaker.annotations.ModOnly;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIItemStack;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenExpansion("minecraft.item.IItemStack")
@ModOnly("IC2")
public class ItemExpansion {
	/**
	 * Gets the charge level of this item.
	 * 
	 * @param stack target
	 * @return charge level
	 */
	@ZenGetter("ic2getCharge")
	public static double getIC2Charge(IItemStack stack) {
		return ElectricItem.manager.getCharge(getItemStack(stack));
	}
	
	/**
	 * Gets the tier level of this item.
	 * 
	 * @param stack target
	 * @return tier level
	 */
	@ZenGetter("ic2tier")
	public static int getIC2Tier(IItemStack stack) {
		ItemStack iStack = getItemStack(stack);
		if (iStack.getItem() instanceof IElectricItem) {
			return ((IElectricItem) iStack.getItem()).getTier(iStack);
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the transfer limit of the specified stack.
	 * 
	 * @param stack target
	 * @return 
	 */
	@ZenGetter("ic2transferLimit")
	public static double getIC2TransferLimit(IItemStack stack) {
		ItemStack iStack = getItemStack(stack);
		if (iStack.getItem() instanceof IElectricItem) {
			return ((IElectricItem) iStack.getItem()).getTransferLimit(iStack);
		} else {
			return 0;
		}
	}
	
	/**
	 * Gets the fully charged IC2 item.
	 * 
	 * @param stack target
	 * @return charged item
	 */
	@ZenGetter("ic2charged")
	public static IItemStack getIC2Charged(IItemStack stack) {
		ItemStack iStack = getItemStack(stack);
		if (iStack.getItem() instanceof IElectricItem) {
			IElectricItem eItem = (IElectricItem) iStack.getItem();
			Item item = eItem.getChargedItem(iStack);
			ItemStack stack2 = new ItemStack(item, 1, 0);
			ElectricItem.manager.charge(stack2, eItem.getMaxCharge(stack2), eItem.getTier(stack2), true, false);
			return getIItemStack(stack2);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the fully discharged IC2 item.
	 * 
	 * @param stack target
	 * @return discharged item
	 */
	@ZenGetter("ic2discharged")
	public static IItemStack getIC2Discharged(IItemStack stack) {
		ItemStack iStack = getItemStack(stack);
		if (iStack.getItem() instanceof IElectricItem) {
			IElectricItem eItem = (IElectricItem) iStack.getItem();
			Item item = eItem.getEmptyItem(iStack);
			return getItemStack(item, 1, 0);
		} else {
			return null;
		}
	}
	
	/**
	 * Charges the item with the specified amount and returns the charged item.
	 * 
	 * @param stack target
	 * @param amount charge amount
	 * @param tier charge tier
	 * @return charged item
	 */
	@ZenMethod("ic2charge")
	public static IItemStack ic2Charge(IItemStack stack, double amount, @Optional int tier) {
		if (tier == 0) {
			tier = getIC2Tier(stack);
		}
		
		ItemStack iStack = getItemStack(stack).copy();
		ElectricItem.manager.charge(iStack, amount, tier, true, false);
		return getIItemStack(iStack);
	}
	
	/**
	 * Discharges the item with the specified amount and returns the discharged item.
	 * 
	 * @param stack target
	 * @param amount discharge amount
	 * @param tier charge tier
	 * @return discharged item
	 */
	@ZenMethod("ic2discharge")
	public static IItemStack ic2Discharge(IItemStack stack, int amount, @Optional int tier) {
		if (tier == 0) {
			tier = getIC2Tier(stack);
		}
		
		ItemStack iStack = getItemStack(stack).copy();
		ElectricItem.manager.discharge(iStack, amount, tier, true, false, false);
		return getIItemStack(iStack);
	}
}
