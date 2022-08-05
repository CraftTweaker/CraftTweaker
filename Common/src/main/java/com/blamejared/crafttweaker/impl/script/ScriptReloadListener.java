package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IngredientCacheBuster;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.helper.FileGathererHelper;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessTagManager;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class ScriptReloadListener extends SimplePreparableReloadListener<Void> {
    
    private static final MutableComponent MSG_RELOAD_STARTING = Component.translatable("crafttweaker.reload.start");
    private static final MutableComponent MSG_RELOAD_COMPLETE = Component.translatable("crafttweaker.reload.complete");
    private static final Random RANDOM = ThreadLocalRandom.current();
    
    private final ReloadableServerResources resources;
    private final Consumer<MutableComponent> feedbackConsumer;
    
    public ScriptReloadListener(final ReloadableServerResources managerSupplier, final Consumer<MutableComponent> feedbackConsumer) {
        
        this.resources = managerSupplier;
        this.feedbackConsumer = feedbackConsumer;
    }
    
    
    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        
        return null;
    }
    
    @Override
    protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
        
        IngredientCacheBuster.claim();
        SequenceManager.clearSequences();
        IAccessibleServerElementsProvider asep = CraftTweakerAPI.getAccessibleElementsProvider().server();
        asep.resources(this.resources);
        TagManager tagmanager = asep.accessibleResources().crafttweaker$getTagManager();
        asep.registryAccess(((AccessTagManager) tagmanager).crafttweaker$getRegistryAccess());
        CraftTweakerTagRegistry.INSTANCE.bind(tagmanager);
        
        final RecipeManager manager = this.resources.getRecipeManager();
        
        this.feedbackConsumer.accept(MSG_RELOAD_STARTING);
        this.fixRecipeManager(manager);
        final Pair<Path, List<Path>> scripts = this.gatherScripts();
        final IScriptRun preparedRun = this.prepareRun(scripts);
        
        try {
            preparedRun.execute();
        } catch(final Throwable e) {
            CraftTweakerAPI.LOGGER.error("Unable to execute script run", e);
            return;
        }
        
        this.storeScriptsInRecipes(manager, scripts);
        
        this.feedbackConsumer.accept(MSG_RELOAD_COMPLETE);
        if(!scripts.getSecond().isEmpty() && preparedRun.specificRunInfo().displayBranding()) {
            
            this.displayPatreonBranding();
        }
        IngredientCacheBuster.release();
    }
    
    private void fixRecipeManager(final RecipeManager manager) {
        
        //ImmutableMap of ImmutableMaps. Nice.
        final AccessRecipeManager accessRecipeManager = (AccessRecipeManager) manager;
        accessRecipeManager.crafttweaker$setRecipes(new HashMap<>(accessRecipeManager.crafttweaker$getRecipes()));
        accessRecipeManager.crafttweaker$getRecipes()
                .replaceAll((k, v) -> new HashMap<>(accessRecipeManager.crafttweaker$getRecipes().get(k)));
        accessRecipeManager.crafttweaker$setByName(new HashMap<>(accessRecipeManager.crafttweaker$getByName()));
        CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(manager);
    }
    
    private Pair<Path, List<Path>> gatherScripts() {
        
        final Path root = CraftTweakerAPI.getScriptsDirectory();
        final PathMatcher matcher = root.getFileSystem().getPathMatcher("glob:**.zs");
        final List<Path> children = new ArrayList<>();
        try {
            Files.walkFileTree(root, FileGathererHelper.of(matcher, children::add));
        } catch(final IOException e) {
            CraftTweakerAPI.LOGGER.error("Unable to read script files! This is serious", e);
        }
        return Pair.of(root, Collections.unmodifiableList(children));
    }
    
    private IScriptRun prepareRun(final Pair<Path, List<Path>> scripts) {
        
        final ScriptRunConfiguration configuration = new ScriptRunConfiguration(
                CraftTweakerConstants.DEFAULT_LOADER_NAME,
                CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID,
                ScriptRunConfiguration.RunKind.EXECUTE
        );
        return CraftTweakerAPI.getScriptRunManager()
                .createScriptRun(scripts.getFirst(), scripts.getSecond(), configuration);
    }
    
    private void storeScriptsInRecipes(final RecipeManager manager, final Pair<Path, List<Path>> scripts) {
        
        final Map<ResourceLocation, Recipe<?>> recipes = ((AccessRecipeManager) manager).crafttweaker$getRecipes()
                .computeIfAbsent(CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS, it -> new HashMap<>());
        final Path root = scripts.getFirst();
        
        scripts.getSecond().stream()
                .map(it -> this.buildScriptRecipe(it, root))
                .forEach(it -> recipes.put(it.getId(), it));
    }
    
    private ScriptRecipe buildScriptRecipe(final Path file, final Path root) {
        
        final String fileName = root.relativize(file).toString().replace('\\', '/');
        final String sanitizedFileName = fileName.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9_.-]", "_");
        final ResourceLocation id = CraftTweakerConstants.rl(sanitizedFileName);
        return new ScriptRecipe(id, fileName, this.readContents(file));
    }
    
    private String readContents(final Path file) {
        
        try {
            return String.join("\n", Files.readAllLines(file));
        } catch(final IOException e) {
            CraftTweakerAPI.LOGGER.info("Unable to read script file " + file, e);
            return "";
        }
    }
    
    @SuppressWarnings("SpellCheckingInspection")
    private void displayPatreonBranding() {
        
        final Collection<String> patronList = CraftTweakerCommon.getPatronList();
        
        patronList.stream()
                .skip(patronList.isEmpty() ? 0 : RANDOM.nextInt(patronList.size()))
                .findFirst()
                .ifPresent(name -> CraftTweakerAPI.LOGGER.info("This reload was made possible by {} and more! Become a patron at https://patreon.com/jaredlll08?s=crtmod", name));
    }
    
}
