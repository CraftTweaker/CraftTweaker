package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.managers.*;
import com.blamejared.crafttweaker.api.util.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.lexer.*;
import org.openzen.zenscript.parser.*;
import org.openzen.zenscript.parser.expression.*;
import org.openzen.zenscript.parser.type.*;

import java.util.*;
import java.util.stream.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.RecipeTypeBracketHandler")
public class RecipeTypeBracketHandler implements BracketExpressionParser {
    
    private static final Map<ResourceLocation, IRecipeManager> registeredTypes = new HashMap<>();
    private static final Map<Class<? extends IRecipeManager>, IRecipeManager> managerInstances = new HashMap<>();
    
    public RecipeTypeBracketHandler(List<Class<? extends IRecipeManager>> recipeManagers) {
        registeredTypes.clear();
        for(Class<? extends IRecipeManager> recipeManager : recipeManagers) {
            registerRecipeManager(recipeManager);
        }
    }
    
    private static IParsedType readParsedType(String name, CodePosition position) {
        final List<ParsedNamedType.ParsedNamePart> collect = Arrays.stream(name.split("[.]"))
                .map(s -> new ParsedNamedType.ParsedNamePart(s, null))
                .collect(Collectors.toList());
        
        return new ParsedNamedType(position, collect);
    }
    
    public static boolean containsCustomManager(ResourceLocation location) {
        return registeredTypes.containsKey(location);
    }
    
    public static IRecipeManager getCustomManager(ResourceLocation location) {
        return registeredTypes.get(location);
    }
    
    @ZenCodeType.Method
    public static <T extends IRecipeManager> T getRecipeManager(String location) {
        //noinspection unchecked
        return (T) registeredTypes.get(new ResourceLocation(location));
    }
    
    private void registerRecipeManager(Class<? extends IRecipeManager> managerClass) {
        if(managerInstances.containsKey(managerClass)) {
            final IRecipeManager manager = managerInstances.get(managerClass);
            registeredTypes.put(manager.getBracketResourceLocation(), manager);
            return;
        }
        
        final IRecipeManager manager = InstantiationUtil.getOrCreateInstance(managerClass);
        if(manager == null) {
            CraftTweakerAPI.logError("Could not add RecipeManager for %s, please report to the author", managerClass);
            return;
        }
        
        final ResourceLocation bracketResourceLocation = manager.getBracketResourceLocation();
        managerInstances.put(managerClass, manager);
        registeredTypes.put(bracketResourceLocation, manager);
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        StringBuilder builder = new StringBuilder();
        while(tokens.optional(ZSTokenType.T_GREATER) == null) {
            builder.append(tokens.next().content);
            builder.append(tokens.getLastWhitespace());
        }
        
        final ResourceLocation resourceLocation = new ResourceLocation(builder.toString());
        if(registeredTypes.containsKey(resourceLocation)) {
            return getCall(builder.toString(), registeredTypes.get(resourceLocation), position);
        } else {
            return getCallFallback(builder.toString(), position);
        }
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
        final IParsedType parsedType = readParsedType(nameContent, position);
        final ParsedCallArguments arguments = new ParsedCallArguments(Collections.singletonList(parsedType), Collections
                .singletonList(new ParsedExpressionString(position, location, false)));
        final ParsedExpressionCall parsedExpressionCall = new ParsedExpressionCall(position, getRecipeManager, arguments);
        return new ParsedExpressionCast(position, parsedExpressionCall, parsedType, false);
    }
}
