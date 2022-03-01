package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.bracket.custom.EnumConstantBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagManagerBracketHandler;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptRunModuleConfiguratorRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerWrapper;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;

import java.util.List;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":builtin")
@SuppressWarnings("unused") // Autowired
public final class BuiltinCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    private final BracketParserRegistrationManager bracketParserRegistrationManager;
    private final EnumBracketParserRegistrationManager enumBracketParserRegistrationManager;
    private final RecipeHandlerGatherer handlerGatherer;
    private final ZenClassGatherer zenGatherer;
    private final ZenClassRegistrationManager zenClassRegistrationManager;
    
    public BuiltinCraftTweakerPlugin() {
        
        this.bracketParserRegistrationManager = new BracketParserRegistrationManager();
        this.enumBracketParserRegistrationManager = new EnumBracketParserRegistrationManager();
        this.handlerGatherer = new RecipeHandlerGatherer();
        this.zenGatherer = new ZenClassGatherer();
        this.zenClassRegistrationManager = new ZenClassRegistrationManager();
    }
    
    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        
        handler.registerLoader(CraftTweakerConstants.INIT_LOADER_NAME);
        handler.registerLoader(CraftTweakerConstants.DEFAULT_LOADER_NAME);
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
    }
    
    @Override
    public void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.zenGatherer.listProviders();
        this.zenGatherer.onCandidates(candidate -> this.zenClassRegistrationManager.attemptRegistration(candidate.loader(), candidate.clazz(), handler));
    }
    
    @Override
    public void registerBracketParsers(final IBracketParserRegistrationHandler handler) {
        
        this.zenGatherer.onCandidates(candidate -> {
            this.bracketParserRegistrationManager.addRegistrationCandidate(candidate.clazz(), candidate.loader());
            this.enumBracketParserRegistrationManager.attemptRegistration(candidate.clazz(), candidate.loader(), handler);
        });
        
        this.bracketParserRegistrationManager.attemptRegistration(handler);
        
        final ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
        final IScriptLoader loader = registry.findLoader(CraftTweakerConstants.DEFAULT_LOADER_NAME);
        final List<Class<? extends IRecipeManager>> recipeManagers = registry.getZenClassRegistry()
                .getImplementationsOf(loader, IRecipeManager.class);
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "recipetype", (engine, module) -> new RecipeTypeBracketHandler(recipeManagers), new IBracketParserRegistrationHandler.DumperData("recipetype", RecipeTypeBracketHandler.getDumperData()));
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "constant", (engine, module) -> new EnumConstantBracketHandler(), new IBracketParserRegistrationHandler.DumperData("constant", EnumConstantBracketHandler.getDumperData()));
        
        final TagManagerBracketHandler tagManagerBEP = new TagManagerBracketHandler(CrTTagRegistryData.INSTANCE);
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "tagmanager", (engine, module) -> tagManagerBEP);
        handler.registerParserFor(CraftTweakerConstants.DEFAULT_LOADER_NAME, "tag", (engine, module) -> new TagBracketHandler(tagManagerBEP));
    }
    
    @Override
    public void registerRecipeHandlers(final IRecipeHandlerRegistrationHandler handler) {
        
        this.handlerGatherer.gatherAndRegisterHandlers(handler);
    }
    
    @Override
    @SuppressWarnings("CodeBlock2Expr")
    public void registerListeners(final IListenerRegistrationHandler handler) {
        
        handler.onZenDataRegistrationCompletion(() -> {
            // TODO("Per-loader API")
            CraftTweakerAPI.getRegistry().getAllLoaders().forEach(loader -> {
                CraftTweakerAPI.getRegistry().getZenClassRegistry().getImplementationsOf(loader, ITagManager.class)
                        .stream()
                        .filter(it -> !it.equals(TagManagerWrapper.class))
                        .forEach(CrTTagRegistryData.INSTANCE::addTagImplementationClass);
            });
        });
    }
    
}
