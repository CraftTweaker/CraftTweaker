package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.*;
import org.openzen.zenscript.parser.type.IParsedType;
import org.openzen.zenscript.parser.type.ParsedNamedType;
import org.openzen.zenscript.parser.type.ParsedStorageTag;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.RecipeTypeBracketHandler")
public class RecipeTypeBracketHandler implements BracketExpressionParser {

    private static final Map<ResourceLocation, IRecipeManager> registeredTypes = new HashMap<>();


    public static void registerRecipeManager(Class<?> managerClass) {
        IRecipeManager manager = null;
        try {
            manager = (IRecipeManager) managerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //e.printStackTrace();
        }

        if (manager == null) {
            try {
                final Optional<Field> any = Arrays.stream(managerClass.getDeclaredFields())
                        .filter(f -> Modifier.isPublic(f.getModifiers()))
                        .filter(f -> Modifier.isStatic(f.getModifiers()))
                        .filter(f -> f.getType().equals(managerClass))
                        .findAny();
                if (any.isPresent()) {
                    manager = (IRecipeManager) any.get().get(null);
                }
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            }
        }

        if (manager == null) {
            try {
                final Constructor<?> constructor = managerClass.getConstructor();
                constructor.setAccessible(true);
                manager = ((IRecipeManager) constructor.newInstance());
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                //e.printStackTrace();
            }
        }

        if(manager == null) {
            CraftTweakerAPI.logError("Could not add RecipeManager for %s, please report to the author", managerClass);
            return;
        }

        final ResourceLocation bracketResourceLocation = manager.getBracketResourceLocation();
        registeredTypes.put(bracketResourceLocation, manager);
    }

    private static IParsedType readParsedType(String name, CodePosition position) {
        final List<ParsedNamedType.ParsedNamePart> collect = Arrays.stream(name.split("[.]"))
                .map(s -> new ParsedNamedType.ParsedNamePart(s, null))
                .collect(Collectors.toList());

        return new ParsedNamedType(position, collect, ParsedStorageTag.NULL);
    }
    
    public static boolean containsCustomManager(ResourceLocation location) {
        return registeredTypes.containsKey(location);
    }
    
    public static IRecipeManager getCustomManager(ResourceLocation location) {
        return registeredTypes.get(location);
    }

    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        StringBuilder builder = new StringBuilder();
        while (tokens.optional(ZSTokenType.T_GREATER) == null) {
            builder.append(tokens.next().content);
            builder.append(tokens.getLastWhitespace());
        }

        final ResourceLocation resourceLocation = new ResourceLocation(builder.toString());
        if (registeredTypes.containsKey(resourceLocation)) {
            return getCall(builder.toString(), registeredTypes.get(resourceLocation), position);
        } else {
            return getCallFallback(builder.toString(), position);
        }
    }

    private ParsedExpression getCallFallback(String location, CodePosition position) {
        //Map to 'crafttweaker.api.BracketHandlers.getRecipeManager(location)'
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
        final ParsedExpressionMember bracketHandlers = new ParsedExpressionMember(position, api, "BracketHandlers", null);
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, bracketHandlers, "getRecipeManager", null);
        return new ParsedExpressionCall(position, getRecipeManager, new ParsedCallArguments(Collections.emptyList(), Collections
                .singletonList(new ParsedExpressionString(position, location, false))));
    }

    private ParsedExpression getCall(String location, IRecipeManager manager, CodePosition position) {
        //Map to
        //crafttweaker.api.RecipeTypeBracketHandler.getRecipeManager<type>(location)
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
        final ParsedExpressionMember recipeTypeBracketHandler = new ParsedExpressionMember(position, api, "RecipeTypeBracketHandler", null);
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, recipeTypeBracketHandler, "getRecipeManager", null);


        final String nameContent = manager.getClass().getAnnotation(ZenCodeType.Name.class).value();
        final IParsedType parsedType = readParsedType(nameContent, position);
        final ParsedCallArguments arguments = new ParsedCallArguments(
                Collections.singletonList(parsedType),
                Collections.singletonList(new ParsedExpressionString(position, location, false))
        );
        final ParsedExpressionCall parsedExpressionCall = new ParsedExpressionCall(position, getRecipeManager, arguments);
        return new ParsedExpressionCast(position, parsedExpressionCall, parsedType, false);
    }

    @ZenCodeType.Method
    public static <T extends IRecipeManager> T getRecipeManager(String location) {
        //noinspection unchecked
        return (T) registeredTypes.get(new ResourceLocation(location));
    }
}
