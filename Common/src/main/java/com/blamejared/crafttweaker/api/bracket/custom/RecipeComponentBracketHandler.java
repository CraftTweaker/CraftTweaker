package com.blamejared.crafttweaker.api.bracket.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.type.IParsedType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ZenCodeType.Name("crafttweaker.api.bracket.RecipeComponentBracketHandler")
@ZenRegister
public final class RecipeComponentBracketHandler implements BracketExpressionParser {
    
    private static final String BRACKET_METHOD_NAME = "bracket";
    private static final Map<Class<?>, String> STDLIB_TYPES = Util.make(() -> {
        final Map<Class<?>, String> m = new Object2ObjectArrayMap<>(); // Brute-force works better for small data-sets
        
        m.put(List.class, "stdlib.List");
        m.put(String.class, "string");
        
        put(m, Void.class, void.class, BasicTypeID.VOID);
        put(m, Boolean.class, boolean.class, BasicTypeID.BOOL);
        put(m, Byte.class, byte.class, BasicTypeID.SBYTE);
        put(m, Short.class, short.class, BasicTypeID.SHORT);
        put(m, Integer.class, int.class, BasicTypeID.INT);
        put(m, Long.class, long.class, BasicTypeID.LONG);
        put(m, Float.class, float.class, BasicTypeID.FLOAT);
        put(m, Double.class, double.class, BasicTypeID.DOUBLE);
        put(m, Character.class, char.class, BasicTypeID.CHAR);
        
        return m;
    });
    
    public RecipeComponentBracketHandler() {}
    
    @ZenCodeType.Method(BRACKET_METHOD_NAME)
    public static <T> IRecipeComponent<T> bracket(final ResourceLocation name) {
        
        return IRecipeComponent.find(name);
    }
    
    public static Supplier<Stream<String>> getDumperData() {
        
        return () -> CraftTweakerAPI.getRegistry()
                .getAllRecipeComponents()
                .stream()
                .map(IRecipeComponent::getCommandString)
                .distinct();
    }
    
    private static void put(final Map<Class<?>, String> m, final Class<?> boxed, final Class<?> primitive, final BasicTypeID id) {
        
        m.put(primitive, id.name);
        m.put(boxed, id.name + '?');
    }
    
    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        
        final String name = ParseUtil.readBracketContent(position, tokens);
        
        final ResourceLocation rl;
        try {
            rl = new ResourceLocation(name);
        } catch(final ResourceLocationException e) {
            throw new ParseException(position, "Invalid name, expected <recipecomponent:modid:location>", e);
        }
        
        final IRecipeComponent<?> component = IRecipeComponent.find(rl);
        
        if(component == null) {
            throw new ParseException(position, "Unknown component " + rl);
        }
        
        final TypeToken<?> token = component.objectType();
        
        return this.call(position, token, rl);
    }
    
    private <T> ParsedExpression call(final CodePosition position, final TypeToken<T> token, final ResourceLocation rl) throws ParseException {
        
        final ParsedExpression thisClass = this.compileClassCall(position);
        final ParsedExpression method = new ParsedExpressionMember(position, thisClass, BRACKET_METHOD_NAME, List.of());
        final ParsedExpression argument = ParseUtil.createResourceLocationArgument(position, rl);
        final IParsedType generic = this.makeParsedType(token, position);
        final ParsedCallArguments arguments = new ParsedCallArguments(List.of(generic), List.of(argument));
        return new ParsedExpressionCall(position, method, arguments);
    }
    
    private ParsedExpression compileClassCall(final CodePosition position) {
        
        return ParseUtil.staticMemberExpression(position, this.className(this.getClass()));
    }
    
    private <T> IParsedType makeParsedType(final TypeToken<T> token, final CodePosition position) throws ParseException {
        
        try {
            return ParseUtil.readParsedType(makeParsedType(token.getType()), position);
        } catch(final UnsupportedOperationException | IllegalStateException | NoSuchElementException e) {
            throw new ParseException(position, "Unable to resolve component generic " + token + " due to an error", e);
        }
    }
    
    private String makeParsedType(final Type type) {
        
        if(type instanceof ParameterizedType generic) {
            
            final String rawType = this.makeParsedType(generic.getRawType());
            return Arrays.stream(generic.getActualTypeArguments())
                    .map(this::makeParsedType)
                    .collect(Collectors.joining(", ", rawType + "<", ">"));
        } else if(type instanceof Class<?> clazz) {
            
            return this.className(clazz);
        } else if(type instanceof TypeVariable<?>) {
            
            throw new UnsupportedOperationException("Unresolved generic identified " + type);
        }
        
        throw new IllegalStateException();
    }
    
    private String className(final Class<?> clazz) {
        
        // TODO("Find a way to obtain full ZenCode type information within BEPs: ZenCode change?")
        final String stdlibType = STDLIB_TYPES.get(clazz);
        if(stdlibType != null) {
            return stdlibType;
        }
        
        final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        final IZenClassRegistry registry = CraftTweakerAPI.getRegistry().getZenClassRegistry();
        return registry.getNameFor(loader, clazz).orElseThrow();
    }
    
}
