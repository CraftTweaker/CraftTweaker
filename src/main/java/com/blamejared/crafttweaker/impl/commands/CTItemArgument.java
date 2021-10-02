package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.data.StringConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.brackets.BracketHandlers;
import com.google.common.collect.Lists;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zenscript.lexer.ParseException;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CTItemArgument implements ArgumentType<IItemStack> {
    INSTANCE;
    private static final Collection<String> EXAMPLES = Lists.newArrayList("<item:minecraft:apple>", "<item:minecraft:iron_ingot>.withTag({display: {Name: \"wow\" as string}})");
    private static final DynamicCommandExceptionType MALFORMED_DATA = new DynamicCommandExceptionType(o -> new LiteralMessage(((ParseException) o).message));
    private static final SimpleCommandExceptionType INVALID_STRING = new SimpleCommandExceptionType(new LiteralMessage("invalid string"));
    private static final Pattern ITEM_PATTERN = Pattern.compile("<item:(\\w+:\\w+)>(.withTag\\((\\{.*})\\))?");
    
    @Override
    public IItemStack parse(StringReader reader) throws CommandSyntaxException {
        
        Matcher matcher = ITEM_PATTERN.matcher(reader.getRemaining());
        if(!matcher.find()) {
            throw INVALID_STRING.createWithContext(reader);
        }
        String itemLocation = matcher.group(1);
        try {
            IItemStack stack = getItem(itemLocation, matcher.group(3));
            reader.setCursor(reader.getCursor() + matcher.group(0).length());
            return stack;
        } catch(ParseException e) {
            reader.setCursor(reader.getCursor() + itemLocation.length() + "<item:>.withTag(".length() + e.position.getFromLineOffset());
            throw MALFORMED_DATA.createWithContext(reader, e);
        }
    }
    
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        
        return ISuggestionProvider.suggest(ForgeRegistries.ITEMS.getKeys().stream().map(it -> String.format("<item:%s>", it)), builder);
    }
    
    @Override
    public Collection<String> getExamples() {
        
        return EXAMPLES;
    }
    
    private static IItemStack getItem(String location, String tag) throws ParseException {
        
        IItemStack stack = BracketHandlers.getItem(location).mutable();
        if(tag != null) {
            stack.withTag(StringConverter.convert(tag));
        }
        return stack;
    }
    
}
