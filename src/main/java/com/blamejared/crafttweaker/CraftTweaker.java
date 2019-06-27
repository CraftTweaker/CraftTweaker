package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.logger.*;
import com.blamejared.crafttweaker.impl.managers.*;
import com.mojang.brigadier.builder.*;
import net.minecraft.command.*;
import net.minecraft.item.crafting.*;
import net.minecraft.resources.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.resource.*;
import org.apache.logging.log4j.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;
import org.openzen.zenscript.codemodel.member.ref.*;
import org.openzen.zenscript.formatter.*;
import org.openzen.zenscript.parser.*;

import java.io.*;
import java.lang.reflect.*;
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
                
                File[] files = fileList.toArray(new File[0]);
//                if(files == null )
//                    throw new FileNotFoundException("Could not find/open script dir " + CraftTweakerAPI.SCRIPT_DIR);
                SourceFile[] sourceFiles = Arrays.stream(files).map(file -> new FileSourceFile(file.getName(), file)).toArray(SourceFile[]::new);
                
                SemanticModule scripts = engine.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE, compileError -> CraftTweakerAPI.logger.error(compileError.toString()), validationLogEntry -> CraftTweakerAPI.logger.error(validationLogEntry.toString()), sourceFile -> CraftTweakerAPI.logger.info("Loading " + sourceFile.getFilename()));
                if(!scripts.isValid()) {
                    CraftTweakerAPI.logger.error("Scripts are invalid!");
                    LOG.info("Scripts are invalid!");
                    return;
                }
                
                boolean formatScripts = false;
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
