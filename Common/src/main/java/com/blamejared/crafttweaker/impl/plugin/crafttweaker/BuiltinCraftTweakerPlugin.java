package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.bracket.custom.EnumConstantBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagManagerBracketHandler;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IReplacerComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptRunModuleConfiguratorRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IVillagerTradeRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.command.type.DumpCommands;
import com.blamejared.crafttweaker.impl.command.type.HandCommands;
import com.blamejared.crafttweaker.impl.command.type.InventoryCommands;
import com.blamejared.crafttweaker.impl.command.type.MiscCommands;
import com.blamejared.crafttweaker.impl.command.type.ModCommands;
import com.blamejared.crafttweaker.impl.command.type.RecipeCommands;
import com.blamejared.crafttweaker.impl.command.type.conflict.ConflictCommand;
import com.blamejared.crafttweaker.impl.command.type.script.ScriptCommands;
import com.blamejared.crafttweaker.impl.command.type.script.example.ExamplesCommand;
import com.blamejared.crafttweaker.impl.recipe.replacement.DefaultTargetingFilters;
import com.blamejared.crafttweaker.impl.recipe.replacement.DefaultTargetingStrategies;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessDyedArmorForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessEmeraldForItems;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessEnchantedItemForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessItemsAndEmeraldsToItems;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessItemsForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessTippedArrowForItemsAndEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":builtin")
@SuppressWarnings("unused") // Autowired
public final class BuiltinCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    private final BracketParserRegistrationManager bracketParserRegistrationManager;
    private final EnumBracketParserRegistrationManager enumBracketParserRegistrationManager;
    private final RecipeHandlerGatherer handlerGatherer;
    private final TaggableElementsRegistrationManager taggableElementsRegistrationManager;
    private final ZenClassGatherer zenGatherer;
    private final ZenClassRegistrationManager zenClassRegistrationManager;
    
    public BuiltinCraftTweakerPlugin() {
        
        this.bracketParserRegistrationManager = new BracketParserRegistrationManager();
        this.enumBracketParserRegistrationManager = new EnumBracketParserRegistrationManager();
        this.handlerGatherer = new RecipeHandlerGatherer();
        this.taggableElementsRegistrationManager = new TaggableElementsRegistrationManager();
        this.zenGatherer = new ZenClassGatherer();
        this.zenClassRegistrationManager = new ZenClassRegistrationManager();
    }
    
    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        
        handler.registerLoader(CraftTweakerConstants.INIT_LOADER_NAME);
        handler.registerLoader(CraftTweakerConstants.DEFAULT_LOADER_NAME);
        handler.registerLoader(CraftTweakerConstants.TAGS_LOADER_NAME);
    }
    
    @Override
    public void registerLoadSource(final IScriptLoadSourceRegistrationHandler handler) {
        
        handler.registerLoadSource(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID);
        handler.registerLoadSource(CraftTweakerConstants.CLIENT_RECIPES_UPDATED_SOURCE_ID);
    }
    
    @Override
    public void registerModuleConfigurators(final IScriptRunModuleConfiguratorRegistrationHandler handler) {
        
        final IScriptRunModuleConfigurator defaultConfig = IScriptRunModuleConfigurator.createDefault(CraftTweakerConstants.MOD_ID);
        handler.registerConfigurator(CraftTweakerConstants.DEFAULT_LOADER_NAME, defaultConfig);
        handler.registerConfigurator(CraftTweakerConstants.INIT_LOADER_NAME, defaultConfig); // TODO("Is this valid?")
        handler.registerConfigurator(CraftTweakerConstants.TAGS_LOADER_NAME, defaultConfig);
    }
    
    @Override
    public void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.zenGatherer.listProviders();
        this.zenGatherer.onCandidates(candidate -> this.zenClassRegistrationManager.attemptRegistration(candidate.loader(), candidate.clazz(), handler));
        this.zenClassRegistrationManager.attemptDeferredRegistration(handler);
    }
    
    @Override
    public void registerBracketParsers(final IBracketParserRegistrationHandler handler) {
        
        this.zenGatherer.onCandidates(candidate -> {
            this.bracketParserRegistrationManager.addRegistrationCandidate(candidate.clazz(), candidate.loader());
            this.enumBracketParserRegistrationManager.attemptRegistration(candidate.clazz(), candidate.loader(), handler);
        });
        
        this.bracketParserRegistrationManager.attemptRegistration(handler);
        
        handler.registerParserFor(CraftTweakerConstants.ALL_LOADERS_MARKER, "constant", new EnumConstantBracketHandler(), new IBracketParserRegistrationHandler.DumperData("constant", EnumConstantBracketHandler.getDumperData()));
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "recipetype", new RecipeTypeBracketHandler(), new IBracketParserRegistrationHandler.DumperData("recipetype", RecipeTypeBracketHandler.getDumperData()));
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "tag", new TagBracketHandler(), new IBracketParserRegistrationHandler.DumperData("tag", TagBracketHandler.getDumperData()));
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "tagmanager", new TagManagerBracketHandler(), new IBracketParserRegistrationHandler.DumperData("tagmanager", TagManagerBracketHandler.getDumperData()));
        
        handler.registerParserFor(CraftTweakerConstants.TAGS_LOADER_NAME, "tag", new TagBracketHandler());
        handler.registerParserFor(CraftTweakerConstants.TAGS_LOADER_NAME, "tagmanager", new TagManagerBracketHandler());
    }
    
    @Override
    public void registerRecipeComponents(final IRecipeComponentRegistrationHandler handler) {
        
        handler.registerRecipeComponent(BuiltinRecipeComponents.INPUT_INGREDIENT);
        handler.registerRecipeComponent(BuiltinRecipeComponents.OUTPUT_ITEM);
    }
    
    @Override
    public void registerRecipeHandlers(final IRecipeHandlerRegistrationHandler handler) {
        
        this.handlerGatherer.gatherAndRegisterHandlers(handler);
    }
    
    @Override
    public void registerVillagerTradeConverters(final IVillagerTradeRegistrationHandler handler) {
        
        final IItemStack emerald = IItemStack.of(new ItemStack(Items.EMERALD));
        final IItemStack compass = IItemStack.of(new ItemStack(Items.COMPASS));
        final IItemStack book = IItemStack.of(new ItemStack(Items.BOOK));
        final IItemStack enchantedBook = IItemStack.of(new ItemStack(Items.ENCHANTED_BOOK));
        final IItemStack filledMap = IItemStack.of(new ItemStack(Items.FILLED_MAP));
        final IItemStack suspiciousStew = IItemStack.of(new ItemStack(Items.SUSPICIOUS_STEW));
        handler.registerTradeConverter(VillagerTrades.DyedArmorForEmeralds.class, trade -> new CTTradeObject(
                emerald,
                IItemStack.empty(),
                IItemStack.of(((AccessDyedArmorForEmeralds) trade).crafttweaker$getItem().getDefaultInstance())));
        handler.registerTradeConverter(VillagerTrades.EmeraldForItems.class, trade -> new CTTradeObject(
                IItemStack.of(((AccessEmeraldForItems) trade).crafttweaker$getItem().getDefaultInstance()),
                IItemStack.empty(),
                emerald));
        handler.registerTradeConverter(VillagerTrades.TreasureMapForEmeralds.class, trade -> new CTTradeObject(
                emerald,
                compass,
                filledMap));
        handler.registerTradeConverter(VillagerTrades.EmeraldsForVillagerTypeItem.class, trade -> new CTTradeObject(
                // This trade has random inputs, there isn't a good way to get them, so just going to use air.
                IItemStack.empty(),
                IItemStack.empty(),
                emerald));
        handler.registerTradeConverter(VillagerTrades.EnchantBookForEmeralds.class, trade -> new CTTradeObject(
                emerald,
                book,
                enchantedBook));
        handler.registerTradeConverter(VillagerTrades.EnchantedItemForEmeralds.class, trade -> new CTTradeObject(
                emerald,
                IItemStack.empty(),
                IItemStack.ofMutable(((AccessEnchantedItemForEmeralds) trade).crafttweaker$getItemStack())));
        handler.registerTradeConverter(VillagerTrades.TippedArrowForItemsAndEmeralds.class, trade -> new CTTradeObject(
                emerald,
                IItemStack.ofMutable(((AccessTippedArrowForItemsAndEmeralds) trade).crafttweaker$getFromItem()
                        .getDefaultInstance()),
                IItemStack.ofMutable(((AccessTippedArrowForItemsAndEmeralds) trade).crafttweaker$getToItem())));
        handler.registerTradeConverter(VillagerTrades.ItemsAndEmeraldsToItems.class, trade -> new CTTradeObject(
                emerald,
                IItemStack.ofMutable(((AccessItemsAndEmeraldsToItems) trade).crafttweaker$getFromItem()),
                IItemStack.ofMutable(((AccessItemsAndEmeraldsToItems) trade).crafttweaker$getToItem())));
        handler.registerTradeConverter(VillagerTrades.ItemsForEmeralds.class, trade -> new CTTradeObject(
                emerald,
                IItemStack.empty(),
                IItemStack.ofMutable(((AccessItemsForEmeralds) trade).crafttweaker$getItemStack())));
        handler.registerTradeConverter(VillagerTrades.SuspiciousStewForEmerald.class, trade -> new CTTradeObject(
                emerald,
                IItemStack.empty(),
                suspiciousStew));
        
    }
    
    @Override
    public void registerCommands(final ICommandRegistrationHandler handler) {
        
        //TODO("Determine what permission to use")
        ConflictCommand.registerCommands(handler);
        DumpCommands.registerCommands(handler);
        InventoryCommands.registerCommands(handler);
        HandCommands.registerCommands(handler);
        ScriptCommands.registerCommands(handler);
        MiscCommands.registerCommands(handler);
        ModCommands.registerCommands(handler);
        RecipeCommands.registerCommands(handler);
        ExamplesCommand.registerCommand(handler);
        DumpCommands.registerDumpers(handler);
    }
    
    @Override
    public void registerTaggableElements(ITaggableElementRegistrationHandler handler) {
        
        this.zenGatherer.onCandidates(candidate -> {
            this.taggableElementsRegistrationManager.attemptRegistration(candidate.clazz(), handler);
        });
    }
    
    @Override
    public void registerReplacerComponents(final IReplacerComponentRegistrationHandler handler) {
        
        handler.registerTargetingFilter(DefaultTargetingFilters::scripts);
        handler.registerTargetingFilter(DefaultTargetingFilters::vanillaSpecial);
        
        // TODO("Find better names for all of these")
        handler.registerTargetingStrategy(ITargetingStrategy.DEFAULT_STRATEGY_ID, DefaultTargetingStrategies::plain);
        handler.registerTargetingStrategy(CraftTweakerConstants.rl("plain"), DefaultTargetingStrategies::plain);
        handler.registerTargetingStrategy(CraftTweakerConstants.rl("deep"), DefaultTargetingStrategies::deep);
    }
    
}
