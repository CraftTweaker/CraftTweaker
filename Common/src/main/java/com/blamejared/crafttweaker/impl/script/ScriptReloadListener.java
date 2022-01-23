package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.CraftTweakerDefaultScriptRunConfiguration;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ScriptReloadListener extends SimplePreparableReloadListener<Void> {
    
    public static final MutableComponent MSG_RELOAD_STARTING = new TranslatableComponent("crafttweaker.reload.start");
    public static final MutableComponent MSG_RELOAD_COMPLETE = new TranslatableComponent("crafttweaker.reload.complete");
    
    private final Supplier<RecipeManager> managerSupplier;
    private final Consumer<MutableComponent> feedbackConsumer;
    
    public ScriptReloadListener(Supplier<RecipeManager> managerSupplier, Consumer<MutableComponent> feedbackConsumer) {
        
        this.managerSupplier = managerSupplier;
        this.feedbackConsumer = feedbackConsumer;
    }
    
    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        
        return null;
    }
    
    @Override
    @ParametersAreNonnullByDefault
    protected void apply(Void objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        
        feedbackConsumer.accept(MSG_RELOAD_STARTING);
        //ImmutableMap of ImmutableMaps. Nice.
        RecipeManager recipeManager = managerSupplier.get();
        AccessRecipeManager accessRecipeManager = (AccessRecipeManager) recipeManager;
        accessRecipeManager.setRecipes(new HashMap<>(accessRecipeManager.getRecipes()));
        accessRecipeManager.getRecipes()
                .replaceAll((k, v) -> new HashMap<>(accessRecipeManager.getRecipes().get(k)));
        CraftTweakerAPI.setRecipeManager(recipeManager);
        
        CraftTweakerAPI.loadScripts(new ScriptLoadingOptions()
                .setSource(ScriptLoadingOptions.RELOAD_LISTENER_SCRIPT_SOURCE)
                .setRunConfiguration(CraftTweakerDefaultScriptRunConfiguration.DEFAULT_CONFIGURATION)
                .execute());
        List<File> scriptFiles = CraftTweakerAPI.getScriptFiles();
        scriptFiles.stream()
                .map(file -> new ScriptRecipe(new ResourceLocation(CraftTweakerConstants.MOD_ID, file.getPath()
                        .substring("scripts\\".length())
                        .replaceAll("[^a-z0-9_.-]", "_")), file.getPath()
                        .substring("scripts\\".length()), readContents(file)))
                .forEach(scriptRecipe -> {
                    Map<ResourceLocation, Recipe<?>> map = accessRecipeManager.getRecipes()
                            .computeIfAbsent(CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS, iRecipeType -> new HashMap<>());
                    map.put(scriptRecipe.getId(), scriptRecipe);
                });
        feedbackConsumer.accept(MSG_RELOAD_COMPLETE);
        if(scriptFiles.size() > 0 && !CraftTweakerAPI.NO_BRAND) {
            Set<String> patronList = CraftTweakerCommon.getPatronList();
            Optional<String> found = patronList.stream()
                    .skip(patronList.isEmpty() ? 0 : new Random().nextInt(patronList.size()))
                    .findFirst();
            found.ifPresent(name -> CraftTweakerAPI.LOGGER.info("This reload was made possible by {} and more! Become a patron at https://patreon.com/jaredlll08?s=crtmod", name));
        }
        
    }
    
    private String readContents(File file) {
        
        try(final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines().collect(Collectors.joining("\r\n"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
}
