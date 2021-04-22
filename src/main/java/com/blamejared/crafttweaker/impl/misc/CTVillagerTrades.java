package com.blamejared.crafttweaker.impl.misc;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.villagers.BasicTradeExposer;
import com.blamejared.crafttweaker.api.villagers.ITradeRemover;
import com.blamejared.crafttweaker.impl.actions.villagers.ActionAddTrade;
import com.blamejared.crafttweaker.impl.actions.villagers.ActionAddWanderingTrade;
import com.blamejared.crafttweaker.impl.actions.villagers.ActionRemoveTrade;
import com.blamejared.crafttweaker.impl.actions.villagers.ActionRemoveWanderingTrade;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this villagerTrades
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.villagers.VillagerTrades")
@Document("vanilla/api/villager/VillagerTrades")
public class CTVillagerTrades {
    
    @ZenCodeGlobals.Global("villagerTrades")
    public static final CTVillagerTrades INSTANCE = new CTVillagerTrades();
    
    /**
     * Adds a Villager Trade for emeralds for an Item. An example being, giving a villager 2 emeralds for an arrow.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param emeralds      The amount of Emeralds.
     * @param forSale       What ItemStack is being sold (by the Villager).
     * @param maxTrades     How many times can this trade be done.
     * @param xp            How much Experience is given by trading.
     * @param priceMult     When this trade is discounted, how much should it be discounted by.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam emeralds 16
     * @docParam forSale <item:minecraft:diamond>
     * @docParam maxTrades 5
     * @docParam xp 2
     * @docParam priceMult 0.05
     */
    @ZenCodeType.Method
    public void addTrade(VillagerProfession profession, int villagerLevel, int emeralds, ItemStack forSale, int maxTrades, int xp, @ZenCodeType.OptionalFloat(1.0f) float priceMult) {
        
        BasicTrade trade = new BasicTrade(emeralds, forSale, maxTrades, xp, priceMult);
        addTradeInternal(profession, villagerLevel, trade);
    }
    
    /**
     * Adds a Villager Trade for an Item for an Item. An example being, giving a villager 2 diamonds for an arrow.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param input1        The ItemStack that is being given to the Villager.
     * @param forSale       What ItemStack is being sold (by the Villager).
     * @param maxTrades     How many times can this trade be done.
     * @param xp            How much Experience is given by trading.
     * @param priceMult     When this trade is discounted, how much should it be discounted by.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam input1 <item:minecraft:dirt> * 16
     * @docParam forSale <item:minecraft:diamond>
     * @docParam maxTrades 5
     * @docParam xp 2
     * @docParam priceMult 0.05
     */
    @ZenCodeType.Method
    public void addTrade(VillagerProfession profession, int villagerLevel, ItemStack input1, ItemStack forSale, int maxTrades, int xp, @ZenCodeType.OptionalFloat(1.0f) float priceMult) {
        
        BasicTrade trade = new BasicTrade(input1, forSale, maxTrades, xp, priceMult);
        addTradeInternal(profession, villagerLevel, trade);
    }
    
    /**
     * Adds a Villager Trade for two Items for an Item. An example being, giving a villager 2 diamonds and 2 dirt for an arrow.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param input1        The main ItemStack that is being given to the Villager.
     * @param input2        The secondary ItemStack that is being given to the Villager.
     * @param forSale       What ItemStack is being sold (by the Villager).
     * @param maxTrades     How many times can this trade be done.
     * @param xp            How much Experience is given by trading.
     * @param priceMult     When this trade is discounted, how much should it be discounted by.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam input1 <item:minecraft:diamond> * 2
     * @docParam input2 <item:minecraft:dirt> * 2
     * @docParam forSale <item:minecraft:arrow>
     * @docParam maxTrades 5
     * @docParam xp 2
     * @docParam priceMult 0.05
     */
    @ZenCodeType.Method
    public void addTrade(VillagerProfession profession, int villagerLevel, ItemStack input1, ItemStack input2, ItemStack forSale, int maxTrades, int xp, @ZenCodeType.OptionalFloat(1.0f) float priceMult) {
        
        BasicTrade trade = new BasicTrade(input1, input2, forSale, maxTrades, xp, priceMult);
        addTradeInternal(profession, villagerLevel, trade);
    }
    
