package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerWrapper;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;

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
        
        handler.registerLoadSource(CraftTweakerConstants.rl("reload_listener"));
        handler.registerLoadSource(CraftTweakerConstants.rl("client_recipes_updated"));
    }
    
    @Override
    public void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.zenGatherer.listProviders();
        this.zenGatherer.onCandidates(candidate -> this.zenClassRegistrationManager.attemptRegistration(candidate.loader(), candidate.clazz(), handler));
    }
    
    @Override
    public void registerBracketParsers(final IBracketParserRegistrationHandler handler) {
        
        this.zenGatherer.onCandidates(candidate -> {
            this.bracketParserRegistrationManager.attemptRegistration(candidate.clazz(), candidate.loader(), handler);
            this.enumBracketParserRegistrationManager.attemptRegistration(candidate.clazz(), candidate.loader(), handler);
        });
    }
    
    @Override
    public void registerRecipeHandlers(final IRecipeHandlerRegistrationHandler handler) {
        
        this.handlerGatherer.gatherAndRegisterHandlers(handler);
    }
    
    @Override
    @SuppressWarnings("CodeBlock2Expr")
    public void registerListeners(final IListenerRegistrationHandler handler) {
        
        // TODO("Find a better place for this; and also per-loader API?")
        handler.registerCraftTweakerLoadCompleteListener(() -> {
            CraftTweakerAPI.getRegistry().getAllLoaders().forEach(loader -> {
                CraftTweakerAPI.getRegistry().getZenClassRegistry().getImplementationsOf(loader, ITagManager.class)
                        .stream()
                        .filter(it -> !it.equals(TagManagerWrapper.class))
                        .forEach(CrTTagRegistryData.INSTANCE::addTagImplementationClass);
            });
        });
    }
    
}
