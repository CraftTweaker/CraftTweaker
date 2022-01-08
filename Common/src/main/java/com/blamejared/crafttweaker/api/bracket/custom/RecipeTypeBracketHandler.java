package com.blamejared.crafttweaker.api.bracket.custom;


import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.RecipeManagerWrapper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionCast;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;
import org.openzen.zenscript.parser.expression.ParsedExpressionVariable;
import org.openzen.zenscript.parser.type.IParsedType;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.RecipeTypeBracketHandler")
public class RecipeTypeBracketHandler implements BracketExpressionParser {
    
    // Using Lazy here allows us to reference scripts in the correct order without f-ing up classloading
    private static final Set<Supplier<RecipeType<?>>> FILTERED_TYPES = ImmutableSet.of(Suppliers.memoize(() -> CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS));
    
    private static final Map<RecipeType<Recipe<?>>, IRecipeManager<Recipe<?>>> registeredTypes = new HashMap<>();
    private static final Map<Class<? extends IRecipeManager<Recipe<?>>>, IRecipeManager<Recipe<?>>> managerInstances = new HashMap<>();
    
    public RecipeTypeBracketHandler(List<Class<? extends IRecipeManager>> recipeManagers) {
        
        registeredTypes.clear();
        recipeManagers.stream()
                .filter(clazz -> !clazz.equals(RecipeManagerWrapper.class))
                .map(clazz -> (Class<? extends IRecipeManager<Recipe<?>>>) clazz)
                .forEach(this::registerRecipeManager);
    }
    
    @Deprecated
    public static boolean containsCustomManager(ResourceLocation location) {
        
        return registeredTypes.containsKey(lookup(location));
    }
    
    @Deprecated
    public static IRecipeManager getCustomManager(ResourceLocation location) {
        
        return registeredTypes.get(lookup(location));
    }
    
    public static Collection<IRecipeManager<Recipe<?>>> getManagerInstances() {
        
        return managerInstances.values();
    }
    
    public static IRecipeManager<Recipe<?>> getOrDefault(final ResourceLocation location) {
        
        return getOrDefault(lookup(location));
    }
    
    public static IRecipeManager<Recipe<?>> getOrDefault(final RecipeType type) {
        
        if(FILTERED_TYPES.stream().anyMatch(it -> type == it.get())) {
            return null;
        }
        
        return registeredTypes.computeIfAbsent(type, RecipeManagerWrapper::makeOrNull);
    }
    
    @ZenCodeType.Method
    public static <T extends IRecipeManager<?>> T getRecipeManager(String location) {
        
        RecipeType<?> recipeType = lookup(new ResourceLocation(location));
        if(recipeType == null) {
            throw new IllegalArgumentException("Unknown recipe type: " + location);
        }
        
        return (T) registeredTypes.get(recipeType);
    }
    
    private void registerRecipeManager(Class<? extends IRecipeManager<Recipe<?>>> managerClass) {
        
        if(managerInstances.containsKey(managerClass)) {
            final IRecipeManager<Recipe<?>> manager = managerInstances.get(managerClass);
            registerInstance(manager);
            return;
        }
        
        final IRecipeManager<Recipe<?>> manager = InstantiationUtil.getOrCreateInstance(managerClass);
        if(manager == null) {
            CraftTweakerAPI.LOGGER.error("Could not add RecipeManager for {}, please report to the author", managerClass);
            return;
        }
        
        managerInstances.put(managerClass, manager);
        registerInstance(manager);
    }
    
    private void registerInstance(IRecipeManager<Recipe<?>> manager) {
        
        final ResourceLocation bracketResourceLocation = manager.getBracketResourceLocation();
        final Class<? extends IRecipeManager> managerClass = manager.getClass();
        
        if(managerClass.getAnnotation(ZenCodeType.Name.class) == null) {
            final String canonicalName = managerClass.getCanonicalName();
            CraftTweakerAPI.LOGGER.warn("No Name Annotation found on manager '{}', it will not be registered!", canonicalName);
            return;
        }
        
        registeredTypes.put(lookup(bracketResourceLocation), manager);
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
        final String name = ParseUtil.readContent(tokens);
        final ResourceLocation resourceLocation = ResourceLocation.tryParse(name);
        if(resourceLocation == null) {
            throw new ParseException(position, "Invalid ResourceLocation, expected: <recipetype:modid:location>");
        }
        
        if(registeredTypes.containsKey(lookup(resourceLocation))) {
            return getCall(name, registeredTypes.get(lookup(resourceLocation)), position);
        }
        
        if(Services.REGISTRY.recipeTypes().keySet().contains(resourceLocation)) {
            return getCallFallback(name, position);
        }
        
        //Unknown BEP
        throw new ParseException(position, String.format("Unknown RecipeType: <recipetype:%s>", name));
    }
    
    private ParsedExpression getCallFallback(String location, CodePosition position) {
        //Map to 'crafttweaker.api.BracketHandlers.getRecipeManager(location)'
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
        final ParsedExpressionMember bracket = new ParsedExpressionMember(position, api, "bracket", Collections.emptyList());
        final ParsedExpressionMember bracketHandlers = new ParsedExpressionMember(position, bracket, "BracketHandlers", null);
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, bracketHandlers, "getRecipeManager", null);
        return new ParsedExpressionCall(position, getRecipeManager, new ParsedCallArguments(Collections
                .emptyList(), Collections.singletonList(new ParsedExpressionString(position, location, false))));
    }
    
    private ParsedExpression getCall(String location, IRecipeManager<?> manager, CodePosition position) {
        //Map to
        //crafttweaker.api.bracket.RecipeTypeBracketHandler.getRecipeManager<type>(location)
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
        final ParsedExpressionMember bracket = new ParsedExpressionMember(position, api, "bracket", Collections.emptyList());
        final ParsedExpressionMember recipeTypeBracketHandler = new ParsedExpressionMember(position, bracket, "RecipeTypeBracketHandler", null);
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, recipeTypeBracketHandler, "getRecipeManager", null);
        
        final String nameContent = manager.getClass()
                .getAnnotation(ZenCodeType.Name.class)
                .value();
        final IParsedType parsedType = ParseUtil.readParsedType(nameContent, position);
        final ParsedCallArguments arguments = new ParsedCallArguments(Collections.singletonList(parsedType), Collections
                .singletonList(new ParsedExpressionString(position, location, false)));
        final ParsedExpressionCall parsedExpressionCall = new ParsedExpressionCall(position, getRecipeManager, arguments);
        return new ParsedExpressionCast(position, parsedExpressionCall, parsedType, false);
    }
    
    private static RecipeType<Recipe<?>> lookup(final ResourceLocation location) {
        //TODO confirm this is fine
        return (RecipeType<Recipe<?>>) Services.REGISTRY.recipeTypes().get(location);
    }
    
}
