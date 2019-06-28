package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.api.zencode.impl.preprocessors.*;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.logger.GroupLogger;
import com.blamejared.crafttweaker.impl.logger.PlayerLogger;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.SimpleReloadableResourceManager;
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
import java.util.*;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public CraftTweaker() throws Exception {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        MinecraftForge.EVENT_BUS.register(this);
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
        LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
        root.then(Commands.literal("hand").executes(context -> {
            CraftTweakerAPI.logInfo("Hand output\n" + new MCItemStackMutable(context.getSource().asPlayer().getHeldItemMainhand()).getCommandString());
            return 0;
        }));
        
        event.getCommandDispatcher().register(root);
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


                final Map<String, IPreprocessor> preprocessors = new HashMap<>();
                {
                    final List<IPreprocessor> pList = Arrays.asList(new DebugPreprocessor(), new NoLoadPreprocessor(), new PriorityPreprocessor(), new ReplacePreprocessor(), new LoadFirstPreprocessor(), new LoadLastPreprocessor());

                    for (IPreprocessor p : pList) {
                        preprocessors.put(p.getName(), p);
                    }
                }

                final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(preprocessors.values());
                SourceFile[] sourceFiles = fileList.stream()
                        .map(file -> new FileAccessSingle(file, preprocessors))
                        .filter(FileAccessSingle::shouldBeLoaded)
                        .sorted(comparator)
                        .map(FileAccessSingle::getSourceFile)
                        .toArray(SourceFile[]::new);

                SemanticModule scripts = engine.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE,
                        compileError -> CraftTweakerAPI.logger.error(compileError.toString()),
                        validationLogEntry -> CraftTweakerAPI.logger.error(validationLogEntry.toString()),
                        sourceFile -> CraftTweakerAPI.logger.info("Loading " + sourceFile.getFilename()));
                
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
    
    public static void findScriptFiles(File path, List<File> files){
        if(path.isDirectory()) {
            for(File file : path.listFiles()) {
                if(file.isDirectory()){
                    findScriptFiles(file, files);
                }else{
                    if(file.getName().toLowerCase().endsWith(".zs")){
                        files.add(file);
                    }
                }
            }
        }
    }
    
}
