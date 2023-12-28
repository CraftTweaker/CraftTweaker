package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.bracket.custom.EnumConstantBracketHandler;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":compatibility")
public final class CompatibilityCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    private final BracketParserRegistrationManager bracketParserRegistrationManager;
    private final EnumBracketParserRegistrationManager enumBracketParserRegistrationManager;
    private final EventRegistrationManager eventRegistrationManager;
    private final RecipeHandlerGatherer handlerGatherer;
    private final TaggableElementsRegistrationManager taggableElementsRegistrationManager;
    private final ZenClassGatherer zenGatherer;
    private final ZenClassRegistrationManager zenClassRegistrationManager;
    
    public CompatibilityCraftTweakerPlugin() {
        
        this.bracketParserRegistrationManager = new BracketParserRegistrationManager();
        this.enumBracketParserRegistrationManager = new EnumBracketParserRegistrationManager();
        this.eventRegistrationManager = new EventRegistrationManager();
        this.handlerGatherer = new RecipeHandlerGatherer();
        this.taggableElementsRegistrationManager = new TaggableElementsRegistrationManager();
        this.zenGatherer = new ZenClassGatherer();
        this.zenClassRegistrationManager = new ZenClassRegistrationManager();
    }
    
    @Override
    public void initialize() {
        
        this.zenGatherer.gatherCandidates();
    }
    
    @Override
    public void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.zenGatherer.listProviders();
        this.zenGatherer.onCandidates(candidate -> this.zenClassRegistrationManager.attemptRegistration(candidate.loader(), candidate.clazz(), handler));
        this.zenClassRegistrationManager.attemptDeferredRegistration(handler);
    }
    
    @Override
    public void registerBracketParsers(IBracketParserRegistrationHandler handler) {
        
        // Note: even though this does not come from annotations, all enum-based brackets are annotation-determined
        // so it makes more sense to have this here
        handler.registerParserFor(
                CraftTweakerConstants.ALL_LOADERS_MARKER,
                "constant",
                new EnumConstantBracketHandler(),
                new IBracketParserRegistrationHandler.DumperData("constant", EnumConstantBracketHandler.getDumperData())
        );
        
        this.zenGatherer.onCandidates(candidate -> {
            this.bracketParserRegistrationManager.addRegistrationCandidate(candidate.clazz(), candidate.loader());
            this.enumBracketParserRegistrationManager.attemptRegistration(candidate.clazz(), candidate.loader(), handler);
        });
        
        this.bracketParserRegistrationManager.attemptRegistration(handler);
    }
    
    @Override
    public void registerRecipeHandlers(final IRecipeHandlerRegistrationHandler handler) {
        
        this.handlerGatherer.gatherAndRegisterHandlers(handler);
    }
    
    @Override
    public void registerTaggableElements(ITaggableElementRegistrationHandler handler) {
        
        this.zenGatherer.onCandidates(candidate ->
                this.taggableElementsRegistrationManager.attemptRegistration(candidate.clazz(), handler)
        );
    }
    
    @Override
    public void registerEvents(final IEventRegistrationHandler handler) {
        
        this.zenGatherer.onCandidates(candidate -> this.eventRegistrationManager.attemptRegistration(candidate.clazz(), handler));
    }
    
}
