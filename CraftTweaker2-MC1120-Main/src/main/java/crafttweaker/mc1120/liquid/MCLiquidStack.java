package crafttweaker.mc1120.liquid;

import crafttweaker.api.data.IData;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Collections;
import java.util.List;

/**
 * @author Stan
 */
public class MCLiquidStack implements ILiquidStack {

    private final FluidStack stack;
    private final IItemTransformerNew transformerNew;
    private IData tag;

    public MCLiquidStack(FluidStack stack) {
        this(stack, null, null);
    }

    private MCLiquidStack(FluidStack stack, IData tag, IItemTransformerNew transformerNew) {
        this.stack = stack;
        this.tag = tag;
        this.transformerNew = transformerNew;
    }

    @Override
    public ILiquidDefinition getDefinition() {
        return new MCLiquidDefinition(stack.getFluid());
    }

    @Override
    public String getName() {
        return stack.getFluid().getName();
    }

    @Override
    public String getDisplayName() {
        return stack.getFluid().getLocalizedName(stack);
    }

    @Override
    public int getAmount() {
        return stack.amount;
    }

    @Override
    public IData getTag() {
        if(tag == null) {
            if(stack.tag == null) {
                return null;
            }

            tag = NBTConverter.from(stack.tag, true);
        }

        return tag;
    }

    @Override
    public ILiquidStack withTag(IData data) {
        FluidStack result = new FluidStack(stack.getFluid(), stack.amount);
        result.tag = (NBTTagCompound) NBTConverter.from(data);
        return new MCLiquidStack(result, data.immutable(), transformerNew);
    }

    @Override
    public ILiquidStack withAmount(int amount) {
        FluidStack result = new FluidStack(stack.getFluid(), amount);
        result.tag = stack.tag;
        return new MCLiquidStack(result, tag, transformerNew);
    }

    @Override
    public int getLuminosity() {
        return stack.getFluid().getLuminosity(stack);
    }

    @Override
    public int getDensity() {
        return stack.getFluid().getDensity(stack);
    }

    @Override
    public int getTemperature() {
        return stack.getFluid().getTemperature(stack);
    }

    @Override
    public int getViscosity() {
        return stack.getFluid().getViscosity(stack);
    }

    @Override
    public boolean isGaseous() {
        return stack.getFluid().isGaseous(stack);
    }

    @Override
    public Object getInternal() {
        return stack;
    }

    // ##################################
    // ### IIngredient implementation ###
    // ##################################

    @Override
    public String getMark() {
        return null;
    }

    @Override
    public List<IItemStack> getItems() {
        final IItemStack stack = CraftTweakerMC.getIItemStack(FluidUtil.getFilledBucket(this.stack.copy()));
        if(stack != null) {
            final String name = String.format("Any container with %s * %d", this.stack.getLocalizedName(), this.getAmount());
            return Collections.singletonList(stack.withDisplayName(name));
        }
        return Collections.emptyList();
    }
    
    @Override
    public IItemStack[] getItemArray() {
        final IItemStack stack = CraftTweakerMC.getIItemStack(FluidUtil.getFilledBucket(this.stack.copy()));
        if(stack != null) {
            final String name = String.format("Any container with %s * %d", this.stack.getLocalizedName(), this.getAmount());
            return new IItemStack[]{stack.withDisplayName(name)};
        }
        return new IItemStack[0];
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        return Collections.singletonList(this);
    }

    @Override
    public IIngredient amount(int amount) {
        return withAmount(amount);
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }

    @Override
    public IIngredient transformNew(IItemTransformerNew transformer) {
        return new MCLiquidStack(this.stack, this.tag, transformer);
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        throw new UnsupportedOperationException("Liquid stack can't have conditions");
    }

    @Override
    public IIngredient marked(String mark) {
        throw new UnsupportedOperationException("Liquid stack can't be marked");
    }

    @Override
    public boolean matches(IItemStack item) {
        final FluidStack fluidStack = FluidUtil.getFluidContained(CraftTweakerMC.getItemStack(item));
        return fluidStack != null && this.matches(CraftTweakerMC.getILiquidStack(fluidStack));
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        return matches(item);
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        return getDefinition().equals(liquid.getDefinition()) && getAmount() <= liquid.getAmount();
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        
        for(ILiquidStack liquid : ingredient.getLiquids()) {
            if(!matches(liquid)) {
                return false;
            }
        }

        return !ingredient.getLiquids().isEmpty();
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        return item;
    }
    
    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        if(transformerNew != null)
            return transformerNew.transform(item);
    
        final ItemStack itemStack = CraftTweakerMC.getItemStack(item);
    
        //Hardcoded cause a bucket that's drained with less than 1000 MB returns itself
        if(itemStack.getItem() instanceof ItemBucket || itemStack.getItem() == Items.MILK_BUCKET)
            return new MCItemStack(new ItemStack(Items.BUCKET, 1));
    
        final IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(itemStack);
        if(fluidHandler == null)
            return CraftTweakerMC.getIItemStack(ForgeHooks.getContainerItem(itemStack));
        fluidHandler.drain(this.stack.copy(), true);
    
        return MCItemStack.createNonCopy(itemStack);
    }
    
    @Override
    public boolean hasNewTransformers() {
        //Always return true so that the draining can be done
        return true;
    }
    
    @Override
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        throw new UnsupportedOperationException("Liquid stack can't have transformers");
    }
    
    @Override
    public String toString() {
        return "<liquid:" + getName() + ">";
    }
    
    @Override
    public String toCommandString() {
        return toString();
    }
}
