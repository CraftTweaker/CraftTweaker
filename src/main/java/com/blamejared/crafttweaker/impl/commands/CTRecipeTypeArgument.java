package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.google.common.collect.Lists;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CTRecipeTypeArgument implements ArgumentType<IRecipeManager> {
    INSTANCE;
    
    private static final Collection<String> EXAMPLES = Lists.newArrayList("<recipetype:minecraft:crafting>", "<recipetype:minecraft:blasting>");
    private static final SimpleCommandExceptionType INVALID_STRING = new SimpleCommandExceptionType(new LiteralMessage("Invalid String"));
    private static final Pattern ITEM_PATTERN = Pattern.compile("<recipetype:(\\w+:\\w+)>");
    
    
    @Override
    public IRecipeManager parse(final StringReader reader) throws CommandSyntaxException {
        
        final Matcher matcher = ITEM_PATTERN.matcher(reader.getRemaining());

        if (!matcher.find()) {
            
            throw INVALID_STRING.createWithContext(reader);
        }
        
        final String location = matcher.group(1);
        
        final IRecipeManager type = RecipeTypeBracketHandler.getRecipeManager(location);
        reader.setCursor(reader.getCursor() + matcher.group(0).length());
        return type;
    }
    
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        
        return ISuggestionProvider.suggest(RecipeTypeBracketHandler.getManagerInstances().stream().map(IRecipeManager::getCommandString), builder);
    }
    
    
    @Override
    public Collection<String> getExamples() {
        
        return EXAMPLES;
    }
    
}
