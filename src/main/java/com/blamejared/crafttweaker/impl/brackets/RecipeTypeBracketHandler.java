package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.impl.brackets.util.ParseUtil;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import com.google.common.collect.ImmutableSet;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
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

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.RecipeTypeBracketHandler")
public class RecipeTypeBracketHandler implements BracketExpressionParser {
    
    private static final Set<ResourceLocation> FILTERED_MANAGERS = ImmutableSet.of(
            new ResourceLocation(CraftTweaker.MODID, "scripts")
    );
    
    private static final Map<ResourceLocation, IRecipeManager> registeredTypes = new HashMap<>();
    private static final Map<Class<? extends IRecipeManager>, IRecipeManager> managerInstances = new HashMap<>();
    
    public RecipeTypeBracketHandler(List<Class<? extends IRecipeManager>> recipeManagers) {
        registeredTypes.clear();
        for(Class<? extends IRecipeManager> recipeManager : recipeManagers) {
            registerRecipeManager(recipeManager);
        }
    }
    
    @Deprecated
    public static boolean containsCustomManager(ResourceLocation location) {
        return registeredTypes.containsKey(location);
    }
    
    @Deprecated
    public static IRecipeManager getCustomManager(ResourceLocation location) {
        return registeredTypes.get(location);
    }
    
    public static Collection<IRecipeManager> getManagerInstances() {
        return managerInstances.values();
    }
    
    public static IRecipeManager getOrDefault(final ResourceLocation location) {
        if (FILTERED_MANAGERS.contains(location)) return null;
        
        return registeredTypes.computeIfAbsent(location, it -> {
            final IRecipeType<?> type = Registry.RECIPE_TYPE.getOrDefault(location);
            return type == null? null : new RecipeManagerWrapper(type);
        });
    }
    
    public static IRecipeManager getOrDefault(final IRecipeType<?> type) {
        return getOrDefault(new ResourceLocation(type.toString()));
    }
    
    @ZenCodeType.Method
    public static <T extends IRecipeManager> T getRecipeManager(String location) {
        //noinspection unchecked
        return (T) registeredTypes.get(new ResourceLocation(location));
    }
    
    private void registerRecipeManager(Class<? extends IRecipeManager> managerClass) {
        if(managerInstances.containsKey(managerClass)) {
            final IRecipeManager manager = managerInstances.get(managerClass);
            registerInstance(manager);
            return;
        }
        
        final IRecipeManager manager = InstantiationUtil.getOrCreateInstance(managerClass);
        if(manager == null) {
            CraftTweakerAPI.logError("Could not add RecipeManager for %s, please report to the author", managerClass);
            return;
        }
        
        managerInstances.put(managerClass, manager);
        registerInstance(manager);
    }
    
    private void registerInstance(IRecipeManager manager) {
        final ResourceLocation bracketResourceLocation = manager.getBracketResourceLocation();
        final Class<? extends IRecipeManager> managerClass = manager.getClass();
        
        if(managerClass.getAnnotation(ZenCodeType.Name.class) == null) {
            final String canonicalName = managerClass.getCanonicalName();
            CraftTweakerAPI.logWarning("No Name Annotation found on manager '%s', it will not be registered!", canonicalName);
            return;
        }
        
        registeredTypes.put(bracketResourceLocation, manager);
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        final String name = ParseUtil.readContent(tokens);
        final ResourceLocation resourceLocation = ResourceLocation.tryCreate(name);
        if(resourceLocation == null) {
            throw new ParseException(position, "Invalid ResourceLocation, expected: <recipetype:modid:location>");
        }
        
        if(registeredTypes.containsKey(resourceLocation)) {
            return getCall(name, registeredTypes.get(resourceLocation), position);
        }
        
        if(Registry.RECIPE_TYPE.keySet().contains(resourceLocation)) {
            return getCallFallback(name, position);
        }
        
        //Unknown BEP
        throw new ParseException(position, String.format("Unknown RecipeType: <recipetype:%s>", name));
    }
    
    private ParsedExpression getCallFallback(String location, CodePosition position) {
        //Map to 'crafttweaker.api.BracketHandlers.getRecipeManager(location)'
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections
                .emptyList());
        final ParsedExpressionMember bracketHandlers = new ParsedExpressionMember(position, api, "BracketHandlers", null);
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, bracketHandlers, "getRecipeManager", null);
        return new ParsedExpressionCall(position, getRecipeManager, new ParsedCallArguments(Collections
                .emptyList(), Collections.singletonList(new ParsedExpressionString(position, location, false))));
    }
    
    private ParsedExpression getCall(String location, IRecipeManager manager, CodePosition position) {
        //Map to
        //crafttweaker.api.RecipeTypeBracketHandler.getRecipeManager<type>(location)
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections
                .emptyList());
        final ParsedExpressionMember recipeTypeBracketHandler = new ParsedExpressionMember(position, api, "RecipeTypeBracketHandler", null);
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, recipeTypeBracketHandler, "getRecipeManager", null);
        
        
        final String nameContent = manager.getClass().getAnnotation(ZenCodeType.Name.class).value();
        final IParsedType parsedType = ParseUtil.readParsedType(nameContent, position);
        final ParsedCallArguments arguments = new ParsedCallArguments(Collections.singletonList(parsedType), Collections
                .singletonList(new ParsedExpressionString(position, location, false)));
        final ParsedExpressionCall parsedExpressionCall = new ParsedExpressionCall(position, getRecipeManager, arguments);
        return new ParsedExpressionCast(position, parsedExpressionCall, parsedType, false);
    }
}
