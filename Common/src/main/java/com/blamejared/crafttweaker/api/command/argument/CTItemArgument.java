package com.blamejared.crafttweaker.api.command.argument;

import com.blamejared.crafttweaker.api.bracket.BracketHandlers;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.converter.StringConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.google.common.collect.Lists;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import org.openzen.zenscript.lexer.ParseException;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CTItemArgument implements ArgumentType<IItemStack> {
    INSTANCE;
    private static final Collection<String> EXAMPLES = Lists.newArrayList("<item:minecraft:apple>", "<item:minecraft:iron_ingot>.withTag({display: {Name: \"wow\" as string}})");
    private static final DynamicCommandExceptionType MALFORMED_DATA = new DynamicCommandExceptionType(o -> new LiteralMessage(((ParseException) o).message));
    private static final DynamicCommandExceptionType UNKNOWN_ITEM = new DynamicCommandExceptionType(o -> new LiteralMessage("Unknown item: " + o));
    private static final SimpleCommandExceptionType INVALID_STRING = new SimpleCommandExceptionType(new LiteralMessage("invalid string"));
    private static final Pattern ITEM_PATTERN = Pattern.compile("^<item:(\\w+:\\w+)>(\\.withTag\\((\\{.*})\\))?(\\s*\\*\s*(\\d+))?");
    
    @Override
    public IItemStack parse(StringReader reader) throws CommandSyntaxException {
        
        Matcher matcher = ITEM_PATTERN.matcher(reader.getRemaining());
        if(!matcher.find()) {
            throw INVALID_STRING.createWithContext(reader);
        }
        String itemLocation = matcher.group(1);
        try {
            IItemStack stack = getItem(itemLocation, matcher.group(3), matcher.group(5));
            reader.setCursor(reader.getCursor() + matcher.group(0).length());
            return stack;
        } catch(ParseException e) {
            reader.setCursor(reader.getCursor() + itemLocation.length() + "<item:>.withTag(".length() + e.position.getFromLineOffset());
            throw MALFORMED_DATA.createWithContext(reader, e);
        } catch(IllegalArgumentException e) {
            reader.setCursor(reader.getCursor() + matcher.group(0).length());
            throw UNKNOWN_ITEM.createWithContext(reader, itemLocation);
        }
    }
    
    @Override
    public Collection<String> getExamples() {
        
        return EXAMPLES;
    }
    
    private static IItemStack getItem(String location, String tag, String amount) throws ParseException, IllegalArgumentException {
        
        IItemStack stack = BracketHandlers.getItem(location).asMutable();
        if(tag != null) {
            IData data = StringConverter.convert(tag);
            if(data instanceof MapData map) {
                stack.withTag(map);
            } else {
                throw new IllegalArgumentException("Given tag: '%s' was not of type MapData!".formatted(tag));
            }
            
        }
        if(amount != null) {
            try {
                int stackAmount = Integer.parseInt(amount);
                stack.setAmount(stackAmount);
            }catch(NumberFormatException e){
                throw new IllegalArgumentException("Given amount: '%s' was not a valid integer!".formatted(tag));
            }
        }
        return stack;
    }
    
}