    /**
     * Removes a `BasicTrade` Villager trade. `BasicTrades` are trades that allow any item, to any other item. It it only really used for mod recipes and it not used for any vanilla villager trade.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param forSale       What ItemStack is being sold (by the Villager).
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam forSale <item:minecraft:arrow>
     * @docParam price <item:minecraft:stick>
     * @docParam price2 <item:minecraft:emerald>
     */
    @ZenCodeType.Method
    public void removeBasicTrade(VillagerProfession profession, int villagerLevel, IItemStack forSale, @ZenCodeType.Optional("<item:minecraft:air>") IItemStack price, @ZenCodeType.Optional("<item:minecraft:air>") IItemStack price2) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof BasicTrade) {
                boolean saleMatches = new MCItemStackMutable(BasicTradeExposer.getForSale(trade)).matches(forSale);
                boolean priceMatches = new MCItemStackMutable(BasicTradeExposer.getPrice(trade)).matches(price);
                boolean price2Matches = new MCItemStackMutable(BasicTradeExposer.getPrice2(trade)).matches(price2);
                return saleMatches && priceMatches && price2Matches;
            }
            return false;
        });
    }
    
    /**
     * Removes a Villager trade for Emeralds for Items. An example being, giving a villager 2 Emeralds for an Arrow.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param tradeFor      What ItemStack is being sold (by the Villager).
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam tradeFor <item:minecraft:arrow>.definition
     */
    @ZenCodeType.Method
    public void removeEmeraldForItemsTrade(VillagerProfession profession, int villagerLevel, Item tradeFor) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof VillagerTrades.EmeraldForItemsTrade) {
                return ((VillagerTrades.EmeraldForItemsTrade) trade).tradeItem == tradeFor;
            } else if(trade instanceof BasicTrade) {
                return BasicTradeExposer.getForSale(trade).getItem() == tradeFor;
            }
            return false;
        });
    }
    
    /**
     * Removes a Villager trade for Items for Emeralds. An example being, giving a villager an Arrow and getting 2 Emeralds.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param sellingItem   What ItemStack is being given to the Villager.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam sellingItem <item:minecraft:arrow>
     */
    @ZenCodeType.Method
    public void removeItemsForEmeraldsTrade(VillagerProfession profession, int villagerLevel, IItemStack sellingItem) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof VillagerTrades.ItemsForEmeraldsTrade) {
                return sellingItem.matches(new MCItemStackMutable(((VillagerTrades.ItemsForEmeraldsTrade) trade).sellingItem));
            } else if(trade instanceof BasicTrade) {
                return new MCItemStackMutable(BasicTradeExposer.getPrice(trade)).matches(sellingItem);
            }
            return false;
        });
    }
    
    /**
     * Removes a Villager trade for Emeralds and Items for Items. An example being, giving a villager an Emerald and Gravel and getting 16 Flint.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param sellingItem   What ItemStack is being given to the Villager.
     * @param buyingItem    The item that the Villager is selling.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam sellingItem <item:minecraft:gravel>
     * @docParam buyingItem <item:minecraft:flint>
     */
    @ZenCodeType.Method
    public void removeItemsForEmeraldsAndItemsTrade(VillagerProfession profession, int villagerLevel, IItemStack sellingItem, IItemStack buyingItem) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof VillagerTrades.ItemsForEmeraldsAndItemsTrade) {
                if(sellingItem.matches(new MCItemStackMutable(((VillagerTrades.ItemsForEmeraldsAndItemsTrade) trade).sellingItem))) {
                    return buyingItem.matches(new MCItemStackMutable(((VillagerTrades.ItemsForEmeraldsAndItemsTrade) trade).buyingItem));
                }
            } else if(trade instanceof BasicTrade) {
                if(sellingItem.matches(new MCItemStackMutable(BasicTradeExposer.getPrice(trade)))) {
                    return buyingItem.matches(new MCItemStackMutable(BasicTradeExposer.getForSale(trade)));
                }
            }
            return false;
        });
    }
    
    /**
     * Removes a Villager trade for Items for an Item with a PotionEffect. An example being, giving a villager an Arrow and getting a Tipped Arrow with night vision.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param potionStack   The base ItemStack that a random potion effect will be applied to. E.G. A tipped Arrow with no effect applied.
     * @param sellingItem   What ItemStack is being given to the Villager.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam potionStack <item:minecraft:tipped_arrow>
     * @docParam sellingItem <item:minecraft:arrow>
     */
    @ZenCodeType.Method
    public void removeItemWithPotionForEmeraldsAndItemsTrade(VillagerProfession profession, int villagerLevel, IItemStack potionStack, Item sellingItem) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof VillagerTrades.ItemWithPotionForEmeraldsAndItemsTrade) {
                if(potionStack.matches(new MCItemStackMutable(((VillagerTrades.ItemWithPotionForEmeraldsAndItemsTrade) trade).potionStack))) {
                    return sellingItem == ((VillagerTrades.ItemWithPotionForEmeraldsAndItemsTrade) trade).buyingItem;
                }
            }
            return false;
        });
    }
    
    /**
     * Removes a Villager trade for Items for Dyed leather armor. An example being, giving a villager a diamond and getting a Blue Dyed Leather chestplate.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param buyingItem    The base ItemStack that a random Dye colour will be applied to. E.G. A leather chestplate with no effect applied.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam buyingItem <item:minecraft:leather_chestplate>
     */
    @ZenCodeType.Method
    public void removeDyedArmorForEmeraldsTrade(VillagerProfession profession, int villagerLevel, Item buyingItem) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof VillagerTrades.DyedArmorForEmeraldsTrade) {
                return ((VillagerTrades.DyedArmorForEmeraldsTrade) trade).tradeItem == buyingItem;
            }
            return false;
        });
    }
    
    /**
     * Removes a Villager trade for a Map. An example being, giving a villager Emeralds and getting a Map to a structure.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     */
    @ZenCodeType.Method
    public void removeEmeraldForMapTrade(VillagerProfession profession, int villagerLevel) {
        
        removeTradeInternal(profession, villagerLevel, trade -> trade instanceof VillagerTrades.EmeraldForMapTrade);
    }
    
    /**
     * Removes a Villager trade for an Enchanted Book. An example being, giving a villager Emeralds and getting an Enchanted Book with a random Enchantment.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     */
    @ZenCodeType.Method
    public void removeEnchantedBookForEmeraldsTrade(VillagerProfession profession, int villagerLevel) {
        
        removeTradeInternal(profession, villagerLevel, trade -> trade instanceof VillagerTrades.EnchantedBookForEmeraldsTrade);
    }
    
    /**
     * Removes a Villager trade for an Enchanted Item. An example being, giving a villager Emeralds and getting an Enchanted Pickaxe.
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     * @param buyingItem    The ItemStack that the Villager is selling (including any NBT).
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     * @docParam buyingItem <item:minecraft:diamond_sword>.withTag({Enchantments: [{lvl: 1 as short, id: "minecraft:smite" as string}]})
     */
    @ZenCodeType.Method
    public void removeEnchantedItemForEmeraldsTrade(VillagerProfession profession, int villagerLevel, IItemStack buyingItem) {
        
        removeTradeInternal(profession, villagerLevel, trade -> {
            if(trade instanceof VillagerTrades.EnchantedItemForEmeraldsTrade) {
                return buyingItem.matches(new MCItemStackMutable(((VillagerTrades.EnchantedItemForEmeraldsTrade) trade).sellingStack));
            }
            return false;
        });
        
    }
    
    /**
     * Removes a Villager trade for Suspicious Stew. An example being, giving a villager Emeralds and getting a bowl of Suspicious Stew back..
     *
     * @param profession    What profession this trade should be for.
     * @param villagerLevel The level the Villager needs to be.
     *
     * @docParam profession <profession:minecraft:farmer>
     * @docParam villagerLevel 1
     */
    @ZenCodeType.Method
    public void removeSuspiciousStewForEmeraldTrade(VillagerProfession profession, int villagerLevel) {
        
        removeTradeInternal(profession, villagerLevel, trade -> trade instanceof VillagerTrades.SuspiciousStewForEmeraldTrade);
    }
    
    
    /**
     * Adds a Wandering Trader Trade for emeralds for an Item. An example being, giving a Wandering Trader 2 emeralds for an arrow.
     *
     * @param rarity    The rarity of the Trade. Valid options are `1` or `2`. A Wandering Trader can only spawn with a single trade of rarity `2`.
     * @param emeralds  The amount of Emeralds.
     * @param forSale   What ItemStack is being sold (by the Wandering Trader).
     * @param maxTrades How many times can this trade be done.
     * @param xp        How much Experience is given by trading.
     *
     * @docParam rarity 1
     * @docParam emeralds 16
     * @docParam forSale <item:minecraft:diamond>
     * @docParam maxTrades 16
     * @docParam xp 2
     */
    @ZenCodeType.Method
    public void addWanderingTrade(int rarity, int emeralds, ItemStack forSale, int maxTrades, int xp) {
        
        BasicTrade trade = new BasicTrade(emeralds, forSale, maxTrades, xp, 1);
        addWanderingTradeInternal(rarity, trade);
    }
    
    /**
     * Removes a Wandering Trader trade for Emeralds for Items. An example being, giving a Wandering Trader 2 Emeralds for an Arrow.
     *
     * @param rarity   The rarity of the Trade. Valid options are `1` or `2`. A Wandering Trader can only spawn with a single trade of rarity `2`.
     * @param tradeFor What ItemStack is being sold (by the Villager).
     *
     * @docParam rarity 2
     * @docParam tradeFor <item:minecraft:arrow>
     */
    @ZenCodeType.Method
    public void removeWanderingTrade(int rarity, IItemStack tradeFor) {
        
        removeWanderingTradeInternal(rarity, trade -> {
            if(trade instanceof VillagerTrades.ItemsForEmeraldsTrade) {
                return tradeFor.matches(new MCItemStackMutable(((VillagerTrades.ItemsForEmeraldsTrade) trade).sellingItem));
            } else if(trade instanceof BasicTrade) {
                return tradeFor.matches(new MCItemStackMutable(BasicTradeExposer.getForSale(trade)));
            }
            return false;
        });
    }
    
    
    private void addTradeInternal(VillagerProfession profession, int villagerLevel, BasicTrade trade) {
        
        CraftTweakerAPI.apply(new ActionAddTrade(profession, villagerLevel, trade));
    }
    
    private void addWanderingTradeInternal(int villagerLevel, BasicTrade trade) {
        
        CraftTweakerAPI.apply(new ActionAddWanderingTrade(villagerLevel, trade));
    }
    
    private void removeTradeInternal(VillagerProfession profession, int villagerLevel, ITradeRemover remover) {
        
        CraftTweakerAPI.apply(new ActionRemoveTrade(profession, villagerLevel, remover));
    }
    
    private void removeWanderingTradeInternal(int villagerLevel, ITradeRemover remover) {
        
        CraftTweakerAPI.apply(new ActionRemoveWanderingTrade(villagerLevel, remover));
    }
    
}