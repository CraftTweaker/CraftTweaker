package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ScriptReloadListener extends SimplePreparableReloadListener<Void> {
    
    private static final class ScriptsDiscoverer extends SimpleFileVisitor<Path> {
    
        private static final PathMatcher scriptFileMatcher = FileSystems.getDefault().getPathMatcher("glob:*.zs");
    
        private final Consumer<Path> adder;
        
        ScriptsDiscoverer(final Consumer<Path> adder) {
            
            this.adder = adder;
        }
        
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            
            super.visitFile(file, attrs);
            if(attrs.isRegularFile()&& scriptFileMatcher.matches(file)) {
                this.adder.accept(file);
            }
            return FileVisitResult.CONTINUE;
        }
        
    }
    
    private static final MutableComponent MSG_RELOAD_STARTING = new TranslatableComponent("crafttweaker.reload.start");
    private static final MutableComponent MSG_RELOAD_COMPLETE = new TranslatableComponent("crafttweaker.reload.complete");
    private static final Random RANDOM = ThreadLocalRandom.current();
    
    private final Supplier<RecipeManager> managerSupplier;
    private final Consumer<MutableComponent> feedbackConsumer;
    
    public ScriptReloadListener(final Supplier<RecipeManager> managerSupplier, final Consumer<MutableComponent> feedbackConsumer) {
        
        this.managerSupplier = managerSupplier;
        this.feedbackConsumer = feedbackConsumer;
    }
    
    @Override
    @SuppressWarnings("NullableProblems")
    protected Void prepare(final ResourceManager resourceManager, final ProfilerFiller profiler) {
        
        return null;
    }
    
    @Override
    @ParametersAreNonnullByDefault
    protected void apply(final Void objectIn, final ResourceManager resourceManagerIn, final ProfilerFiller profilerIn) {
        
        final RecipeManager manager = this.managerSupplier.get();
        
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
    }
    
    private void fixRecipeManager(final RecipeManager manager) {
        
        //ImmutableMap of ImmutableMaps. Nice.
        final AccessRecipeManager accessRecipeManager = (AccessRecipeManager) manager;
        accessRecipeManager.setRecipes(new HashMap<>(accessRecipeManager.getRecipes()));
        accessRecipeManager.getRecipes()
                .replaceAll((k, v) -> new HashMap<>(accessRecipeManager.getRecipes().get(k)));
        accessRecipeManager.setByName(new HashMap<>(accessRecipeManager.getByName()));
        CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(manager);
    }
    
    private Pair<Path, List<Path>> gatherScripts() {
        
        final Path root = CraftTweakerAPI.getScriptsDirectory();
        final List<Path> children = new ArrayList<>();
        try {
            Files.walkFileTree(root, new ScriptsDiscoverer(children::add));
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
        
        final Map<ResourceLocation, Recipe<?>> recipes = ((AccessRecipeManager) manager).getRecipes()
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
            return String.join("\r\n", Files.readAllLines(file));
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
