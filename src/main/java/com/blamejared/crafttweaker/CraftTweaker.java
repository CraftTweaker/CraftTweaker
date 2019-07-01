package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.DebugPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.LoadFirstPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.LoadLastPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.NoLoadPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.PriorityPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.ReplacePreprocessor;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.logger.GroupLogger;
import com.blamejared.crafttweaker.impl.logger.PlayerLogger;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.JavaNativeModule;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.ScriptBlock;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.formatter.FileFormatter;
import org.openzen.zenscript.formatter.ScriptFormattingSettings;
import org.openzen.zenscript.parser.PrefixedBracketParser;
import org.openzen.zenscript.parser.SimpleBracketParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    public static final String VERSION = "5.0.0";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public CraftTweaker() throws Exception {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        MinecraftForge.EVENT_BUS.register(this);
        PacketHandler.init();
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
        CraftTweakerAPI.setupLoggers();
        CraftTweakerAPI.SCRIPT_DIR.mkdirs();
        CraftTweakerAPI.SCRIPT_DIR.mkdir();
        CraftTweakerRegistry.findClasses();
        
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        LOG.info("{} client has loaded successfully!", NAME);
    }
    
    
    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ((GroupLogger) CraftTweakerAPI.logger).addLogger(new PlayerLogger(event.getPlayer()));
    }
    
    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        CTCommands.init(event.getCommandDispatcher());
    }
    
    public class TagCollectionWrapper extends TagCollection<Item> {
        
        public TagCollectionWrapper(Function<ResourceLocation, Optional<Item>> p_i50686_1_, String p_i50686_2_, boolean p_i50686_3_, String p_i50686_4_) {
            super(p_i50686_1_, p_i50686_2_, p_i50686_3_, p_i50686_4_);
            
        }
    }
    
    @SubscribeEvent
    public void startServer(FMLServerAboutToStartEvent event) {
        
        SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) event.getServer().getResourceManager();
        manager.addReloadListener((ISelectiveResourceReloadListener) (resourceManager, resourcePredicate) -> {
            //ImmutableMap of ImmutableMaps. Nice.
            RecipeManager recipeManager = event.getServer().getRecipeManager();
            recipeManager.recipes = new HashMap<>(recipeManager.recipes);
            for(IRecipeType<?> type : recipeManager.recipes.keySet()) {
                recipeManager.recipes.put(type, new HashMap<>(recipeManager.recipes.get(type)));
            }
            CTRecipeManager.recipeManager = recipeManager;
            
            try {
                CraftTweakerAPI.reload();
                ScriptingEngine engine = new ScriptingEngine();
                engine.debug = true;
                //Register crafttweaker module first to assign deps
                JavaNativeModule crafttweakerModule = engine.createNativeModule(MODID, "crafttweaker");
                CraftTweakerRegistry.getClassesInPackage("crafttweaker").forEach(crafttweakerModule::addClass);
                CraftTweakerRegistry.getZenGlobals().forEach(crafttweakerModule::addGlobals);
                PrefixedBracketParser bep = new PrefixedBracketParser(null);
                for(Method method : CraftTweakerRegistry.getBracketResolvers()) {
                    String name = method.getAnnotation(BracketResolver.class).value();
                    FunctionalMemberRef memberRef = crafttweakerModule.loadStaticMethod(method);
                    bep.register(name, new SimpleBracketParser(engine.registry, memberRef));
                }
                engine.registerNativeProvided(crafttweakerModule);
                for(String key : CraftTweakerRegistry.getRootPackages()) {
                    //module already registered
                    if(key.equals("crafttweaker")) {
                        continue;
                    }
                    JavaNativeModule module = engine.createNativeModule(key, key, crafttweakerModule);
                    CraftTweakerRegistry.getClassesInPackage(key).forEach(module::addClass);
                    engine.registerNativeProvided(module);
                }
                List<File> fileList = new ArrayList<>();
                findScriptFiles(CraftTweakerAPI.SCRIPT_DIR, fileList);
                
                
                final List<IPreprocessor> preprocessors = new ArrayList<>(6);
                preprocessors.add(new DebugPreprocessor());
                preprocessors.add(new NoLoadPreprocessor());
                preprocessors.add(new PriorityPreprocessor());
                preprocessors.add(new ReplacePreprocessor());
                preprocessors.add(new LoadFirstPreprocessor());
                preprocessors.add(new LoadLastPreprocessor());
                
                final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(preprocessors);
                SourceFile[] sourceFiles = fileList.stream().map(file -> new FileAccessSingle(file, preprocessors)).filter(FileAccessSingle::shouldBeLoaded).sorted(comparator).map(FileAccessSingle::getSourceFile).toArray(SourceFile[]::new);
                
                SemanticModule scripts = engine.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE, compileError -> CraftTweakerAPI.logger.error(compileError.toString()), validationLogEntry -> CraftTweakerAPI.logger.error(validationLogEntry.toString()), sourceFile -> CraftTweakerAPI.logger.info("Loading " + sourceFile.getFilename()));
                
                if(!scripts.isValid()) {
                    CraftTweakerAPI.logger.error("Scripts are invalid!");
                    LOG.info("Scripts are invalid!");
                    return;
                }
                
                boolean formatScripts = true;
                //  toggle this to format scripts, ideally this should be a command
                if(formatScripts) {
                    List<HighLevelDefinition> all = scripts.definitions.getAll();
                    ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
                    FileFormatter formatter = new FileFormatter(builder.build());
                    List<ScriptBlock> blocks = scripts.scripts;
                    for(ScriptBlock block : blocks) {
                        String format = formatter.format(scripts.rootPackage, block, all);
                        File parent = new File("scriptsFormatted");
                        parent.mkdirs();
                        parent.mkdir();
                        File file = new File(parent, block.file.getFilename());
                        file.createNewFile();
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(format);
                        writer.close();
                    }
                }
                engine.registerCompiled(scripts);
                engine.run(Collections.emptyMap(), this.getClass().getClassLoader());
                
            } catch(Exception e) {
                e.printStackTrace();
                CraftTweakerAPI.logger.throwingErr("Error running scripts", e);
            }
            
            CraftTweakerAPI.endFirstRun();
        });
    }
    
    
    public static void findScriptFiles(File path, List<File> files) {
        if(path.isDirectory()) {
            for(File file : path.listFiles()) {
                if(file.isDirectory()) {
                    findScriptFiles(file, files);
                } else {
                    if(file.getName().toLowerCase().endsWith(".zs")) {
                        files.add(file);
                    }
                }
            }
        }
    }
    
}
