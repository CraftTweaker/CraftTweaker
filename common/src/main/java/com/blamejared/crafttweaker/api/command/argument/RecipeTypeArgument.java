package com.blamejared.crafttweaker.api.command.argument;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
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
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeTypeArgument implements ArgumentType<IRecipeManager> {
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("recipe_type");
    
    private static final Collection<String> EXAMPLES = Lists.newArrayList("<recipetype:minecraft:crafting>", "<recipetype:minecraft:blasting>");
    private static final SimpleCommandExceptionType INVALID_STRING = new SimpleCommandExceptionType(new LiteralMessage("Invalid String"));
    private static final DynamicCommandExceptionType UNKNOWN_RECIPE_TYPE = new DynamicCommandExceptionType(o -> new LiteralMessage("Unknown Recipe Type: " + o));
    private static final Pattern RECIPE_TYPE_PATTERN = Pattern.compile("^<recipetype:(\\w+:\\w+)>");
    
    
    @Override
    public IRecipeManager parse(final StringReader reader) throws CommandSyntaxException {
        
        final Matcher matcher = RECIPE_TYPE_PATTERN.matcher(reader.getRemaining());
        
        if(!matcher.find()) {
            
            throw INVALID_STRING.createWithContext(reader);
        }
        
        final String location = matcher.group(1);
        reader.setCursor(reader.getCursor() + matcher.group(0).length());
        
        try {
            return RecipeTypeBracketHandler.getRecipeManager(location);
        } catch(IllegalArgumentException e) {
            throw UNKNOWN_RECIPE_TYPE.createWithContext(reader, location);
        }
    }
    
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        
        return SharedSuggestionProvider.suggest(RecipeTypeBracketHandler.getManagerInstances()
                .stream()
                .map(IRecipeManager::getCommandString), builder);
    }
    
    
    @Override
    public Collection<String> getExamples() {
        
        return EXAMPLES;
    }
    
    
    public static RecipeTypeArgument get() {
        
        return new RecipeTypeArgument();
    }
    
}
